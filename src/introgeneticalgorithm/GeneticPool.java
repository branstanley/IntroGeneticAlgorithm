package introgeneticalgorithm;

public class GeneticPool {
  /***********************************
   * static variables
   ***********************************/
    private static double cross_over_rate = 0.7;
    private static double mutation_rate = 0.003;
    
  /***********************************
   * member variables
   ***********************************/
    private int pool_size = 100; //number of chromosomes in the pool
    private Hierarchy_Wrapper [] hierarchy = new Hierarchy_Wrapper[pool_size];
    private double fitness_pool_size; //current total fitness score, used for making the heriarchy percents
    
  /***********************************
   * member functions
   ***********************************/
    public GeneticPool(){
        for(int i = 0; i < pool_size; ++i){
            hierarchy[i] = new Hierarchy_Wrapper(new Chromosome());
        }
    }
    
    /*
     * Calls the calculate_fitness function for all Chromosomes, if a fitness score of 1 is found, we return it. 
     * Otherwise we then establish the current hierarchy of chromosomes, giving them a weighted percent,
     * and finally sort them by their weighted percents.
     * 
     * @return A Chromosome that's fitness is 1, otherwise null
     */
    final public Chromosome establish_heirarchy(){
        fitness_pool_size = 0;
        
        // Add up all the fitnesses
        for(int i = 0; i < pool_size; ++i){
        	hierarchy[i].get_chromosome().calculate_fitness();
        	if(hierarchy[i].get_chromosome().get_fitness() == 1)
        		return hierarchy[i].get_chromosome(); // We've found a match, we're done.
        	
            fitness_pool_size += Math.abs(hierarchy[i].get_chromosome().get_fitness());
        }
        
        // set up all the percentile sizes
        for(int i = 0; i < pool_size; ++i)
            hierarchy[i].percent = 100 * Math.abs(hierarchy[i].get_chromosome().get_fitness() / fitness_pool_size);
        
        // Sort the hierarchy
        for(int i = 0; i < pool_size; ++i){
            for(int j = i; j < pool_size; ++j){
                if(hierarchy[j].percent > hierarchy[i].percent){
                    Hierarchy_Wrapper temp = hierarchy[j];
                    hierarchy[j] = hierarchy[i];
                    hierarchy[i] = temp;
                }
            }
        }
        return null;
    }
    
    /*
     * This is the main driver method for the Genetic Pool
     */
    public void evolution(){
        Chromosome t1, t2;
        Chromosome king; // A Chromosome with fitness == 1
        while((king = establish_heirarchy()) == null){
	        //grab two random Chromosomes, for evolution
	        t1 = get_chromosome(null);
	        t2 = get_chromosome(t1);
	        
	        if(Math.random() < cross_over_rate){
	            int i = (int)Math.floor(Math.random() * Chromosome.get_strand_size()); // Pick a Gene to cross over with
	            int j = (int)Math.floor(Math.random() * 4); // Pick a bit to cross over
	            Gene.swap_bits_at(t1.get_gene(i), t2.get_gene(i), j);
	            for(int k = ++i; k < Chromosome.get_strand_size(); ++k){
	                Gene.swap_bits_at(t1.get_gene(k), t2.get_gene(k), 0);
	            }
	        }
	        
	        for(int i = 0; i < Chromosome.get_strand_size(); ++i){
	            for(int j = 0; j < 4; ++j){
	                if(Math.random() <= mutation_rate){
	                    t1.get_gene(i).flip_bit(j);
	                }
	                if(Math.random() <= mutation_rate){
	                    t2.get_gene(i).flip_bit(j);
	                }
	            } // End bit loop
	        } // End Gene loop
        } // End While loop
        
        System.out.println("The king is: ");
        System.out.println(king.get_chromosome_string());
        System.out.println("Value: " + king.recursive_solve());
        
    } // End evolution
    
    protected Chromosome get_chromosome(Chromosome in){
        double temp_precent = Math.random() * 100; // Random percentile number
        int i;
        double sum = 0;
        
        // Go through the hierarchy
        for(i = 0; temp_precent > 0 && i < pool_size; ++i){
            temp_precent -= hierarchy[i].percent;
            sum += hierarchy[i].percent;
        
        }
        --i; //i needs to be corrected
        
        if(in == hierarchy[i].get_chromosome()){
            return hierarchy[i+1].get_chromosome();
        }
        return hierarchy[i].get_chromosome();
    }
    
    private class Hierarchy_Wrapper{
        public double percent;  //share of the 100% fitness pool
        private Chromosome chromosome;
        
        public Hierarchy_Wrapper(Chromosome in){
            chromosome = in;
        }
        
        public Chromosome get_chromosome(){
        	return chromosome;
        }
        
    }
}
