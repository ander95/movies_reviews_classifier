package getModel;

import preprocess.ArffLoader;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class proba {

	public static void main(String[] args) {
		//args0 = dev, args1 = train
		if(args.length<2){
			System.out.println("usage: TODO");
			System.out.println("example: TODO");
			System.out.println("exit with error -1");
			System.exit(-1);
		}
		
		ArffLoader arffLoader;
		arffLoader = new ArffLoader((String) args[0]);
		Instances instancesDev = arffLoader.getData();
		arffLoader = new ArffLoader((String)args[1]);
		Instances instancesTrain = arffLoader.getData();
		
		Bagging bag = new Bagging();
		bag.setClassifier(new J48());
		
		Object[] param = parametroakEkortu(bag, instancesTrain, instancesDev);
		
		bag.setBagSizePercent((int)param[0]);
		bag.setCalcOutOfBag((boolean)param[1]);
		bag.setNumIterations(10);
		
		Classifier cls = bag;
		Evaluation eval = null;
		try {
			cls.buildClassifier(instancesTrain);
			eval = new Evaluation(instancesTrain);
			eval.evaluateModel(cls, instancesDev);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Parametro egokienak:\nBagSizePercent: "+param[0]+"\nCalcOutOfBag: "+param[1]);
		System.out.println(cls);
		System.out.println(eval.toSummaryString());
		try {
			System.out.println(eval.toClassDetailsString());
			System.out.println(eval.toMatrixString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static Object[] parametroakEkortu(Bagging bag, Instances train, Instances dev) {
		Object[] param = new Object[2];
		
		int ehunekoa = 1;
		
		bag.setBagSizePercent(ehunekoa);
		bag.setCalcOutOfBag(false);
		
		Classifier cls = bag;
		Evaluation eval = null;
		try {
			cls.buildClassifier(train);
			eval = new Evaluation(train);
			eval.evaluateModel(cls, dev);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int ehunekoOnena = ehunekoa;
		double fmeasure = eval.weightedFMeasure();
		System.out.println("Ehunekoa: "+ehunekoa+"\nFmeasurea:"+fmeasure);
		ehunekoa++;
		while (ehunekoa < 100) {
			bag.setBagSizePercent(ehunekoa);
			cls = bag;
			eval = null;
			try {
				cls.buildClassifier(train);
				eval = new Evaluation(train);
				eval.evaluateModel(cls, dev);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Ehunekoa: "+ehunekoa+"\nFmeasurea:"+eval.weightedFMeasure());
			if (eval.weightedFMeasure()>fmeasure) {
				ehunekoOnena = ehunekoa;
				fmeasure = eval.weightedFMeasure();
			}
			ehunekoa++;
		}
		bag.setCalcOutOfBag(true);
		cls = bag;
		eval = null;
		try {
			cls.buildClassifier(train);
			eval = new Evaluation(train);
			eval.evaluateModel(cls, dev);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Ehunekoa: "+ehunekoa+"\nFmeasurea:"+eval.weightedFMeasure());
		
		param[0] = ehunekoOnena;
		param[1] = false;
		
		if (eval.weightedFMeasure()>fmeasure) {
			param[1] = true;
			fmeasure = eval.weightedFMeasure();
			return param;
		}
		return param;
	}

}
