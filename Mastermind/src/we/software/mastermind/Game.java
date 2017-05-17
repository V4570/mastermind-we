package we.software.mastermind;

import java.util.ArrayList;
import java.util.Collections;

class Game {

	public Player p1;
	public Player p2; // Player or Computer
	public Client c;
	private int gameType;
	private int difficulty; // 0-2 (Easy - Hard)
	private int games;
	public int currentRound;
	public int currentGame;
	public int gameScore;
	public int 	maxRounds=10;

	public Game(int gameType,int difficulty) {
		this.gameType = gameType;
		p1 = new Player();
		p2 = new Computer(difficulty);
	}

	public Game(int gameType,int games,Client c,boolean isClientCodeMaker) { // PvP / PvE selection
		this.gameType = gameType;
		this.currentRound = 0;
		this.games=games;
		this.c = c;
		this.gameScore=0;
		c.setEnemy(new Player());
		p2  = c.getEnemy();
		if(isClientCodeMaker){
			c.setCodeMaker(true);
		}
		else{
			c.setCodeMaker(false);
		}
	}

	public boolean checkIfAllRed(ArrayList<Integer> result) {

		for (int aPeg : result)
			if (aPeg != 2)
				return false;
		return true;
	}
	
	private void setRoundScore(ArrayList<Integer> result){
		
		for(int resPeg : result){
			if(resPeg==2){
				gameScore = gameScore + 50;
			}
			else if(resPeg==1){
				gameScore = gameScore+40;
			}
		}
	}
	
	public int getGameScore(){
		if(checkIfAllRed(checkGuess())){
			return gameScore+(5000-(currentRound*150));
		}
		else{
			return gameScore;
		}
	}
	
	// Returns result table
	public ArrayList<Integer> checkGuess() {

		// Player1 table (p1)
		ArrayList<Integer> guess = p1.getCode();

		// Player2 or AI table (p2)
		ArrayList<Integer> code = p2.getCode();

		// Initializing final table (p3)
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		ArrayList<Integer> ex = new ArrayList<Integer>();

		for (int i = 0; i < 4; i++) {
			if (guess.get(i) == code.get(i)) {
				result.add(2);
				ex.add(i);
			}
		}
		for (int i = 0; i < 4; i++) {
			if (!ex.isEmpty()) {
				if (ex.contains(i))
					continue;
			}
			for (int j = 0; j < 4; j++) {
				if (guess.get(i) == code.get(j)) {
					result.add(1);
					break;
				}
			}
		}
		
		while (result.size() < 3) {
			result.add(0);
		}
		setRoundScore(result);
		return result;

	}
}
