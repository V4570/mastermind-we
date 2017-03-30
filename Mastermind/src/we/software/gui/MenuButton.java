package we.software.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by Camel on 30-Mar-17.
 */
class MenuButton extends JButton {

    private ImageIcon image;
    private ImageIcon imageHover;
    private int x, y;
    //private AudioLoad hover;

    public MenuButton(String imagePath, int xPos, int yPos){
        x = xPos;
        y = yPos;
        //hover = new AudioLoad("Hover.wav");

        try {
            image = new ImageIcon(ImageIO.read(LoadAssets.load(imagePath)));
            imageHover = new ImageIcon(ImageIO.read(LoadAssets.load("h" +imagePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setIcon(image);
        setBounds(x, y, image.getIconWidth(), image.getIconHeight());

        this.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e){
                setIcon(imageHover);
                setBounds(x-51, y, imageHover.getIconWidth(), imageHover.getIconHeight());
                //hover.playClip();
            }
            public void mouseExited(MouseEvent e){
                setIcon(image);
                setBounds(x, y, image.getIconWidth(), image.getIconHeight());
                //hover.closeClip();
            }
        });

        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }
}
