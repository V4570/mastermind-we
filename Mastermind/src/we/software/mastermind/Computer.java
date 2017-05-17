package we.software.mastermind;

import java.util.ArrayList;

/**
 * Created by bill on 3/24/17.
 */
class Computer extends Player{
	
    
	// Πινακας που περιεχει το Code 
    private ArrayList<Integer> CodeToBreak = new ArrayList<Integer>();
    
    //Easy Difficulty : Code to crack for the player has 6 colours, no duplicates , no NULL colour.
    //Medium Difficulty : Code to crack for the player has 6 colours, has duplicates , no NULL colour.
    //Hard Difficulty : Code to crack for the player has 6 colours, has duplicates , has NULL colour.
    
    

    public Computer(int difficultyChoise) {

        super();
        
        
        switch (difficultyChoise){
        case 0:
            easyAlgorithm();
            break;
        case 1:
        	mediumAlgorithm();
            break;
        case 2:
        	hardAlgorithm();
        }
        	
    }
    
  //Colour - Numbers
  //0 - Default ( no colour selected)- blank
  //1 - Red
  //2 - Green
  //3 - Blue
  //4 - Yellow
  //5 - White 
  //6 - Black 

    //Γεμιζει τον πινακα με Pegs Διαφορετικων χροματων
    public void easyAlgorithm(){
    	
    	
		for (int i = 0; i < super.numberOfPins; i++) {
			//Τυχαιος αροιθμος απο το 1-6
	        CodeToBreak.add(1+ (int)(Math.random()*6));
            //ελεγχει για το εαν υπαρχει ειδη ο αροιθμος στον πινακα
	        for (int j = 0; j < i; j++) {
	            if (CodeToBreak.get(i).equals(CodeToBreak.get(j))) {
	            	//αν υπαρχει παει το i μια θεση πισω
	                i--;
	                break;
	            }
	        }   
	    }	
    }
    
    
  //Γεμιζει τον πινακα με Pegs που μπορει να εχουν και ιδιο χρωμα πολλες φορες
    public void mediumAlgorithm(){
    	
		for (int i = 0; i<super.numberOfPins; i++) {
			CodeToBreak.add(1+ (int)(Math.random()*6));
	        
	    }
		
    }
    
  //Γεμιζει τον πινακα με Pegs που μπορει να εχουν και ιδιο χρωμα πολλες φορες, και κενα
    public void hardAlgorithm(){
    	
    	
		for (int i = 0; i < super.numberOfPins; i++) {
			 CodeToBreak.add((int)(Math.random()*7));
	        
	    }
				

    }
    
   public ArrayList<Integer> getCode(){
	   return CodeToBreak;
	   
   }
}
