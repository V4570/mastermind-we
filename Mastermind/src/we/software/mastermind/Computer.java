package we.software.mastermind;

/**
 * Created by bill on 3/24/17.
 */
class Computer extends Player{

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

    
    public void easyAlgorithm(){

    }

    public void mediumAlgorithm(){
    }

    public void hardAlgorithm(){

    }
}
