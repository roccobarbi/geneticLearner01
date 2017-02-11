/**
 * 
 */
package geneticLearner01;

/**
 * This class defines the item for the training set that drives evolution.
 * Each item contains a string of arbitrary complexity and a boolean attribute that specifies
 * if the item should be matched by the filter.
 * 
 * @author WT-Rocco
 *
 */
public class TrainingItem {
	
	private String text;
	private boolean isFiltered;
	
	// Accessors
	public String getText(){
		return text;
	}
	
	public boolean getIsFiltered(){
		return isFiltered;
	}
	
	/**
	 * Default constructor: empty string that is not filtered
	 */
	public TrainingItem(){
		text = "";
		isFiltered = false;
	}
	
	/**
	 * Complete constructor: string and isFiltered attribute
	 */
	public TrainingItem(String theText, boolean filtered){
		text = theText;
		isFiltered = filtered;
	}
}
