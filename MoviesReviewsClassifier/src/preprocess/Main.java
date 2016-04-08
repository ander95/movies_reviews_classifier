package preprocess;
import java.util.ArrayList;
import java.util.Iterator;

import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Remove;
//import weka.core.pmml.jaxbbindings.Attribute;

public class Main {
	
	public static void main(String[] args) {

		if(args.length<4){
			System.out.println("usage: java -jar Preprocess.jar <path to arff>");
			System.out.println("example: java -jar Preprocess.jar /tmp/file.arff");
			System.out.println("exit with error -1");
			System.exit(-1);
		}
		//args 0:allData, 1: dev, 2: train, 3: test_blind
		//ArffLoader arffLoader;
		
		ArffLoader arffLoader = new ArffLoader(args[0]);
		Instances allInstances = arffLoader.getData();
		
		DataFilters dataFilters = new DataFilters();
		Instances bowAllInstances = dataFilters.getBOW(allInstances);
		
		Instances dev_train = new Instances(bowAllInstances, 0, 1600);
		Instances test = new Instances (bowAllInstances, 1600, 400);
		
		Instances[] newTrain_Test = dataFilters.getGainAttributeEval(dev_train, test);
		
		Instances newDevTrain = newTrain_Test[0];
		Instances newTest = newTrain_Test[1];
		
		Instances instancesDevBow = new Instances(newDevTrain, 0, 400);
		Instances instancesTrainBow = new Instances(newDevTrain, 400, 1200);
		Instances instancesTestBow = newTest;
		
		ArffGorde gordeallBOW = new ArffGorde(bowAllInstances, args[0].replace(".arff", "BOW.arff"));
		gordeallBOW.gorde();
		ArffGorde gordedev = new ArffGorde(instancesDevBow, args[1]);
		gordedev.gorde();
		ArffGorde gordetrain = new ArffGorde(instancesTrainBow, args[2]);
		gordetrain.gorde();
		ArffGorde gordetest = new ArffGorde(instancesTestBow, args[3]);
		gordetest.gorde(); 
	}	
}
