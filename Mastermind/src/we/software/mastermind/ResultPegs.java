package we.software.mastermind;

/**
 * Created by ralph on 3/24/17.
 */
class ResultPegs{
	
	private boolean colour; // TRUE = RED , FALSE = WHITE
	
	ResultPegs(boolean colour) {
		this.colour = colour;
	}
	
    boolean getColour() {
        return colour;
    }

}
