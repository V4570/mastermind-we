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
public class Player {

	//General User Information
    protected String username;
    private int highScore;
    private int leastTurns;
    
    //Current Game player information.
    private boolean guessing = true;
    protected ArrayList<Integer> CodeToBreak;
    private ArrayList<Integer> guess;
    
    //Game Info
    protected int numberOfPins = 4; 
    
    private int runningRounds;
    private double highscore;
    

    //---Constructor---
    public Player(){
    	CodeToBreak = new ArrayList<Integer>();
    	guess = new ArrayList<Integer>();
    }
        
    //---Methods---
    
    //CodeMaker
    
    public void createCode(int position , int colour){
    	CodeToBreak.set(position, colour);
    }
    
    public ArrayList<Integer> getCode(){
 	   return CodeToBreak;
    }
    
    //CodeBreaker
    
    //Position in the array list (Input = 0-3), setting the colour of the selected peg. (Input = 0-6 , with 0 as the default) 
    public void addPin(int position , int colour){
    	guess.set(position, colour);
    }
    
    //Before checking the guess ArrayList, fill the position of the pegs that where not assigned a colour, with the default (blank) peg.
    public void initializeGuessArray(){
    	for(int i=0;i<4;i++){
    		guess.add(0);
    	}
    			
    }
    
    //When the turn has ended, restores the guessing ArrayList to the default state.
    public void restoreGuessToDefault(){
    	for(int i=0;i<guess.size();i++){
    		guess.set(i, 0);
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
