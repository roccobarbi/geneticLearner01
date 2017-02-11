/**
 * 
 */
package geneticLearner01;

/**
 * Engine for the evolution of the best algorithm.
 * It can be created out of a set of words, a single organism or a set of organisms
 *
 * @author WT-Rocco
 *
 */

public class EvolutionEngine {
	// Constants
	private static final int BEST_SIZE = 3;
	private static final int POP_SIZE = (int) Math.pow(2, BEST_SIZE) * 2;
	
	// Private fields
	private RuleOrganism winner;
	private RuleOrganism bestChoices[];
	private RuleOrganism population[];
	private boolean isActive;
	
	// Default constructor
	public EvolutionEngine(){
		isActive = false;
		bestChoices = new RuleOrganism[BEST_SIZE];
		population = new RuleOrganism[POP_SIZE];
	}
	
	// Basic constructor: word list
	public EvolutionEngine(String words[]){
		this();
		RuleItem basicRules[] = new RuleItem[words.length];
		boolean isInclusive = true;
		boolean isActive = false;
		for(int i = 0; i < POP_SIZE; i++){
			// Organisms are created randomly, favouring inactive and inclusive rules
			for(int k = 0; k < words.length; k++){
				isInclusive = (int)(Math.random() * 3) == 0 ? false : true;
				isActive = (int)(Math.random() * 4) == 0 ? true : false;
				basicRules[i] = new RuleItem(words[k], isInclusive, isActive);
			}
			population[i] = new RuleOrganism(basicRules);
		}
		isActive = true;
	}
	
	// TODO: the other constructors
}
