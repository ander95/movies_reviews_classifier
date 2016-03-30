package preprocess;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

public class GainAttributeEval {
	
	public GainAttributeEval(){
		
	}
	
	private Instances gainEval(Instances data){
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

}
