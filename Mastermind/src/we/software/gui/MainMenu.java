package we.software.gui;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Created by ralph on 3/28/17.
 */
public class MainMenu extends JFrame{
    //private JFrame mainFrame;
    private JLabel title;
    private JButton playButton;
    private JPanel mainPanel;
    private static int WIDTH = 1024;
    private static int HEIGHT = WIDTH * 12/9;

    public MainMenu(){
        initFrame();

    }

    private void initFrame(){
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }


}
