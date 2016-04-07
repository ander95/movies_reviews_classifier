package preprocess;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class DataFilters {

	public DataFilters() {

	}


	private Instances applyFilter(Filter filter, Instances data) {
		try {
			filter.setInputFormat(data);
			data = Filter.useFilter(data, filter);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return data;
	}	


	public Instances getBOW(Instances data) {
		StringToWordVector bow =new StringToWordVector();
		try {
			bow.setIDFTransform(false);
			bow.setTFTransform(false);
			bow.setLowerCaseTokens(true);
			bow.setOutputWordCounts(true);
			bow.setAttributeIndices("1");
			bow.setWordsToKeep(2000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return applyFilter(bow, data);
	}

	
	public Instances remove(int i, Instances data){
		Remove remove = new Remove();
		remove.setAttributeIndices(Integer.toString(i));
		return applyFilter(remove, data);
	}
	


	public Instances fssTFIFD(Instances data){

		StringToWordVector bow = new StringToWordVector();
		try {
			bow.setIDFTransform(true);
			bow.setTFTransform(true);
			bow.setLowerCaseTokens(true);
			bow.setOutputWordCounts(true);
			bow.setAttributeIndices("1");
			bow.setWordsToKeep(2000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return applyFilter(bow, data);
	}


	public Instances getGainAttributeEval(Instances data){
		/*AttributeSelection filter = new AttributeSelection();
		InfoGainAttributeEval eval = new InfoGainAttributeEval();
		Ranker search = new Ranker();
		double param = 0.0;
		search.setThreshold(param);
		filter.setEvaluator(eval);
		filter.setSearch(search);*/
		AttributeSelection filter = new AttributeSelection();
		InfoGainAttributeEval eval = new InfoGainAttributeEval();
		Ranker search = new Ranker();
		//search.setNumToSelect(-1);
		search.setNumToSelect(1000);
		search.setThreshold(-1.8);
		filter.setEvaluator(eval);
		filter.setSearch(search);


		return applyFilter(filter, data);
	}




}
