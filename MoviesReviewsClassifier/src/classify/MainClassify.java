package classify;

import preprocess.ArffLoader;
import weka.classifiers.Classifier;
import weka.core.Instances;

//https://stackoverflow.com/questions/20017957/how-to-reuse-saved-classifier-created-from-explorerin-weka-in-eclipse-java
public class MainClassify {

	public static void main(String[] args) {
		//args0 = dev, args1 = train and args2 = pth to save the model
		if(args.length<2){
			System.out.println("usage: TODO");
			System.out.println("example: TODO");
			System.out.println("exit with error -1");
			System.exit(-1);
		}
		//load model
		String path=args[0]; 
		Classifier cls;
		try {
			cls = (Classifier) weka.core.SerializationHelper.read(path);

			//predict instance class values
			ArffLoader arffLoader;
			arffLoader = new ArffLoader(args[1]);
			Instances originalTest=arffLoader.getData();
				
			//which instance to predict class value
			for (int s = 0; s < originalTest.numInstances();s++){
				//perform your prediction
				double value;
				
				value = cls.classifyInstance(originalTest.instance(s));
				
		
				//get the name of the class value
				String prediction=originalTest.classAttribute().value((int)value); 
		
				System.out.println("The predicted value of instance "+
				                    Integer.toString(s)+
				                    ": "+prediction); 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
