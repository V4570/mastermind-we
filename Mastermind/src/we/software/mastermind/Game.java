package we.software.mastermind;

import java.util.ArrayList;
import java.util.Collections;

class Game {

    private boolean started;
    
	private Player p1;
    private Player p2; //Player or Computer
    //private Computer c;
    private int gameType;
    private int difficulty; // 0-2 (Easy - Hard)


    public Game(int gameType){
        this.gameType = gameType;
        setGame();
    }

    private void setGame(){	//PvP / PvE selection

        switch (gameType){
            case 0:
                p1 = new Player();
                p2 = new Computer(difficulty);
                break;
            case 1:
                p1 = new Player();
                p2 = new Player();
                break;
        }
    }
    
    public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}    
    
    private boolean checkIfWin(){
    	
    	ArrayList<ResultPegs> result = this.checkGuess();
    	boolean allRed = true;
    	
    	for(ResultPegs aPeg: result)
    		if(!aPeg.getColour())
    			allRed = false;
    	
    	if(allRed && result.size() == 4)
    		return true;
    	else
    		return false;
    }
    
    //Returns result table
    private ArrayList<ResultPegs> checkGuess(){
    	
    	//Player1 table (p1)
    	ArrayList<Integer> guess = p1.getCode();
    	
    	//Player2 or AI table (p2)
    	ArrayList<Integer> code = p2.getCode();  
    	
    	//Initializing final table (p3)
    	ArrayList<ResultPegs> result = new ArrayList<ResultPegs>();

		/**
         * 'i' reads the records of p1 and j of p2 one by one. It checks every time whether the peg color
         * of position i of p1 is the same as one of p2's, then it registers in the p3 a White peg.
         * If the above + i == j then registers a Red peg in p3.
         * It shuffles p3 so that the positions of the ResultPegs do not match with the Pegs of p1.
		 */
		for(int i=0; i<4; i++){
    		int pp1 = guess.get(i);
    		
    		for(int j=0; j<4; j++){
    			int pp2 = code.get(i);
    			
    			if(pp1==pp2){
    				if(i==j){
    					result.add(new ResultPegs(true));
    					p1.redPegIncrease();
    				}
    					
    				else{
    					result.add(new ResultPegs(false));
    					p1.whitePegIncrease();
    				}
    					
    			}
    		}			
    	}
    	
    	Collections.shuffle(result);
    	p1.roundIncrease();
    	p1.restoreGuessToDefault();
    	return result;
    }
}
