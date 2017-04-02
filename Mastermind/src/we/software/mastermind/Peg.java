package we.software.mastermind;

/**
 * Created by ralph on 3/24/17.
 */
abstract class Peg {
    private int color;
    private int xPos;
    private int yPos;
    private boolean selected;

    Peg(){

    }

    abstract int getXPos();
    abstract int getYPos();
    abstract int getColor();
}
