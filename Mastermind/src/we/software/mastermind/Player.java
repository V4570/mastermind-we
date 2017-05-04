package we.software.mastermind;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by ralph on 3/24/17.
 */
class Player {

	//General User Information
    private String name;
    private int highScore;
    private int leastTurns;
    
    //Current Game player information.
    
    private ArrayList<PlayingPegs> guess;
    private ResultPegs[] result;
    
    //Game Info
    private int numberOfPins = 4; 
    
    private int runningRounds;
    private int redPegsFound;
    private int whitePegsFound;
    private int highscore;
    
    private boolean guessing = true;
    
    
    //Constructor
    public Player(String aName){
    	
    	//Διάβασμα του ονόματος του παίκτη - !-Βάλτε το αλλού-!
    	/*String aName;
    	Scanner keyboard = new Scanner(System.in);
    	
    	System.out.println("Enter your name: ");
    	aName = keyboard.nextLine();
    	
    	if(aName.toLowerCase().equals("mastermind")){
    		System.out.println("Nice try!");
    		System.out.println("Please enter your name again: ");
    		aName = keyboard.nextLine();
    	}*/
    	
    	//Διαβάζει το PlayerDatabase.txt, αν υπάρχει ο χρήστης ανακτάει τα στοιχεία του
    	boolean found = false;
    	String checkName;
    	
    	File file = new File("PlayerDatabase.txt");
    	Scanner scanner = null;
    	
    	try {
    		scanner = new Scanner(file);
    		scanner.useDelimiter(", "); //Διαμόρφωση αρχείου
		} 
    	catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//Έλεγχος αν υπάρχει το όνομα ως 1η λέξη στη σειρά
    	while(scanner.hasNextLine() && !found){
    		checkName = scanner.next();
    		//Αν υπάρχει διάβασε και τα υπόλοιπα δεδομένα
    		if(checkName.equals(aName)){
    			found = true;
    			name = aName;
    			highScore = scanner.nextInt();
    			leastTurns = scanner.nextInt();
    		}
    	}
    	
    	scanner.close();
    
    	//Αν δεν υπάρχει ο χρήστης, τότε τον δημιουργεί
    	if(!found){
    		BufferedWriter bw = null;
    		FileWriter fw = null;
    		
    		try {

    			String data = aName + ", 0, 0\n";

    			fw = new FileWriter(file.getAbsoluteFile(), true);
    			bw = new BufferedWriter(fw);

    			try {
					bw.write(data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

    			System.out.println("Done");

    		} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {

    			try {

    				if (bw != null)
    					bw.close();

    				if (fw != null)
    					fw.close();

    			}catch (IOException ex) {

    				ex.printStackTrace();

    			}
			 }
    	}
    	
    	
        guess = new ArrayList<PlayingPegs>();
        result = new ResultPegs[numberOfPins];
        
        //Αρχικοποιηση default των selected pegs σε 0.
        for (int i=0; i<numberOfPins; i++){
        	guess.add(new PlayingPegs(0));
        }
        //Restoring to 0 all highscore values.
        runningRounds = 0;
        redPegsFound =0;
        whitePegsFound =0;
        
        
    	
    }
    
    public Player(){		//This is for calling the computer.
    		
    	name = "Mastemind";
    	
        guess = new ArrayList<PlayingPegs>();
        result = new ResultPegs[numberOfPins];
    }
    
    
    //Methods : 
    
    //Position in the array list (Input = 0-3), setting the colour of the selected peg. (Input = 1-6) 
    public void AddPin(int position , int colour){
    	guess.get(position).setColour(colour);
    	
    }
    
    //When the turn has ended, restores the guessing ArrayList to the default state.
    public void restoreGuessToDefault(){
    	for(int i = 0; i < guess.size(); i++){
    		guess.remove(i);
    	}
    }
    
    //Should increase every round by how many red pegs has the player scored. (Resets every time the player starts a new game.)
    public void AddRedPegs(int found){
    	redPegsFound += found;
    }
    //Should increase every round by how many white pegs has the player scored. (Resets every time the player starts a new game.)
    public void AddWhitePegs(int found){
    	whitePegsFound += found;
    }
    //Should increase every round by 1. (Resets every time the player starts a new game.)
    public void roundIncrease(){
    	runningRounds++;
    }
    //Returning the user highscore at the end of the round. 
    public double returnHighScore(){
    	return (redPegsFound * 10 + whitePegsFound * 5) * 1.25 - (0.05 * runningRounds);		//Placeholder values.
    }
    
    
    public void GuessToTxt(){
    	File file = new File("Guess.txt");
    	BufferedWriter bw = null;
		FileWriter fw = null;
		
		try {
			String data = "";
			
			for(PlayingPegs aPeg: guess)
				data += aPeg.getColour();

			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			try {
				bw.write(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Done");

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			}catch (IOException ex) {

				ex.printStackTrace();

			 }
		}
    }

    
    //Getters 
    
    public boolean getGuessing(){
    	return guessing;
    }

    public String getName(){
        return name;
    }
    
    /*Επιστρέφει τον πίνακα "guess" 
     *Το άλλαξα από getGuess -> getCode
     *Για να έχει το ίδιο όνομα με τη μέθοδο
     *στη κλάση Computer. 
    */
   
    public ArrayList<PlayingPegs> getCode(){
    	return guess;
    }
    
    
}
