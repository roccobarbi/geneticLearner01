/**
 * Defines an entire set of rule that can be subjected to evolutionary logics.
 */
package geneticLearner01;

/**
 * @author WT-Rocco
 *
 */
public class ruleOrganism {	
	// Fields
	private ruleItem rules[];
	private int fitness; // -1 = has not been evaluated
	private boolean isInitialised; // 
	
	// Default constructor
	public ruleOrganism(){
		rules = null;
		isInitialised = false;
		fitness = -1;
	}
	
	// Full constructor
	public ruleOrganism(ruleItem[] theRules){
		this();
		rules = new ruleItem[theRules.length];
		for(int i = 0; i < theRules.length; i++){
			rules[i] = theRules[i];
		}
		isInitialised = true;
	}
	
	/**
	 * Flips a rule type
	 */
	public void flipType(int item){
		if(item < rules.length)
			rules[item].flipType();
	}
	
	/**
	 * Flips a rule's active state
	 */
	public void flipStatus(int item){
		if(item < rules.length)
			rules[item].flipStatus();;
	}
	
	/**
	 * @return	a random mutation of the current organism.
	 */
	public ruleOrganism mutate(){
		ruleOrganism output = new ruleOrganism(rules);
		int mutationItem = (int)(Math.random() * rules.length);
		if((int)(Math.random() * 10) == 0){ // Flips are rarer than active state mutations
			output.flipType(mutationItem);
		} else {
			output.flipStatus(mutationItem);
		}
		return output;
	}
}
