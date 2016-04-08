package getModel;

import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class Klasifikatzailea {

	private Bagging klasifikatzailea;
	public Klasifikatzailea(Instances data, int numIterations, int bagSizePercent, boolean bagError, boolean representUsingWeights){
		try {
			klasifikatzailea=new Bagging();
			data.setClassIndex(data.numAttributes()-1);
			klasifikatzailea.setSeed(1);
			//number of bagging iterations mayor que 1
			klasifikatzailea.setNumIterations(numIterations);
			//tamaño en porcentage de cada bolsa de baggin 1-100
			klasifikatzailea.setBagSizePercent(bagSizePercent);
			//permitir calcular error de quedarse fuera de la bolsa true-false
			klasifikatzailea.setCalcOutOfBag(bagError);
			//permitir tener mal copias de instancias que instancias reales
			//klasifikatzailea.setRepresentCopiesUsingWeights(representUsingWeights);
			klasifikatzailea.setClassifier(new J48());
			klasifikatzailea.buildClassifier(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Classifier getClassifier(){
		//return klasifikatzailea.getClassifier();
		return klasifikatzailea;
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
