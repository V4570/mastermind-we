package we.software.mastermind;

/**
 * Created by ralph on 3/24/17.
 */
public abstract class Peg {
    private String color;
    private int xPos;
    private int yPos;
    private boolean selected;

    public Peg(){

    }

    abstract int getXPos();
    abstract int getYPos();
    abstract String getColor();
}
