package preprocess;
import weka.core.Instances;
import weka.filters.Filter;
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
		bow.setLowerCaseTokens(true);
		bow.setOutputWordCounts(true);
		bow.setAttributeIndices("1");
		bow.setWordsToKeep(2000);
		return applyFilter(bow, data);
	}
	
	
	
	
}
