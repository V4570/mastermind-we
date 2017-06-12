package we.software.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Bill on 17-May-17.
 * This class is used to load images that are going to be used multiple times so that they don't load everytime.
 * glassRed, glassGreen, etc. are the colored versions of the standard glass image that is used for the guess.
 * glowRed, glowGreen, etc. are the highlighted versions of the above images.
 * whitePeg, blackPeg, etc. are the images used in the history panel for each turn guess.
 */
public class PreloadImages {

    private static ArrayList<ImageIcon> glasses = new ArrayList<ImageIcon>();
    private static ArrayList<BufferedImage> numbers = new ArrayList<BufferedImage>();
    private static ArrayList<BufferedImage> pegs = new ArrayList<BufferedImage>();


    public static void preloadImages(){

        loadGlasses();
        loadNumbers();
        loadPegs();
    }


    private static void loadGlasses(){

        ImageIcon glassRed = new ImageIcon(LoadAssets.load("Buttons/glassRed.png"));
        glasses.add(glassRed);
        ImageIcon glassGreen = new ImageIcon(LoadAssets.load("Buttons/glassGreen.png"));
        glasses.add(glassGreen);
        ImageIcon glassBlue = new ImageIcon(LoadAssets.load("Buttons/glassBlue.png"));
        glasses.add(glassBlue);
        ImageIcon glassYellow = new ImageIcon(LoadAssets.load("Buttons/glassYellow.png"));
        glasses.add(glassYellow);
        ImageIcon glassWhite = new ImageIcon(LoadAssets.load("Buttons/glassWhite.png"));
        glasses.add(glassWhite);
        ImageIcon glassBlack = new ImageIcon(LoadAssets.load("Buttons/glassBlack.png"));
        glasses.add(glassBlack);

        ImageIcon glowRed = new ImageIcon(LoadAssets.load("Buttons/glowRed.png"));
        glasses.add(glowRed);
        ImageIcon glowGreen = new ImageIcon(LoadAssets.load("Buttons/glowGreen.png"));
        glasses.add(glowGreen);
        ImageIcon glowBlue = new ImageIcon(LoadAssets.load("Buttons/glowBlue.png"));
        glasses.add(glowBlue);
        ImageIcon glowYellow = new ImageIcon(LoadAssets.load("Buttons/glowYellow.png"));
        glasses.add(glowYellow);
        ImageIcon glowWhite = new ImageIcon(LoadAssets.load("Buttons/glowWhite.png"));
        glasses.add(glowWhite);
        ImageIcon glowBlack = new ImageIcon(LoadAssets.load("Buttons/glowBlack.png"));
        glasses.add(glowBlack);
    }

    private static void loadNumbers(){

        for(int i=0; i<10; i++){

            String path ="TurnNumbers/"+Integer.toString(i+1)+".png";

            try {
                numbers.add(ImageIO.read(LoadAssets.load(path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadPegs(){

        try {

            BufferedImage redPeg = ImageIO.read(LoadAssets.load("Pegs_Evaluation/redv2.png"));
            pegs.add(redPeg);
            BufferedImage greenPeg = ImageIO.read(LoadAssets.load("Pegs_Evaluation/greenv2.png"));
            pegs.add(greenPeg);
            BufferedImage bluePeg = ImageIO.read(LoadAssets.load("Pegs_Evaluation/bluev2.png"));
            pegs.add(bluePeg);
            BufferedImage yellowPeg = ImageIO.read(LoadAssets.load("Pegs_Evaluation/yellowv2.png"));
            pegs.add(yellowPeg);
            BufferedImage whitePeg = ImageIO.read(LoadAssets.load("Pegs_Evaluation/whitev2.png"));
            pegs.add(whitePeg);
            BufferedImage blackPeg = ImageIO.read(LoadAssets.load("Pegs_Evaluation/magentav2.png"));
            pegs.add(blackPeg);

            BufferedImage whiteClue = ImageIO.read(LoadAssets.load("Pegs_Evaluation/clueWhite.png"));
            pegs.add(whiteClue);
            BufferedImage redClue = ImageIO.read(LoadAssets.load("Pegs_Evaluation/clueRed.png"));
            pegs.add(redClue);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageIcon> getGlasses(){
        return glasses;
    }

    public static ArrayList<BufferedImage> getNumbers(){
        return numbers;
    }

    public static ArrayList<BufferedImage> getPegs(){
        return pegs;
    }
}
