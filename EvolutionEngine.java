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
	private RuleOrganism bestChoices[]; // Not needed if I order the population and take the first ones
	private RuleOrganism population[];
	private boolean isActive;
	
	private TrainingItem trainingSet[];
	private int filteredAmount; // How many items can be filtered
	private int trainingSize; // Total size of the training set
	
	// Accessors
	public void setTrainingSet(TrainingItem trainingItems[]){
		trainingSize = trainingItems.length;
		filteredAmount = 0;
		trainingSet = new TrainingItem[trainingSize];
		for(int i = 0; i < trainingSize; i++){
			trainingSet[i] = new TrainingItem(trainingItems[i].getText(), trainingItems[i].getIsFiltered());
			if(trainingSet[i].getIsFiltered())
				filteredAmount++;
		}
	}
	
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
	
	/**
	 * Run the evolution engine and return the best rule
	 * @param	target	The desired % of filtered messages to be guessed
	 * @param	maxGenerations	The maximum number of generations to be tested
	 */
	public RuleOrganism evolve(int target, int maxGenerations){
		RuleOrganism winner = null;
		// Chech that the engine is active and there's a viable training set
		if(isActive && trainingSize > 0){
			for(int g = 0; g < maxGenerations; g++){
				// Test all the rules and assign the fitness scores
				// Order the set by fitness, in ascending order
				// The first n are the best of the set, spawn and mutate them
			}
			// Pick a winner
		}
		return winner;
	}
}
