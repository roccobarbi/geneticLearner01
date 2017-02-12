/**
 * Defines an entire set of rule that can be subjected to evolutionary logics.
 */
package geneticLearner01;

/**
 * @author WT-Rocco
 *
 */
public class RuleOrganism {	
	// Fields
	private RuleItem rules[];
	private float fitness; // -1 = has not been evaluated
	private boolean isInitialised; // 
	
	// Default constructor
	public RuleOrganism(){
		rules = null;
		isInitialised = false;
		fitness = -1;
	}
	
	// Full constructor
	public RuleOrganism(RuleItem[] theRules){
		this();
		rules = new RuleItem[theRules.length];
		for(int i = 0; i < theRules.length; i++){
			rules[i] = theRules[i];
		}
		isInitialised = true;
	}
	
	// Public accessors
	public RuleItem getRule(int item){
		RuleItem output = rules[item].getRule();
		return output;
	}
	
	public void setFitness(float theFitness){
		fitness = theFitness;
	}
	
	public float getFitness(){
		return fitness;
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
	 * @return	an array of RuleItem objects that copies the current one
	 */
	private RuleItem[] copyRules(){
		RuleItem output[] = new RuleItem[rules.length];
		for(int i = 0; i < rules.length; i++){
			output[i] = rules[i].getRule();
		}
		return output;
	}
	
	/**
	 * @return	a random mutation of the current organism.
	 */
	public RuleOrganism mutate(){
		RuleItem theRules[] = copyRules();
		RuleOrganism output = new RuleOrganism(theRules);
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
	 * @param	organism	a second RuleOrganism
	 * @return	a new organism that is a crossbreed of the current organism and the parameter's one
	 */
	public RuleOrganism spawn(RuleOrganism organism){
		int spawnPoint = (int) (Math.random() * rules.length);
		RuleItem theRules[] = new RuleItem[rules.length];
		for(int i = 0; i < spawnPoint; i++){
			theRules[i] = getRule(i);
		}
		for(int i = spawnPoint; i < rules.length; i++){
			theRules[i] = organism.getRule(i);
		}
		return new RuleOrganism(theRules);
	}
	
	/**
	 * Applies the rules for this organism to a string
	 * @param	message	the message that needs to be tested against the rules
	 * @return	true if the message should be blocked, false if it should be allowed
	 */
	public boolean filter(String message){
		boolean output = false; // The message is assumed good
		if(isInitialised){ // Otherwise there are no rules
			// Check the negative rules
			for(int i = 0; i < rules.length && !output; i++){
				if(rules[i].apply(message) < 0)
					output = true;
			}
			// Correct if needed with the positive rules
			if(output){
				for(int i = 0; i < rules.length && output; i++){
					if(rules[i].apply(message) > 0)
						output = true;
				}
			}
		}
		return output;
	}
	
	/**
	 * Sorts a population by fitness
	 */
	public static void sort(RuleOrganism currentSet[]){
		if(currentSet.length > 1){
			// Divide
			int half = currentSet.length / 2;
			RuleOrganism firstHalf[] = new RuleOrganism[half];
			RuleOrganism secondHalf[] = new RuleOrganism[currentSet.length - half];
			for(int i = 0; i < half; i++)
				firstHalf[i] = currentSet[i];
			for(int i = 0; i < secondHalf.length; i++)
				secondHalf[i] = currentSet[i + half];
			// Order recursively
			sort(firstHalf);
			sort(secondHalf);
			// Merge
			int index = 0, indexFirst = 0, indexSecond = 0;
			while(indexFirst < firstHalf.length && indexSecond < secondHalf.length){
				if(firstHalf[indexFirst].getFitness() > secondHalf[indexSecond].getFitness()){
					currentSet[index] = firstHalf[indexFirst];
					index++;
					indexFirst++;
				}
				else{
					currentSet[index] = secondHalf[indexSecond];
					index++;
					indexSecond++;
				}
			}
			for(int j = indexFirst; j < firstHalf.length; j++){
				currentSet[index] = firstHalf[j];
				index++;
			}
			for(int j = indexSecond; j < secondHalf.length; j++){
				currentSet[index] = secondHalf[j];
				index++;
			}
		}
	}
	
}
