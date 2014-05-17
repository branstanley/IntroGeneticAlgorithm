package introgeneticalgorithm;

public class Chromosome {
  /***********************************
   * static variables
   ***********************************/
    static int target = 53;
    static int strand_size = 20;
    
  /***********************************
   * static functions
   ***********************************/
    public static void set_strand_size(int size){ strand_size = size; }
    public static void set_target(int inTarget){ target = inTarget; }
    public static int get_strand_size(){ return strand_size; }
    
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
        double temp = 0;
        while(current_gene < strand_size && !Character.isDigit(strand[current_gene].get_gene())){ ++current_gene; }
        if(current_gene >= strand_size)
            return 0;
        
        temp = (double)strand[current_gene].get_gene();
        
        int i = current_gene;
        while(++current_gene < strand_size){
            if(!Character.isDigit(strand[current_gene].get_gene())){
                switch(strand[current_gene].get_gene()){
                    case '*':
                        temp *= get_next_number();
                        break;
                    case '/':
                        temp /= get_next_number();
                        break;
                    case '+':
                        ++current_gene;
                        return temp + recursive_solve();
                    case '-':
                        ++current_gene;
                        return temp - recursive_solve();
                    default:
                        break;
                }
            }
        }
        return temp;
    }
    
    private double get_next_number(){
        while(current_gene > strand_size && !Character.isDigit(strand[current_gene].get_gene())){ current_gene++; }
        return Character.getNumericValue(strand[current_gene].get_gene());
    }
    
  
}
