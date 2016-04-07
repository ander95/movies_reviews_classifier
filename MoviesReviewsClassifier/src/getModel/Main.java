package getModel;

import java.util.Random;

import preprocess.ArffLoader;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.Bagging;
import weka.core.Instances;

public class Main {

	public static void main(String[] args) {
		//args0 = dev, args1 = train and args2 = pth to save the model
		if(args.length<3){
			System.out.println("usage: TODO");
			System.out.println("example: TODO");
			System.out.println("exit with error -1");
			System.exit(-1);
		}
		
		ArffLoader arffLoader;
		arffLoader = new ArffLoader(args[0]);
		Instances instancesDev = arffLoader.getData();
		arffLoader = new ArffLoader(args[1]);
		Instances instancesTrain = arffLoader.getData();
		
		//Klasifikatzaile onena gordetzeko
		Klasifikatzailea klasifikatzaileOnena = null;
		
		//ekortu behar den zenbakia numIterations by default 10
//		int numIterations = 10;
//		int bagSizePercent = 10;
		boolean bagError = false;
		boolean representUsingWeights = false;
		Klasifikatzailea klasifikatzailea;
		
		//parametroak ekortu
		Classifier est;
		Evaluation evaluator=null;
		double fMeasureMax=0;
		double fMeasure=0;
		for (int numIterations = 1; numIterations < 20; numIterations++) {
			for (int bagSizePercent = 100; bagSizePercent > 0; bagSizePercent--) {
				System.out.println(numIterations);
				System.out.println(bagSizePercent);
				klasifikatzailea = new Klasifikatzailea(instancesTrain, numIterations, bagSizePercent, bagError, representUsingWeights);
				est= klasifikatzailea.getClassifier();
				try {
					evaluator = new Evaluation(instancesTrain);
					evaluator.crossValidateModel(est, instancesTrain, 10, new Random(1));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fMeasure=evaluator.fMeasure(0);
				//			fMeasure=evaluator.fMeasure(0);
				if (fMeasureMax <fMeasure){
					fMeasureMax=fMeasure;
					klasifikatzaileOnena=klasifikatzailea;
				}
				
			}
		}
		
		//klasifikatzailea gorde
		klasifikatzaileOnena.save(args[2]);
	}

}
