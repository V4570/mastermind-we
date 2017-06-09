package we.software.mastermind;

import java.util.ArrayList;

/**
 * Created by bill on 3/24/17.
 */
public class Computer extends Player{
	
    
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
        	    break;
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

    //Fill the arraylist with different colors
    public void easyAlgorithm(){

		for(int i=0; i< super.numberOfPins; i++){

		    int rand = 1+ (int)(Math.random()*6);

		    if(!CodeToBreak.contains(rand)){

		        CodeToBreak.add(rand);
            }
            else{
		        i--;
            }
        }
    }
    
    
  //Γεμιζει τον πινακα με Pegs που μπορει να εχουν και ιδιο χρωμα πολλες φορες
    public void mediumAlgorithm(){
    	
		for (int i = 0; i<super.numberOfPins; i++) {

		    int rand = 1+ (int)(Math.random()*6);
			CodeToBreak.add(rand);
	    }
		
    }
    
  //Γεμιζει τον πινακα με Pegs που μπορει να εχουν και ιδιο χρωμα πολλες φορες, και κενα
    public void hardAlgorithm(){
    	
    	
		for (int i = 0; i < super.numberOfPins; i++) {
			 CodeToBreak.add((int)(Math.random()*7));
	        
	    }
				

    }
    
  
}
