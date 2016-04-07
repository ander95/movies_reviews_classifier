package getModel;

import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.Bagging;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class Klasifikatzailea {

	private Bagging klasifikatzailea;
	public Klasifikatzailea(Instances data, int numIterations){
		try {
			klasifikatzailea.setSeed(1);
			klasifikatzailea.setNumIterations(numIterations);
			//TODO
			String[] optionsBagging = {};
			klasifikatzailea.setOptions(optionsBagging);
			klasifikatzailea.buildClassifier(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Classifier getClassifier(){
		return klasifikatzailea.getClassifier();
	}
	public void save(String path){
		try {
			SerializationHelper.write(path, getClassifier());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
	}

	public void parametroEkorketa(Instances trainData) {
		Bagging est=(Bagging) this.getClassifier();
		int kMax=0;
		Evaluation evaluator=null;
		double fMeasureMax=0;
		double fMeasure=0;
		for (int k = 1; k < 20; k++) {
			est.setNumIterations(k);
			for (int i = 1; i < 100; i++) {
				try {
					evaluator = new Evaluation(trainData);
					evaluator.crossValidateModel(est, trainData, 10, new Random(1));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fMeasure=evaluator.fMeasure(trainData.numAttributes());
				//			fMeasure=evaluator.fMeasure(0);
				if (fMeasureMax <fMeasure){
					kMax=k;
					fMeasureMax=fMeasure;
				}
			}
		}
	}
}
