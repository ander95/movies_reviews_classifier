package preprocess;
import java.util.ArrayList;
import java.util.Iterator;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.pmml.jaxbbindings.Attribute;

public class Main {
	
	public static void main(String[] args) {
		if(args.length<3){
			System.out.println("usage: java -jar Preprocess.jar <path to arff>");
			System.out.println("example: java -jar Preprocess.jar /tmp/file.arff");
			System.out.println("exit with error -1");
			System.exit(-1);
		}
		//args 0: dev, 1: train, 2: test_blind
		//ArffLoader arffLoader;
		ArffLoader arffLoader1 = new ArffLoader(args[0]);
		Instances instancesDev = arffLoader1.getData();
		ArffLoader arffLoader2 = new ArffLoader(args[1]);
		Instances instancesTrain = arffLoader2.getData();
		ArffLoader arffLoader3 = new ArffLoader(args[2]);
		Instances instancesTest = arffLoader3.getData();

		System.out.println(instancesDev.numInstances());
		System.out.println(instancesTrain.numInstances());
		System.out.println(instancesTest.numInstances());
		
		System.out.println(instancesDev.get(0));
		System.out.println(instancesTrain.get(0));
		System.out.println(instancesTest.get(0));
		
		
		Instances allInstances = new Instances(instancesDev);
		allInstances.addAll(instancesTrain);
		allInstances.addAll(instancesTest);
		/*
		System.out.println(allInstances.get(400));
		//'the kids
		Iterator<Instance> iterator = allInstances.iterator();
		Instance i = null;
		while (iterator.hasNext()) {
			//System.out.println(iterator.next());
			i = iterator.next();
			//System.out.println(i);
		}*/
		//System.out.println(i);
		
		/*Iterator<Instance> iteratordev = instancesDev.iterator();
		while (iteratordev.hasNext()) {
			System.out.println(iteratordev.next());
		}*/
		/*Iterator<Instance> iteratortrain = instancesTrain.iterator();
		while (iteratortrain.hasNext()) {
			//System.out.println(iteratortrain.next());
			allInstances.add(iteratortrain.next());
		}
		Iterator<Instance> iteratortest = instancesTest.iterator();
		while (iteratortest.hasNext()) {
			//System.out.println(iteratortest.next());
			allInstances.add(iteratortest.next());
		}*/
		
		/*Instances allInstances = new Instances(instancesDev);
		for (int i=0; i < instancesTrain.numInstances(); i++){
			//allInstances.add(instancesTrain.instance(i));
			allInstances.add(instancesTrain.get(i));
		}
		for (int i=0; i < instancesTest.numInstances(); i++){
			//allInstances.add(instancesTest.instance(i));
			allInstances.add(instancesTest.get(i));
		}*/
		System.out.println("allInstances: "+allInstances.numInstances());
		System.out.println(allInstances.get(0));
		System.out.println(allInstances.get(400));
		System.out.println(allInstances.get(1600));
		
		DataFilters dataFilters = new DataFilters();
		Instances bowAllInstances = dataFilters.getBOW(allInstances);
		System.out.println(bowAllInstances.numInstances());
		

		Instances instancesDevBow = new Instances(bowAllInstances, 0, instancesDev.numInstances());
		System.out.println(instancesDevBow.numInstances());
		Instances instancesTrainBow = new Instances(bowAllInstances, instancesDev.numInstances(), instancesTrain.numInstances());
		System.out.println(instancesTrainBow.numInstances());
		Instances instancesTestBow = new Instances(bowAllInstances, instancesTrain.numInstances()+instancesDev.numInstances(), instancesTest.numInstances());
		System.out.println(instancesTestBow.numInstances());
		
		ArffGorde gordedev = new ArffGorde(instancesDevBow, args[0].replace(".arff", "")+"BOW.arff");
		gordedev.gorde();
		ArffGorde gordetrain = new ArffGorde(instancesTrainBow, args[1].replace(".arff", "")+"BOW.arff");
		gordetrain.gorde();
		ArffGorde gordetest = new ArffGorde(instancesTestBow, args[2].replace(".arff", "")+"BOW.arff");
		gordetest.gorde();
	}	
}
