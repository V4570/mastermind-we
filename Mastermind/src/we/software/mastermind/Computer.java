package we.software.mastermind;

import java.util.ArrayList;

/**
 * Created by bill on 3/24/17.
 */
class Computer extends Player{
	
    private int NumberOfPegs = 4;
	// Πινακας που περιεχει το Code 
    private ArrayList<PlayingPegs> CodeToBreak = new ArrayList<PlayingPegs>();
    
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
    	
    	
    	int[] ColorCombo = new int[4];
		for (int i = 0; i < ColorCombo.length; i++) {
			//Τυχαιος αροιθμος απο το 1-6
	        ColorCombo[i] =1+ (int)(Math.random()*6);
            //ελεγχει για το εαν υπαρχει ειδη ο αροιθμος στον πινακα
	        for (int j = 0; j < i; j++) {
	            if (ColorCombo[i] == ColorCombo[j]) {
	            	//αν υπαρχει παει το i μια θεση πισω
	                i--;
	                break;
	            }
	        }   
	    }
		//Δημιουργει τα Pegs με τα χροματα που βγηκαν απο τον παραπανω αλγοριθμο
		PlayingPegs a= new PlayingPegs(ColorCombo[0]);
        PlayingPegs b= new PlayingPegs(ColorCombo[1]);
        PlayingPegs c= new PlayingPegs(ColorCombo[2]);
        PlayingPegs d= new PlayingPegs(ColorCombo[3]);
        
      //Βαζει τα Pegs στον πινακα που περιεχει το Code
		CodeToBreak.add(a);
    	CodeToBreak.add(b);
    	CodeToBreak.add(c);
    	CodeToBreak.add(d);
    	

    	

    }
  //Γεμιζει τον πινακα με Pegs που μπορει να εχουν και ιδιο χρωμα πολλες φορες
    public void mediumAlgorithm(){
    	int[] ColorCombo = new int[4];
		for (int i = 0; i < ColorCombo.length; i++) {
			ColorCombo[i] =1+ (int)(Math.random()*6);
	        
	    }
    	
    	PlayingPegs a= new PlayingPegs(ColorCombo[0]);
        PlayingPegs b= new PlayingPegs(ColorCombo[1]);
        PlayingPegs c= new PlayingPegs(ColorCombo[2]);
        PlayingPegs d= new PlayingPegs(ColorCombo[3]);
        
        
		CodeToBreak.add(a);
    	CodeToBreak.add(b);
    	CodeToBreak.add(c);
    	CodeToBreak.add(d);
    }
    
  //Γεμιζει τον πινακα με Pegs που μπορει να εχουν και ιδιο χρωμα πολλες φορες, και κενα
    public void hardAlgorithm(){
    	
    	int[] ColorCombo = new int[4];
		for (int i = 0; i <ColorCombo.length; i++) {
			ColorCombo[i] =(int)(Math.random()*7);
	        
	    }
    	
    	PlayingPegs a= new PlayingPegs(ColorCombo[0]);
        PlayingPegs b= new PlayingPegs(ColorCombo[1]);
        PlayingPegs c= new PlayingPegs(ColorCombo[2]);
        PlayingPegs d= new PlayingPegs(ColorCombo[3]);
        
        
		CodeToBreak.add(a);
    	CodeToBreak.add(b);
    	CodeToBreak.add(c);
    	CodeToBreak.add(d);

    }
    
   public ArrayList<PlayingPegs> getCode(){
	   return CodeToBreak;
	   
   }
}
