package we.software.mastermind;

import java.util.ArrayList;

/**
 * Created by bill on 3/24/17.
 */
class Computer extends Player{
	
    private int NumberOfPegs = 4;
	// Πινακας που περιεχει το Code 
    private ArrayList<PlayingPegs> CodeToBreak = new ArrayList<PlayingPegs>();
    
    
    

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
    
    
   
    

    //Γεμιζει τον πινακα με Pegs Διαφορετικων χροματων
    public void easyAlgorithm(){
    	
    	
    	int[] ar1 = new int[4];
		for (int i = 0; i < ar1.length; i++) {
			//Τυχαιος αροιθμος απο το 1-6
	        ar1[i] =1+ (int)(Math.random()*6);
            //ελεγχει για το εαν υπαρχει ειδη ο αροιθμος στον πινακα
	        for (int j = 0; j < i; j++) {
	            if (ar1[i] == ar1[j]) {
	            	//αν υπαρχει παει το i μια θεση πισω
	                i--;
	                break;
	            }
	        }   
	    }
		//Δημιουργει τα Pegs με τα χροματα που βγηκαν απο τον παραπανω αλγοριθμο
		PlayingPegs a= new PlayingPegs(ar1[0]);
        PlayingPegs b= new PlayingPegs(ar1[1]);
        PlayingPegs c= new PlayingPegs(ar1[2]);
        PlayingPegs d= new PlayingPegs(ar1[3]);
        
      //Βαζει τα Pegs στον πινακα που περιεχει το Code
		CodeToBreak.add(a);
    	CodeToBreak.add(b);
    	CodeToBreak.add(c);
    	CodeToBreak.add(d);
    	

    	

    }
  //Γεμιζει τον πινακα με Pegs που μπορει να εχουν και ιδιο χρωμα πολλες φορες
    public void mediumAlgorithm(){
    	int[] ar1 = new int[4];
		for (int i = 0; i < ar1.length; i++) {
	        ar1[i] =1+ (int)(Math.random()*6);
	        
	    }
    	
    	PlayingPegs a= new PlayingPegs(ar1[0]);
        PlayingPegs b= new PlayingPegs(ar1[1]);
        PlayingPegs c= new PlayingPegs(ar1[2]);
        PlayingPegs d= new PlayingPegs(ar1[3]);
        
        
		CodeToBreak.add(a);
    	CodeToBreak.add(b);
    	CodeToBreak.add(c);
    	CodeToBreak.add(d);
    }
    
  //Γεμιζει τον πινακα με Pegs που μπορει να εχουν και ιδιο χρωμα πολλες φορες, και κενα
    public void hardAlgorithm(){
    	
    	int[] ar1 = new int[4];
		for (int i = 0; i < ar1.length; i++) {
	        ar1[i] =(int)(Math.random()*6);
	        
	    }
    	
    	PlayingPegs a= new PlayingPegs(ar1[0]);
        PlayingPegs b= new PlayingPegs(ar1[1]);
        PlayingPegs c= new PlayingPegs(ar1[2]);
        PlayingPegs d= new PlayingPegs(ar1[3]);
        
        
		CodeToBreak.add(a);
    	CodeToBreak.add(b);
    	CodeToBreak.add(c);
    	CodeToBreak.add(d);

    }
}
