/**
 * 
 */
package geneticLearner01;

/**
 * Defines an individual rule, which can be active or inactive, inclusive or exclusive.
 * Each rule item can filter an element as spam or allow it as non spam.
 * Inclusive rules are stronger than exclusive ones:
 * - all exclusive rules should be considered in an AND relationship with each other;
 * - all inclusive rules should be considered in an OR relationship with each other;
 * - messages should be allowed if ((NOT excluded) OR INCLUDED).
 * When evaluating the fitness of rules and weighting the complexity of a rule to avoid overfitting,
 * inclusive rules should weight more than exclusive ones.
 * @author WT-Rocco
 *
 */
public class RuleItem {
	private String word; // The word to be checked, all in lowercase
	private boolean isActive; // true if the rule is active
	private boolean isInclusive; // true if the rule is inclusive
	
	/**
	 * Default constructor. Rules are assumed exclusive and inactive.
	 */
	public RuleItem(){
		word = "";
		isActive = false;
		isInclusive = false;
	}
	
	/**
	 * Basic constructor. Rules are assumed exclusive and inactive.
	 */
	public RuleItem(String theWord){
		this();
		word = theWord;
	}
	
	/**
	 * Semi-complete constructor. Rules are assumed inactive
	 */
	public RuleItem(String theWord, boolean inclusive){
		this(theWord);
		isInclusive = inclusive;
	}
	
	// Public Accessors
	public RuleItem getRule(){
		return new RuleItem(word, isInclusive, isActive);
	}
	
	public boolean getStatus(){
		return isActive;
	}
	
	public boolean getType(){
		return isInclusive;
	}
	
	/**
	 * Complete constructor.
	 */
	public RuleItem(String theWord, boolean inclusive, boolean active){
		this(theWord, inclusive);
		isActive = active;
	}
	
	/**
	 * Activates the rule
	 */
	public void activate(){
		isActive = true;
	}
	
	/**
	 * Deactivates the rule
	 */
	public void deactivate(){
		isActive = false;
	}
	
	/**
	 * Changes the status of the rule (active > inactive or vice versa)
	 */
	public void flipStatus(){
		isActive = !isActive;
	}
	
	/**
	 * Changes the type of the rule (inclusive > exclusive or vice versa)
	 */
	public void flipType(){
		isInclusive = !isInclusive;
	}
	
	/**
	 * Applies the rule
	 * @param	message	a string that is searched for the presence of the word and is the subjected to the test
	 * @return	1 if the message is included, -1 if it is excluded; 0 if the rule is inactive 
	 */
	public int apply(String message){
		int output = 0; // The test is assumed inactive
		if(isActive){ // Otherwise nothing is tested
			if(message.indexOf(word) != -1){ // The word has been found
				if(isInclusive)
					output = 1;
				else
					output = -1;
			}
		}
		return output;
	}
}
