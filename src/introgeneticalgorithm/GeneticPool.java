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
        
        establish_heirarchy();
    }
    
    final public void establish_heirarchy(){
        fitness_pool_size = 0;
        
        // Add up all the fitnesses
        for(int i = 0; i < pool_size; ++i)
            fitness_pool_size += Math.abs(hierarchy[i].get_chromosome().get_fitness());
        
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
    }
    
    /*
     * This is the main driver method for the Genetic Pool
     */
    public void evolution(){
        Chromosome t1, t2;

        //grab two random Chromosomes, for evolution
        t1 = get_chromosome(null);
        t2 = get_chromosome(t1);
        
        if(Math.random() < cross_over_rate){
            int i = (int)Math.floor(Math.random() * Chromosome.get_strand_size());
            int j = (int)Math.floor(Math.random() * 4);
            Gene.swap_bits_at(t1.get_gene(i), t2.get_gene(i), j);
            System.out.println("Crossover beginning in gene " + i + " bit " + j);;
            for(int k = ++i; k < Chromosome.get_strand_size(); ++k){
                Gene.swap_bits_at(t1.get_gene(k), t2.get_gene(k), 0);
            }
        }
        
        for(int i = 0; i < Chromosome.get_strand_size(); ++i){
            for(int j = 0; j < 4; ++j){
                if(Math.random() <= mutation_rate){
                    t1.get_gene(i).flip_bit(j);
                    System.out.println("t1 has mutated in gene " + i + " bit " + j);
                }
                if(Math.random() <= mutation_rate){
                    t2.get_gene(i).flip_bit(j);
                    System.out.println("t2 has mutated in gene " + i + " bit " + j);
                }
            }
        }
        
    }
    
    protected Chromosome get_chromosome(Chromosome in){
        double temp_precent = Math.random() * 100; // Random percentile number
        System.out.println("t is " + temp_precent);
        int i;
        double sum = 0;
        
        // Go through the hierarchy
        for(i = 0; temp_precent > 0 && i < pool_size; ++i){
            temp_precent -= hierarchy[i].percent;
            sum += hierarchy[i].percent;
            System.out.println(i + ". " + hierarchy[i].percent + "%");
        
        }
        --i; //i needs to be corrected
        System.out.println("Sum is " + sum);
        System.out.println(" index " + i + "is selected with t "+ temp_precent + " as result");
        
        if(in == hierarchy[i].get_chromosome()){
            return get_chromosome(in);
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
