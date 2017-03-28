package we.software.mastermind;

public class Game {

    private boolean started;
    private Player p1;
    private Player p2;
    private Computer c;
    private int gameType;


    public Game(int gameType){
        this.gameType = gameType;
        setGame();
    }

    private void setGame(){

        switch (gameType){
            case 0:
                p1 = new Player();
                c = new Computer();
                break;
            case 1:
                p1 = new Player();
                p2 = new Player();
                break;
        }
    }
}
