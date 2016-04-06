package preprocess;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class GainAttributeEval {

	public GainAttributeEval(){

	}

	public Instances gainEval(Instances data){

		//BOW egiterakoan klase atributua lehena jartzen da

		data.setClassIndex(0);

		AttributeSelection filter = new AttributeSelection();
		InfoGainAttributeEval eval = new InfoGainAttributeEval();
		Ranker search = new Ranker();
		double param = 0.0;
		search.setThreshold(param);
		filter.setEvaluator(eval);
		filter.setSearch(search);
		Instances newData = null;
		try {
			newData = Filter.useFilter(data, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newData;
	}

	public Instances fssTFIFD(Instances data){

		//BOW egiterakoan klase atributua lehena jartzen da

		data.setClassIndex(0);

		StringToWordVector filter = new StringToWordVector();
		
		filter.setIDFTransform(true);
		filter.setTFTransform(true);
		filter.setOutputWordCounts(true);		
		
		Instances newData = null;
		try {
			newData = Filter.useFilter(data, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newData;
	}

}
