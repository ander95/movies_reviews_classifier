package preprocess;
import java.util.ArrayList;
import java.util.Iterator;

import weka.core.Instance;
import weka.core.Instances;
//import weka.core.pmml.jaxbbindings.Attribute;

public class Main {
	
	public static void main(String[] args) {

		if(args.length<4){
			System.out.println("usage: java -jar Preprocess.jar <path to arff>");
			System.out.println("example: java -jar Preprocess.jar /tmp/file.arff");
			System.out.println("exit with error -1");
			System.exit(-1);
		}
		//args 0: dev, 1: train, 2: test_blind
		//ArffLoader arffLoader;
		
		ArffLoader arffLoader = new ArffLoader(args[0]);
		Instances allInstances = arffLoader.getData();
		
		DataFilters dataFilters = new DataFilters();
		Instances bowAllInstances = dataFilters.getBOW(allInstances);

		Instances instancesDevBow = new Instances(bowAllInstances, 0, 400);
		Instances instancesTrainBow = new Instances(bowAllInstances, 400, 1200);
		Instances instancesTestBow = new Instances(bowAllInstances, 1600, 400);
		
		ArffGorde gordeallBOW = new ArffGorde(instancesDevBow, args[0].replace(".arff", "BOW.arff"));
		gordeallBOW.gorde();
		ArffGorde gordedev = new ArffGorde(instancesDevBow, args[1]);
		gordedev.gorde();
		ArffGorde gordetrain = new ArffGorde(instancesTrainBow, args[2]);
		gordetrain.gorde();
		ArffGorde gordetest = new ArffGorde(instancesTestBow, args[3]);
		gordetest.gorde(); 
	}	
}
