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
    private int numberOfPins = 4; //Maybe change this to a global, or move it. 
    private int totalGuesses;
    private int[] points;
    private int currentRound;
    
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
        for (PlayingPegs aPeg : guess){
        	aPeg.setColour(0);
        }
        
    	
    }
    
    public Player(){		//This is for calling the computer.
    		
    	name = "Mastemind";
    	
        guess = new ArrayList<PlayingPegs>();
        result = new ResultPegs[numberOfPins];
    }
    
    
    //Methods : 

    public void AddPin(int position , int colour){	//Position in the array list (Input = 0-3), setting the colour of the selected peg. (Input = 1-6) 
    	guess.get(position).setColour(colour);
    	
    }
    
    public void endGuess(){
    	for(int i = 0; i < guess.size(); i++){
    		guess.remove(i);
    	}
    }

    /*public Peg selectPin(){

    }*/

    public boolean getGuessing(){
    	return guessing;
    }

    public String getName(){
        return name;
    }
    
    public ArrayList<PlayingPegs> getGuess(){
    	return guess;
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
}
