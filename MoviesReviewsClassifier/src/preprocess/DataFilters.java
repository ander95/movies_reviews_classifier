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
			bow.setWordsToKeep(450);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return applyFilter(bow, data);
	}


	public Instances[] getGainAttributeEval(Instances train, Instances test){

		Instances[] inst = new Instances[2];
		
		AttributeSelection filter = new AttributeSelection();
		InfoGainAttributeEval eval = new InfoGainAttributeEval();
		Ranker search = new Ranker();
		search.setThreshold(0.0);
		filter.setEvaluator(eval);
		filter.setSearch(search);
		
		Instances newTrain = null;
		Instances newTest = null;
		
		try {
			filter.setInputFormat(train);
			newTrain = Filter.useFilter(train, filter);
			newTest = Filter.useFilter(test, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		inst[0] = newTrain;
		inst[1] = newTest;

		return inst;
	}




}
