package we.software.mastermind;

/**
 * Created by ralph on 3/24/17.
 */
public class Player {

    private String name;
    private NormalPin[] guess;
    private SmallPin[] outcome;
    private int numberOfPins;
    private int maxGuesses;
    private int totalGuesses;
    private int[] points;
    private int currentRound;
    private boolean playing = false;

    public Player(String name, int numberOfPins){
        this.name = name;
        this.numberOfPins = numberOfPins;
    }

    public void setPin(){

    }

    /*public Pin selectPin(){

    }*/

    public void removePin(){

    }

    public void startPlaying(){
        playing = true;
    }

    public void stopPlaying(){
        playing = false;
    }

    public boolean getPlaying(){
        return playing;
    }

    public String getName(){
        return name;
    }
}
