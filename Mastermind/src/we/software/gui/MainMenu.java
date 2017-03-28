package we.software.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by ralph on 3/28/17.
 */
public class MainMenu extends JFrame{
    //private JFrame mainFrame;
    private JLabel title;
    private JButton playButton;
    private JPanel mainPanel;
    private JPanel backgroundPanel;
    private BackgroundPanel panel;
    private static int WIDTH = 1024;
    private static int HEIGHT = WIDTH / 12*9;
    private Image image;

    public MainMenu(){
        initFrame();
        /*try {
            image = ImageIO.read(LoadAssets.load("Images/Background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        panel = new BackgroundPanel(image);*/
        initFrame();
    }

    private void initFrame(){
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        try {
            setContentPane(new JLabel(new ImageIcon(ImageIO.read(LoadAssets.load("Background.png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
    }


    class BackgroundPanel extends JPanel{

        private Image image;

        public BackgroundPanel(Image image){
            Dimension size = new Dimension(WIDTH, HEIGHT);
            setMinimumSize(size);
            setMaximumSize(size);
            setPreferredSize(size);

            this.image = image;
            repaint();
        }
    }
}
