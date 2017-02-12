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
	private static final int POP_SIZE = (int) Math.pow(BEST_SIZE, 2) * 2;
	
	// Private fields
	private RuleOrganism winner;
	private RuleOrganism bestChoices[]; // Not needed if I order the population and take the first ones
	private RuleOrganism population[];
	private boolean isActive;
	
	private TrainingItem trainingSet[];
	private int trainingSize; // Total size of the training set
	private int qtyFiltered; // How many items should be filtered
	private int qtyPassed;
	
	// Accessors
	public void setTrainingSet(TrainingItem trainingItems[]){
		trainingSize = trainingItems.length;
		qtyFiltered = 0;
		qtyPassed = 0;
		trainingSet = new TrainingItem[trainingSize];
		for(int i = 0; i < trainingSize; i++){
			trainingSet[i] = new TrainingItem(trainingItems[i].getText(), trainingItems[i].getIsFiltered());
			if(trainingSet[i].getIsFiltered())
				qtyFiltered++;
		}
		qtyPassed = trainingSize - qtyFiltered;
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
		int cf = 0; // Counter of elements filtered correctly
		int cn = 0; // Counter of elements filtered by error
		float fitness = 0; // Temporary storage for each rules' fitness 
		RuleOrganism tempPop [] = new RuleOrganism[POP_SIZE];
		// Check that the engine is active and there's a viable training set
		if(isActive && trainingSize > 0){
			for(int g = 0; g < maxGenerations; g++){
				if(g > 0){
					// The first n are the best of the set, spawn and mutate them
					for(int r = 0; r < BEST_SIZE; r++){
						for(int s = 0; s < BEST_SIZE; s++){
							tempPop[(r * 3) + s] = population[r].spawn(population[s]);
						}
					}
					for(int r = (POP_SIZE / 2); r < POP_SIZE; r++){
						tempPop[r] = tempPop[r - (POP_SIZE / 2)].mutate();
					}
				}
				// Test all the rules and assign the fitness scores
				for(int r = 0; r < POP_SIZE; r++){
					cn = 0; cf = 0; fitness = 0;
					for(int t = 0; t < trainingSize; t++){
						if(population[r].filter(trainingSet[t].getText())){
							if(trainingSet[t].getIsFiltered())
								cf++;
							else
								cn++;
						}
					}
					fitness = (cf / qtyFiltered) - (cn / qtyPassed);
					population[r].setFitness(fitness);
				}
				// Order the set by fitness, in descending order
				sort(population);
			}
			winner = population[0];
		}
		return winner;
	}
	
	/**
	 * Sorts the current population by fitness
	 */
	private void sort(RuleOrganism currentSet[]){
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
