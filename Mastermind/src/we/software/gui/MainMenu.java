package we.software.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by bill on 3/28/17.
 */
public class MainMenu extends JFrame{

    private MenuButton howToPlay;
    private MenuButton play;
    private MenuButton options;
    private JButton exitButton;
    private GameMode gameModePanel;
    private static int WIDTH = 1024;
    private static int HEIGHT = WIDTH / 12*9;
    private int posX = 130;
    private int posY = 300;
    private ButtonListener b = new ButtonListener();

    public MainMenu(){
        gameModePanel = new GameMode();

        setUpButtons();

        initFrame();

    }

    private void initFrame(){

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            setIconImage(ImageIO.read(LoadAssets.load("master.png")));
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }

        try {
            setContentPane(new JLabel(new ImageIcon(ImageIO.read(LoadAssets.load("Background.png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(howToPlay);
        add(play);
        add(options);
        add(gameModePanel);
        add(exitButton);

        setUndecorated(true);
        setVisible(true);
    }

    private void setUpButtons(){
        howToPlay = new MenuButton("howtoplay.png");
        posY += 70;
        play = new MenuButton("play.png");
        posY += 70;
        options = new MenuButton("options.png");
        posY += 70;
        exitButton = new JButton();

        try {
            exitButton.setIcon(new ImageIcon(ImageIO.read(new LoadAssets().load("exit.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    exitButton.setIcon(new ImageIcon(ImageIO.read(new LoadAssets().load("exit2.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    exitButton.setIcon(new ImageIcon(ImageIO.read(new LoadAssets().load("exit.png"))));
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        });

        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(b);
        exitButton.setBounds(1001,5,18,15);
    }

    class GameMode extends JPanel{

        private BufferedImage background;
        private ImageIcon exitIcon;
        private boolean flag = false;
        private JButton exit;
        private GridBagConstraints c;

        private GameMode(){

            try {
                background = ImageIO.read(LoadAssets.load("gameoptions.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                new LoadAssets();
				exitIcon = new ImageIcon(ImageIO.read(LoadAssets.load("exit.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            exit = new JButton();
            //exit.setBounds(0, 0, exitIcon.getIconWidth(), exitIcon.getIconHeight());
            exit.setIcon(exitIcon);
            exit.setOpaque(false);
            exit.setContentAreaFilled(false);
            exit.setBorderPainted(false);

            setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

            setLayout(new GridBagLayout());
            c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            add(exit, c);


            setBounds(420, 280, background.getWidth(), background.getHeight());
            setOpaque(false);
            setVisible(flag);
        }
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(background,0,0,null);
        }

        public void setVisible(){
            if(!flag){
                setVisible(true);
            }
        }
    }

    class ButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == howToPlay){
                System.out.println("how");
            }
            else if(e.getSource() == play){
                System.out.println("play");
                gameModePanel.setVisible();
            }
            else if(e.getSource() == options){
                System.out.println("options");
            }
            else{
                System.exit(0);
                //setState(Frame.ICONIFIED);
            }
        }
    }

    class MenuButton extends JButton{

        private Dimension size;
        private ImageIcon image;
        private ImageIcon imageHover;
        private int x, y;

        public MenuButton(String imagePath){
            x = posX;
            y = posY;
            try {
                image = new ImageIcon(ImageIO.read(LoadAssets.load(imagePath)));
                imageHover = new ImageIcon(ImageIO.read(LoadAssets.load("h" +imagePath)));
            } catch (IOException e) {
                e.printStackTrace();
            }

            setIcon(image);
            addActionListener(b);
            setBounds(x, y, image.getIconWidth(), image.getIconHeight());
            this.addMouseListener(new MouseAdapter(){
                public void mouseEntered(MouseEvent e){
                    setIcon(imageHover);
                    setBounds(x-51, y, imageHover.getIconWidth(), imageHover.getIconHeight());
                }
                public void mouseExited(MouseEvent e){
                    setIcon(image);
                    setBounds(x, y, image.getIconWidth(), image.getIconHeight());
                }
            });

            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
        }
    }
}
