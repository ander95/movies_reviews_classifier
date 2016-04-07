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
		//args 0: dev, 1: train, 2: test_blind
		//ArffLoader arffLoader;
		
		ArffLoader arffLoader = new ArffLoader(args[0]);
		Instances allInstances = arffLoader.getData();
		
		DataFilters dataFilters = new DataFilters();
		Instances bowAllInstances = dataFilters.getBOW(allInstances);
		//bowAllInstances = dataFilters.getGainAttributeEval(bowAllInstances);
		Instances bowAllInstancesGainAttributeEval = dataFilters.getGainAttributeEval(new Instances(bowAllInstances, 0, 1600));
		
		System.out.println(bowAllInstancesGainAttributeEval.numAttributes());
		
		for(int i = bowAllInstances.numAttributes()-1; i>0;i--){
			boolean contains = false;
			for (int e = 0; e<bowAllInstancesGainAttributeEval.numAttributes() && !contains;e++){
				System.out.println(bowAllInstancesGainAttributeEval.attribute(e));
				if(bowAllInstancesGainAttributeEval.attribute(e).equals(bowAllInstances.attribute(i))){
					contains = true;
					System.out.println(i+": "+e);
				}
			}
			if(contains==false){
				bowAllInstances = dataFilters.remove(i, bowAllInstances);
			}
		}
		
		/*Instances instancesDevBow = new Instances(bowAllInstancesGainAttributeEval, 0, 400);
		Instances instancesTrainBow = new Instances(bowAllInstancesGainAttributeEval, 400, 1200);
		Instances instancesTestBow = new Instances(bowAllInstances, 1600, 400);*/
		
		Instances instancesDevBow = new Instances(bowAllInstances, 0, 400);
		Instances instancesTrainBow = new Instances(bowAllInstances, 400, 1200);
		Instances instancesTestBow = new Instances(bowAllInstances, 1600, 400);
		
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
