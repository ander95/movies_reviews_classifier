package preprocess;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class ArffGorde {

	Instances data;
	String path;
	public ArffGorde(Instances data, String path){
		this.data = data;
		this.path = path;
	}
	public void gorde(){
				 ArffSaver saver = new ArffSaver();
				 saver.setInstances(data);
				 try {
					saver.setFile(new File(path));
					 saver.writeBatch();
				} catch (IOException e) {
					e.printStackTrace();
				}
				 
	}
}
