package introgeneticalgorithm;

public class Chromosome {
  /***********************************
   * static variables
   ***********************************/
    static int target = 53; // The number we're trying to calculate
    static int strand_size = 20; // The number of Genes in a Chromosome
    
  /***********************************
   * static functions
   ***********************************/
    public static void set_strand_size(int size){
    	strand_size = size; 
    }
    public static void set_target(int inTarget){
    	target = inTarget; 
	}
    public static int get_strand_size(){
    	return strand_size;
    }
    
  /***********************************
   * member variables
   ***********************************/
    protected Gene [] strand = new Gene[strand_size];
    protected int current_gene; //used by the recursive solve function, must be zeroed before use.
    protected double fitness;
    
  /***********************************
   * member functions
   ***********************************/
    public Chromosome(){
        for(int i = 0; i < strand_size; i++){
            strand[i] = new Gene((int)Math.floor(Math.random()*16));
        }
        calculate_fitness();
    }
    
    final public void calculate_fitness(){
        current_gene = 0;
        double solution = recursive_solve();
        if((target - solution) == 0){
            fitness = 1;
            return;
        }
        fitness = 1/(target - solution);
    }
    
    final public double get_fitness(){
        calculate_fitness();
        return fitness;
    }
    
    final public Gene get_gene(int i){
        return strand[i];
    }
    
    private double recursive_solve(){
        /********************************************************************************************************
         * Solves the valid genetic equation. Skips anything that doesn't fit the 3+5*3/2-1 format
         ********************************************************************************************************/
        double current_value = 0;
        double check_value = 0;
        
        if( (current_value = get_next_number()) == -1)
        	return current_value;
        
        while(++current_gene < strand_size){
            if(!Character.isDigit(strand[current_gene].get_gene())){
                switch(strand[current_gene].get_gene()){
                    case '*':
                    	if( (check_value = get_next_number()) == -1)
                    		return current_value;
                        current_value *= check_value;
                        break;
                    case '/':
                    	if( (check_value = get_next_number()) == -1)
                    		return current_value;
                        current_value /= current_value;
                        break;
                    case '+':
                        return current_value + recursive_solve();
                    case '-':
                        return current_value - recursive_solve();
                    default:
                        break;
                }
            }
        }
        return current_value;
    }
    
    private double get_next_number(){
    	double number_builder;
    	
    	// Skip all the garbage (any math operators or blanks at this point)
        do{
            if(++current_gene >= strand_size) // Make sure we're not at the end of the Chromosome
                return -1;
        }while(!Character.isDigit(strand[current_gene].get_gene()));
        
        number_builder = Character.getNumericValue(strand[current_gene].get_gene());
        
        // Grab all following numbers to build this number correctly
        while(++current_gene < strand_size && Character.isDigit(strand[current_gene].get_gene())){
        	number_builder *= 10;
        	number_builder += Character.getNumericValue(strand[current_gene].get_gene());
        }
        
        return number_builder;
    }
    
  
}
