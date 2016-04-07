package preprocess;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

public class Test_preprocess {

	public static void main(String[] args) {

		FileReader fi = null;
		FileReader file = null;
		try {
			fi = new FileReader("/home/ander/train.BOW.arff");
			file = new FileReader("/home/ander/test_blind.BOW.arff");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Instances train = null;
		Instances test = null;

		try {
			train = new Instances(fi);
			test = new Instances(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		train.setClassIndex(0);
		test.setClassIndex(0);
		
		Instances newTrain = null;
		Instances newTest = null;
		
		AttributeSelection filter = new AttributeSelection();
		InfoGainAttributeEval eval = new InfoGainAttributeEval();
		filter.setEvaluator(eval);
		Ranker search = new Ranker();
		search.setThreshold(0.0);
		filter.setSearch(search);
		
		try {
			filter.setInputFormat(train);
			newTrain = Filter.useFilter(train, filter);
			newTest = Filter.useFilter(test, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}


		ArffSaver saver = new ArffSaver();
		saver.setInstances(newTrain);
		try {
			saver.setFile(new File("/home/ander/newTrain.arff"));
			saver.writeBatch();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		saver.setInstances(newTest);
		try {
			saver.setFile(new File("/home/ander/newTest.arff"));
			saver.writeBatch();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
