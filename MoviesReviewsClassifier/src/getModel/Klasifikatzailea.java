package getModel;

import weka.classifiers.Classifier;
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
}
