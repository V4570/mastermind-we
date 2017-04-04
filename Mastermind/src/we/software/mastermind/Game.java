package we.software.mastermind;

import java.util.ArrayList;
import java.util.Collections;

class Game {

    private boolean started;
    private Player p1;
    private Player p2;
    private Computer c;
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
                c = new Computer(difficulty);
                break;
            case 1:
                p1 = new Player();
                p2 = new Player();
                break;
        }
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
    
    //Επιστροφή πίνακα ελέγχου
    private ArrayList<ResultPegs> checkGuess(){
    	
    	//Πίνακας Player1 (π1)
    	ArrayList<PlayingPegs> guess = p1.getGuess();
    	
    	//Πίνακας Player2 ή AI (π2)
    	ArrayList<PlayingPegs> code = new ArrayList<PlayingPegs>(); 
    	
    	//Αρχικοποίηση τελικού πίνακα (π3)
    	ArrayList<ResultPegs> result = new ArrayList<ResultPegs>();
    	
    	/* Το i διαβαζει μία-μία τις εγγραφές του π1 και το j του π2.
    	 * Ελέγχει κάθε φορά αν το χρώμα peg της θέσης i του π1 είναι
    	 * ίδιο με κάποιο του π2, τότε καταχωρεί στο π3 peg χρώματος
    	 * άσπρο. Αν ισχύει το παραπάνω + i==j τότε καταχωρεί στο π3 peg 
    	 * χρώματος κόκκινο. 
    	 * Κάνει shuffle τον π3 ώστε οι θέσεις των ResultPegs να μην αντιστοιχούν
    	 * με τις θέσεις των Pegs του π1
    	*/
    	for(int i=0; i<4; i++){
    		PlayingPegs pp1 = guess.get(i);
    		
    		for(int j=0; j<4; j++){
    			PlayingPegs pp2 = code.get(i);
    			
    			if(pp1.getColour()==pp2.getColour() && i==j)
    				result.add(new ResultPegs(true));
    			
    			else if(guess.get(i).equals(code.get(j)))
    				result.add(new ResultPegs(false));
    		}			
    	}
    	
    	Collections.shuffle(result);
    	return result;
    }
}
