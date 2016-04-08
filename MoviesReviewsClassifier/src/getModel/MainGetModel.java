package getModel;

import java.util.Random;

import preprocess.ArffLoader;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.Bagging;
import weka.core.Instances;

public class MainGetModel {

	public static void main(String[] args) {
		//args0 = dev, args1 = train, args2 = pth to save the model, args3 =mode 1 k 2 trainvstest
		if(args.length<4){
			System.out.println("usage: java -jar getModel.jar <path to preprocess dev output file> <path to preprocess train output file> <path to model output> <mode: 1 crossValidate, 2 train vs test>");
			System.out.println("example: java -jar getModel.jar ./devp.arff ./trainp.arff ./model.model 2");
			System.out.println("exit with error -1");
			System.out.println(args.length);
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

			for (int bagSizePercent = 100; bagSizePercent > 0; bagSizePercent = bagSizePercent-10) {
				//System.out.println(numIterations);
				System.out.println(bagSizePercent);
				//klasifikatzailea = new Klasifikatzailea(instancesTrain, numIterations, bagSizePercent, bagError, representUsingWeights);
				klasifikatzailea = new Klasifikatzailea(instancesTrain, 10, bagSizePercent, bagError, representUsingWeights);
				est= klasifikatzailea.getClassifier();
				try {
					evaluator = new Evaluation(instancesTrain);
					if(args[3].equals("1")){
						evaluator.crossValidateModel(est, instancesTrain, 10, new Random(1));
					}else{
						evaluator.evaluateModel(est, instancesDev);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//fMeasure=evaluator.fMeasure(0);
				fMeasure=evaluator.weightedFMeasure();
				//			fMeasure=evaluator.fMeasure(0);
				if (fMeasureMax <fMeasure){
					fMeasureMax=fMeasure;
					klasifikatzaileOnena=klasifikatzailea;
					System.out.println("fMeasure: "+fMeasureMax);
					//System.out.println("numIterations: "+numIterations);
					System.out.println("bagSizePercent: "+bagSizePercent);
					System.out.println("================================================");
				}else{
					System.out.println("fMeasureNO: "+fMeasureMax);
					//System.out.println("numIterations: "+numIterations);
					System.out.println("bagSizePercentNO: "+bagSizePercent);
					System.out.println("================================================");
				}
			}
		}

		//klasifikatzailea gorde
		klasifikatzaileOnena.save(args[2]);
	}

}
