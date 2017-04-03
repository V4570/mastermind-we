package we.software.mastermind;

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
}
