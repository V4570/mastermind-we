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

    //===================================================================================================initFrame
    /**
     * Εδώ αρχικοποιείται το frame μαζί με ότι στοιχεία θέλουμε να προσθέσουμε.
     * Καλείτε τελευταία στον constructor, αφού έχουν δημιουργηθεί όλα τα επι-
     * μέρους στοιχεία όπως Buttons, Panels, κλπ.
     */
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

    //==================================================================================================setUpButtons
    /**
     * Μέθοδος που αρχικοποιεί τα κουμπιά που θέλουμε να έχουμε στο μενού. Αφορά
     * την αριστερή στήλη του μενού. Η μεταβλητή posY που αλλάζει αυξάνεται κάθε
     * φορά που προστίθεται ένα κουμπί κατά 70 pixel για σωστή στοίχιση.
     */
    private void setUpButtons(){
        howToPlay = new MenuButton("howtoplay.png");
        posY += 70;
        play = new MenuButton("play.png");
        posY += 70;
        options = new MenuButton("options.png");
        posY += 70;
        exitButton = new JButton();

        try {
            exitButton.setIcon(new ImageIcon(ImageIO.read(LoadAssets.load("exit.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Εδώ αλλάζει η εικόνα του exit button κάθε φορά που πάει το ποντίκι
         * πάνω του. Δεν προσφέρει κάτι στην λειτουργικότητα παρά μόνο στην
         * εμφάνιση του παιχνιδιού.
         */
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    exitButton.setIcon(new ImageIcon(ImageIO.read(LoadAssets.load("exit2.png"))));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    exitButton.setIcon(new ImageIcon(ImageIO.read(LoadAssets.load("exit.png"))));
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

    //==================================================================================================GameMode
    /**
     * Εσωτερική κλάση τύπου JPanel η οποία χρησιμοποιείται για να εμφανίζεται
     * το παραθυράκι επιλογής game mode αφού πατηθεί το κουμπί Play.
     */
    class GameMode extends JPanel{

        private Image background;
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
				exitIcon = new ImageIcon(ImageIO.read(LoadAssets.load("exit.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }

            exit = new JButton();
            exit.setIcon(exitIcon);
            exit.addActionListener(b);
            exit.setOpaque(false);
            exit.setContentAreaFilled(false);
            exit.setBorderPainted(false);

            setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

            setLayout(new GridBagLayout());
            c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            add(exit, c);


            setBounds(420, 280, background.getWidth(null), background.getHeight(null));
            setOpaque(false);
            setVisible(flag);
        }
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(background,0,0,null);
        }

        public void setVisible(){
            if(flag){
                setVisible(false);
                flag = false;
                return;
            }
            setVisible(true);
            flag = true;
        }
    }
    //==================================================================================================ButtonListener
    /**
     * Ειδική κλάση για να ελέγχει ποιό κουμπί πατήθηκε και να
     * ακολουθει τα αντίστοιχα βήματα.
     */
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
            else if(e.getSource() == gameModePanel.exit){
                gameModePanel.setVisible();
            }
            else{
                System.exit(0);
                //setState(Frame.ICONIFIED);
            }
        }
    }
    //==================================================================================================MenuButton
    /**
     * Ειδική κλάση για καλύτερη διαχείρηση των κουμπιών του μενού.
     */
    class MenuButton extends JButton{

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
