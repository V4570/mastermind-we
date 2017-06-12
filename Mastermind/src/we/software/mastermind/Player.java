package we.software.mastermind;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {

	//General User Information
    protected String username;
    private int totalScore=0;
    
    //Current Game player information.
    private boolean guessing = true;
    protected ArrayList<Integer> codeToBreak;
    protected ArrayList<Integer> guess;
    protected boolean codeMaker;
    
    //Game Info
    protected int numberOfPins = 4; 
    
    

    //---Constructor---
    public Player(){
    	codeToBreak = new ArrayList<Integer>();
    	guess = new ArrayList<Integer>();
    }
        
    //---Methods---
    
    //CodeMaker
    
    public void createCode(int position , int colour){
    	codeToBreak.set(position, colour);
    }
    
    public ArrayList<Integer> getCode(){
 	   return codeToBreak;
    }
    
    //CodeBreaker
    
    public boolean isCodeMaker() {
		return codeMaker;
	}

	public void setCodeMaker(boolean codeMaker) {
		this.codeMaker = codeMaker;
	}

	 
    public void addPin(int position , int colour){
    	guess.set(position, colour);
    }
    
    //Before checking the guess ArrayList, fill the position of the pegs that where not assigned a colour, with the default (blank) peg.
    public void initializeGuessArray(){
    	for(int i=0;i<4;i++){
    		guess.add(0);
    	}
    			
    }
    
    public void initializeCodeToBreakArray(){
    	for(int i=0;i<4;i++){
    		codeToBreak.add(0);
    	}
    			
    }
    
    public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	//When the turn has ended, restores the guessing ArrayList to the default state.
    public void restoreGuessToDefault(){
    	for(int i=0;i<guess.size();i++){
    		guess.set(i, 0);
    	}
    }
    
    public void restoreCodeToDefault(){
    	for(int i=0;i<codeToBreak.size();i++){
    		codeToBreak.set(i, 0);
    	}
    }


    public String getName(){
        return username;
    }
    
    public void setName(String name){
    	this.username = name;
    }
    
    //Return "guess" array
   
    public ArrayList<Integer> getGuess(){
    	return guess;
    }
    
    public void setGuess(int[] guess){

        for(int i : guess){
            this.guess.add(i);
        }
    }
}
