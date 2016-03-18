package preprocess;
import weka.core.Instance;
import weka.core.Instances;

public class Main {
	
	public static void main(String[] args) {
		if(args.length<1){
			System.out.println("usage: java -jar Preprocess.jar <path to arff>");
			System.out.println("example: java -jar Preprocess.jar /tmp/file.arff");
			System.out.println("exit with error -1");
			System.exit(-1);
		}
		ArffLoader arffLoader = new ArffLoader(args[0]);
		Instances instances = arffLoader.getData();
		
	}	
}
