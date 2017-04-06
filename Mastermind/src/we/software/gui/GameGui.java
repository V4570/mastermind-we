package we.software.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Bill on 06-Apr-17.
 */
public class GameGui extends JFrame{

    private final int WIDTH = 1024;
    private final int HEIGHT = WIDTH / 12*9;
    private ButtonListener btnListener = new ButtonListener();
    private MenuButton exitButton, optionsButton, backButton;

    public GameGui(){

        setUpButtons();
        initFrame();
    }

    private void initFrame(){

        try {
            setIconImage(ImageIO.read(LoadAssets.load("master.png")));
            setContentPane(new JLabel(new ImageIcon(ImageIO.read(LoadAssets.load("GameGui.png")))));
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }

        add(exitButton);
        add(optionsButton);
        add(backButton);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void setUpButtons(){

        exitButton = new MenuButton("exit.png", 1001, 5, 0);
        exitButton.addActionListener(btnListener);

        optionsButton = new MenuButton("ingameoptions.png", 885, 213, 0);
        optionsButton.addActionListener(btnListener);

        backButton = new MenuButton("backtomenu.png", 947, 213, 0);
        backButton.addActionListener(btnListener);
    }

    class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == optionsButton){

            }
            else if(e.getSource() == backButton){

            }
            else if(e.getSource() == exitButton){
                System.exit(0);
            }
        }
    }


}
