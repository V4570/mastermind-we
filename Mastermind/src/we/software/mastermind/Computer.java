package we.software.mastermind;

/**
 * Created by bill on 3/24/17.
 */
public class Computer extends Player{
	
    
    //Easy Difficulty : Code to crack for the player has 6 colours, no duplicates , no NULL colour.
    //Medium Difficulty : Code to crack for the player has 6 colours, has duplicates , no NULL colour.    
    

    public Computer(int difficultyChoise) {
        super();

        switch (difficultyChoise){
            case 0:
                easyAlgorithm();
                break;
            case 1:
        	    mediumAlgorithm();
        	    break;
        }
    }

    //Fill the arraylist with different colors
    public void easyAlgorithm(){

		for(int i=0; i< super.numberOfPins; i++){

		    int rand = 1+ (int)(Math.random()*6);

		    if(!codeToBreak.contains(rand)){

		        codeToBreak.add(rand);
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
			codeToBreak.add(rand);
	    }
		
    }
    
    
  
}
