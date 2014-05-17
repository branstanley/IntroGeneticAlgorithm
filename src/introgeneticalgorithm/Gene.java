package introgeneticalgorithm;

public class Gene {
  /***********************************
   * static functions
   ***********************************/
    public static void swap_bits_at(Gene g1, Gene g2, int bit){
        boolean b;
        for(int i = bit; i < 4; ++i){
            b = g1.code[i];
            g1.code[i] = g2.code[i];
            g2.code[i] = b;
        }
    }
    
    
  /***********************************
   * member variables
   ***********************************/
    protected boolean [] code;
    
    
  /***********************************
   * member functions
   ***********************************/
    public Gene(char in){
        switch(in){
            case '0':
                code = new boolean[]{ false, false, false, false };
                break;
            case '1':
                code = new boolean[]{ false, false, false, true };
                break;
            case '2':
                code = new boolean[]{ false, false, true, false };
                break;
            case '3':
                code = new boolean[]{ false, false, true, true };
                break;
            case '4':
                code = new boolean[]{ false, true, false, false };
                break;
            case '5':
                code = new boolean[]{ false, true, false, true };
                break;
            case '6':
                code = new boolean[]{ false, true, true, false };
                break;
            case '7':
                code = new boolean[]{ false, true, true, true };
                break;
            case '8':
                code = new boolean[]{ true, false, false, false };
                break;
            case '9':
                code = new boolean[]{ true, false, false, true };
                break;
            case '+':
                code = new boolean[]{ true, false, true, false };
                break;
            case '-':
                code = new boolean[]{ true, false, true, true };
                break;
            case '*':
                code = new boolean[]{ true, true, false, false };
                break;
            case '/':
                code = new boolean[]{ true, true, false, true };
                break;
            default:
                code = new boolean[]{ true, true, true, true };
                break;
        
        };
    }
    public Gene(int in){
        switch(in){
            case 0:
                code = new boolean[]{ false, false, false, false };
                break;
            case 1:
                code = new boolean[]{ false, false, false, true };
                break;
            case 2:
                code = new boolean[]{ false, false, true, false };
                break;
            case 3:
                code = new boolean[]{ false, false, true, true };
                break;
            case 4:
                code = new boolean[]{ false, true, false, false };
                break;
            case 5:
                code = new boolean[]{ false, true, false, true };
                break;
            case 6:
                code = new boolean[]{ false, true, true, false };
                break;
            case 7:
                code = new boolean[]{ false, true, true, true };
                break;
            case 8:
                code = new boolean[]{ true, false, false, false };
                break;
            case 9:
                code = new boolean[]{ true, false, false, true };
                break;
            case 10:
                code = new boolean[]{ true, false, true, false };
                break;
            case 11:
                code = new boolean[]{ true, false, true, true };
                break;
            case 12:
                code = new boolean[]{ true, true, false, false };
                break;
            case 13:
                code = new boolean[]{ true, true, false, true };
                break;
            case 14:
                code = new boolean[]{ true, true, true, false };
                break;
            case 15:
                code = new boolean[]{ true, true, true, true };
                break;
        
        };
    }
    public char get_gene(){
        
        if(!code[0] && !code[1] && !code[2] && !code[3]){
            return '0';
        }
        if(!code[0] && !code[1] && !code[2] && code[3]){
            return '1';
        }
        if(!code[0] && !code[1] && code[2] && !code[3]){
            return '2';
        }
        if(!code[0] && !code[1] && code[2] && code[3]){
            return '3';
        }
        if(!code[0] && code[1] && !code[2] && !code[3]){
            return '4';
        }
        if(!code[0] && code[1] && !code[2] && code[3]){
            return '5';
        }
        if(!code[0] && code[1] && code[2] && !code[3]){
            return '6';
        }
        if(!code[0] && code[1] && code[2] && code[3]){
            return '7';
        }
        if(code[0] && !code[1] && !code[2] && !code[3]){
            return '8';
        }
        if(code[0] && !code[1] && !code[2] && code[3]){
            return '9';
        }
        if(code[0] && !code[1] && code[2] && !code[3]){
            return '+';
        }
        if(code[0] && !code[1] && code[2] && code[3]){
            return '-';
        }
        if(code[0] && code[1] && !code[2] && !code[3]){
            return '*';
        }
        if(code[0] && code[1] && !code[2] && code[3]){
            return '/';
        }
        return ' ';
    } 
    
    
    
    public void flip_bit(int bit){
        code[bit] = !code[bit];
    }
}
