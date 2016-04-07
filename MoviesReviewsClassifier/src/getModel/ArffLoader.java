package getModel;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import weka.core.Instances;

public class ArffLoader {

	private String path;
	private Instances data;
	
	public ArffLoader(String path){
		 
		this.path = path;
		data=null;
		load();
	}
	private void load(){
	    // 1.2. Open the file
	    FileReader fi=null;
		try {
			///home/endika/weka-3-6-13/data/breast-cancer.arff
			fi= new FileReader(path);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Revisar path del fichero de datos: "+path);
		}
		// 1.3. Load the instances
		try {
			data = new Instances(fi);
		} catch (IOException e) {
			System.out.println("ERROR: Revisar contenido del fichero de datos: "+path);
		}
		// 1.4. Close the file
		try {
			fi.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		// 1.5. Shuffle the instances: apply Randomize filter
		//  HACER!!!!
		//int randomInt = (int) (10.0 * Math.random()) + 2;
		//data.randomize(new Random(randomInt));

		// 1.6. Specify which attribute will be used as the class: the last one, in this case 
		data.setClassIndex(data.numAttributes()-1);
	}
	
	public Instances getData(){
		return data;
	}
}
