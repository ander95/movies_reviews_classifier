package preprocess;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

public class GainAttributeEval {
	
	public GainAttributeEval(){
		
	}
	
	public Instances gainEval(Instances data){
		AttributeSelection filter = new AttributeSelection();
		InfoGainAttributeEval eval = new InfoGainAttributeEval();
		Ranker search = new Ranker();
		double param = 0.0;
		search.setThreshold(param);
		try {
			filter.setInputFormat(data);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//filter.setAttributeIndices("1");
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

}
