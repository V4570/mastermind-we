package we.software.mastermind;

import java.util.ArrayList;

/**
 * Created by ralph on 3/24/17.
 */
public class Player {

    private String name;
    private ArrayList<NormalPeg> guess;
    private SmallPeg[] outcome;
    private int numberOfPins;
    private int totalGuesses;
    private int[] points;
    private int currentRound;
    private boolean guessing = true;

    public Player(String name, int numberOfPins){
        this.name = name;
        this.numberOfPins = numberOfPins;
        guess = new ArrayList();
        outcome = new SmallPeg[numberOfPins];
    }
    
    public void addPin(NormalPeg selectedPin){

    	if(guessing){
    		for(int i = 0; i < guess.size(); i++){
        		if(guess.get(i).getXPos() == selectedPin.getXPos() && guess.get(i).getYPos() == selectedPin.getYPos()){
        			guess.set(i, selectedPin);
        			return;
        		}
        	}
        	guess.add(selectedPin);
    	}
    	else{
    	}
    }
    
    public void endGuess(){
    	for(int i = 0; i < guess.size(); i++){
    		guess.remove(i);
    	}
    }

    public Peg selectPin(){

    }

    public boolean getGuessing(){
    	return guessing;
    }

    public String getName(){
        return name;
    }
}
