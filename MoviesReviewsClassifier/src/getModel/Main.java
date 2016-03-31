package getModel;

import preprocess.ArffLoader;
import weka.classifiers.Classifier;
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
		int numIterations = 10;
		Klasifikatzailea klasifikatzailea = new Klasifikatzailea(instancesDev, numIterations);
		
		
		
		//klasifikatzailea gorde
		klasifikatzaileOnena.save(args[2]);
	}

}
