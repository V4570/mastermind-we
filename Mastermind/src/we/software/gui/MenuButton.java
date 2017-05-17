package we.software.gui;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

/**
 * Created by Bill on 30-Mar-17.
 *
 * MenuButton is a general class for all the menu buttons that share the same usability.
 */
class MenuButton extends Button {

    private AudioClip pressed;
    private AudioClip hover;
    private boolean isOn = false;
    private boolean selected = false;

    public MenuButton(String imagePath, int xPos, int yPos, int Xdisplacement, int Ydisplacement){
        //The displacement variables are used when a hovered button changes size so it has to be placed differently.

        super(imagePath, xPos, yPos);

        this.addMouseListener(new MouseAdapter(){

            public void mouseEntered(MouseEvent e){

                if(imageHover != null) {
                    setIcon(imageHover);
                    setBounds(xPos - Xdisplacement, yPos - Ydisplacement, imageHover.getIconWidth(), imageHover.getIconHeight());
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
                    setIcon(image);
                    setBounds(xPos, yPos, image.getIconWidth(), image.getIconHeight());
                    if(isOn){
                        hover.stop();
                        isOn = false;
                    }
                }
            }
        });

    }

    public void playSound(){

        URL pressedUrl = MenuButton.class.getResource("/Select.wav");
        pressed = Applet.newAudioClip(pressedUrl);
        pressed.play();
    }

    public void staySelected(){

        setIcon(imageHover);
        selected = true;
    }

    public void setUnselected(){

        setIcon(image);
        selected = false;
    }
}
