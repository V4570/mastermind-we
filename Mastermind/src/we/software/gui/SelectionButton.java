package we.software.gui;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Bill on 17-May-17.
 */
public class SelectionButton extends Button{

    private AudioClip hover, pressed;
    private boolean isOn = false;
    protected boolean selected = false;
    private ArrayList<ImageIcon> glasses;
    private ImageIcon coloredHover, coloredGlass = null;
    private int color = -1;

    public SelectionButton(String imagePath, int xPos, int yPos){

        super(imagePath, xPos, yPos);

        glasses = (ArrayList<ImageIcon>) PreloadImages.getGlasses().clone();

        this.addMouseListener(new MouseAdapter(){

            public void mouseEntered(MouseEvent e){

                if(imageHover != null && coloredHover == null) {
                    setIcon(imageHover);
                    setBounds(xPos, yPos, imageHover.getIconWidth(), imageHover.getIconHeight());
                }
                else if(coloredHover != null){
                    setIcon(coloredHover);
                    setBounds(xPos, yPos, coloredHover.getIconWidth(), coloredHover.getIconHeight());
                }

                if(MainMenu.soundfxOn){
                    URL hoverUrl = MenuButton.class.getResource("/Hover.wav");
                    hover = Applet.newAudioClip(hoverUrl);
                    hover.play();
                    isOn = true;
                }
            }

            public void mouseExited(MouseEvent e){

                if(!selected){

                    if(coloredGlass == null){
                        setIcon(image);
                        setBounds(xPos, yPos, image.getIconWidth(), image.getIconHeight());
                    }
                    else{
                        setIcon(coloredGlass);
                        setBounds(xPos, yPos, coloredGlass.getIconWidth(), coloredGlass.getIconHeight());
                    }
                    if(isOn){
                        hover.stop();
                        isOn = false;
                    }
                }
            }
        });
    }

    @Override
    public void playSound() {
        URL pressedUrl = MenuButton.class.getResource("/Select.wav");
        pressed = Applet.newAudioClip(pressedUrl);
        pressed.play();
    }

    public void setColored(int colorSelection){

        color = colorSelection;
        coloredGlass = glasses.get(color);
        coloredHover = glasses.get(color + 6);
    }

    public void setUncolored(){

        coloredGlass = null;
        coloredHover = null;
        setUnselected();
    }

    public void staySelected(){

        if(coloredHover == null){
            setIcon(imageHover);
        }
        else{
            setIcon(coloredHover);
        }
        selected = true;
    }

    public void setUnselected(){

        if(coloredGlass == null){
            setIcon(image);
        }
        else{
            setIcon(coloredGlass);
        }
        selected = false;
    }

    public int getColor(){

        return color;
    }
}
