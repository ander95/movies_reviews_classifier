package getModel;

import weka.classifiers.Classifier;
import weka.classifiers.meta.Bagging;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class Klasifikatzailea {

	private Bagging klasifikatzailea;
	public Klasifikatzailea(Instances data, int numIterations, int bagSizePercent, boolean bagError, boolean representUsingWeights){
		try {
			klasifikatzailea.setSeed(1);
			//number of bagging iterations mayor que 1
			klasifikatzailea.setNumIterations(numIterations);
			//tamaño en porcentage de cada bolsa de baggin 1-100
			klasifikatzailea.setBagSizePercent(bagSizePercent);
			//permitir calcular error de quedarse fuera de la bolsa true-false
			klasifikatzailea.setCalcOutOfBag(bagError);
			//permitir tener mal copias de instancias que instancias reales
			//klasifikatzailea.setRepresentCopiesUsingWeights(representUsingWeights);
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
