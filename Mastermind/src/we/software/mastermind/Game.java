package we.software.mastermind;

import java.util.ArrayList;

public class Game {

	private Player p1;
	private Player p2; // Player or Computer (CodeMaker)
	
	private int currentRound;
	private int currentGame;
	private int gameScore;
	private int maxRounds = 10;
	private ArrayList<Integer> result;

	public Game(int difficulty) {
		p1 = new Player();
		p2 = new Computer(difficulty);
		p1.initializeGuessArray();
	}

	public Game(Client c,boolean isClientCodeMaker,String enemyName) { // PvP / PvE selection
		this.currentRound = 0;
		this.p1 = c;
		this.gameScore = 0;
		c.setEnemy(new Player());
		p2  = c.getEnemy();
		p2.setName(enemyName);
		c.setCodeMaker(isClientCodeMaker);
	}
	

	public Player getP1() {
		return p1;
	}

	public Player getP2() {
		return p2;
	}
	

	public void setP2(Player p2) {
		this.p2 = p2;
	}

	//Checks right guesses
	public boolean checkIfWin() {

		for (int aPeg : result)
			if (aPeg != 2)
				return false;
		return true;
	}
	
	//Calculates the score of current round
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
	
	//Returns current score
	public int getGameScore(){
		if(checkIfWin()){
			return gameScore+(3000-(currentRound*200));
		}
		else{
			return gameScore;
		}
	}
	
	// Returns result table
	public ArrayList<Integer> checkGuess() {
		currentRound++;
		// Player1 table (p1)
		ArrayList<Integer> guess = p1.getGuess();
		System.out.println(guess);

		// Player2 or AI table (p2)
		ArrayList<Integer> code = p2.getCode();
		System.out.println(code);

		// Initializing final table (p3)
		result = new ArrayList<Integer>();
		
		ArrayList<Integer> ex = new ArrayList<Integer>();

		for (int i = 0; i < 4; i++) {
			if (guess.get(i).equals(code.get(i))) {
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
				if (guess.get(i).equals(code.get(j))) {
					result.add(1);
					break;
				}
			}
		}

		while (result.size() < 4) {
			result.add(0);
		}
		setRoundScore(result);
		return result;

	}

	public void setGuess(int[] guess){
	    p1.setGuess(guess);
    }
}
