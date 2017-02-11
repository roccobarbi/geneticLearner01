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
	
	// Public accessors
	public ruleItem getRule(int item){
		ruleItem output = rules[item].getRule();
		return output;
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
	 * Copies all the rules for the current object
	 * 
	 * @return	an array of ruleItem objects that copies the current one
	 */
	private ruleItem[] copyRules(){
		ruleItem output[] = new ruleItem[rules.length];
		for(int i = 0; i < rules.length; i++){
			output[i] = rules[i].getRule();
		}
		return output;
	}
	
	/**
	 * @return	a random mutation of the current organism.
	 */
	public ruleOrganism mutate(){
		ruleItem theRules[] = copyRules();
		ruleOrganism output = new ruleOrganism(theRules);
		int mutationItem = (int)(Math.random() * rules.length);
		if((int)(Math.random() * 10) == 0){ // Flips are rarer than active state mutations
			output.flipType(mutationItem);
		} else {
			output.flipStatus(mutationItem);
		}
		return output;
	}
	
	/**
	 * Spawns a new generation that starts with the genetic code for the current organism
	 * @param	organism	a second ruleOrganism
	 * @return	a new organism that is a crossbreed of the current organism and the parameter's one
	 */
	public ruleOrganism spawn(ruleOrganism organism){
		int spawnPoint = (int) (Math.random() * rules.length);
		ruleItem theRules[] = new ruleItem[rules.length];
		for(int i = 0; i < spawnPoint; i++){
			theRules[i] = getRule(i);
		}
		for(int i = spawnPoint; i < rules.length; i++){
			theRules[i] = organism.getRule(i);
		}
		return new ruleOrganism(theRules);
	}
}
