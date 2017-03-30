package we.software.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        private ImageIcon exitIconHover;
        private ImageIcon titleImage;
        private ImageIcon pvaiIcon;
        private ImageIcon pvaiIconHover;
        private JButton pvai;
        private ImageIcon pvpIcon;
        private ImageIcon pvpIconHover;
        private JButton pvp;
        private boolean flag = false;
        private JButton exit;
        private JLabel title;

        private GameMode(){
            title = new JLabel();
            try {
                titleImage = new ImageIcon(LoadAssets.load("selectgamemode.png"));
                pvaiIcon = new ImageIcon(LoadAssets.load("pvai.png"));
                pvaiIconHover = new ImageIcon(LoadAssets.load("hpvai.png"));
                pvpIcon = new ImageIcon(LoadAssets.load("pvp.png"));
                pvpIconHover = new ImageIcon(LoadAssets.load("hpvp.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            title.setIcon(titleImage);
            title.setBounds(60, 11, titleImage.getIconWidth(), titleImage.getIconHeight());

            pvai = new JButton();
            pvai.setIcon(pvaiIcon);
            pvai.setBounds(39, 46, pvaiIcon.getIconWidth(), pvaiIcon.getIconHeight());
            pvai.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    pvai.setIcon(pvaiIconHover);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    pvai.setIcon(pvaiIcon);
                }
            });
            pvai.setOpaque(false);
            pvai.setContentAreaFilled(false);
            pvai.setBorderPainted(false);

            pvp = new JButton();
            pvp.setIcon(pvpIcon);
            pvp.setBounds(39, 98, pvpIcon.getIconWidth(), pvpIcon.getIconHeight());
            pvp.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    pvp.setIcon(pvpIconHover);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    pvp.setIcon(pvpIcon);
                }
            });
            pvp.setOpaque(false);
            pvp.setContentAreaFilled(false);
            pvp.setBorderPainted(false);

            readImages();
            addExit();
            initPanel();

        }
        private void readImages(){

            try {
                background = ImageIO.read(LoadAssets.load("gameoptions.png"));
                exitIcon = new ImageIcon(ImageIO.read(LoadAssets.load("exit.png")));
                exitIconHover = new ImageIcon(ImageIO.read(LoadAssets.load("sexit.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        private void initPanel(){
            setLayout(null);
            add(exit);
            add(title);
            add(pvai);
            add(pvp);

            setBounds(420, 280, background.getWidth(null), background.getHeight(null));
            setOpaque(false);
            setVisible(flag);
        }

        private void addExit(){
            exit = new JButton();
            exit.setIcon(exitIcon);
            exit.setBounds(323, 13, exitIcon.getIconWidth(), exitIcon.getIconHeight());
            exit.addActionListener(b);
            exit.setOpaque(false);
            exit.setContentAreaFilled(false);
            exit.setBorderPainted(false);
            exit.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    exit.setBounds(316, 7, exitIconHover.getIconWidth(), exitIconHover.getIconHeight());
                    exit.setIcon(exitIconHover);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    exit.setBounds(323, 13, exitIcon.getIconWidth(), exitIcon.getIconHeight());
                    exit.setIcon(exitIcon);
                }
            });


        }

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(background,0,0,null);
        }

        public void setPanelVisible(){
            if(!flag){
                setVisible(!flag);
                flag = !flag;
            }
        }

        public void setPanelInvisible(){
            if(flag){
                setVisible(!flag);
                flag = !flag;
            }
        }
    }
    //==================================================================================================ButtonListener
    /**
     * Ειδική κλάση για να ελέγχει ποιό κουμπί πατήθηκε και να
     * ακολουθει τα αντίστοιχα βήματα.
     */
    class ButtonListener implements ActionListener{
        //private AudioLoad select = new AudioLoad("Select.wav");
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == howToPlay){
                System.out.println("how");
                //select.playClip();
            }
            else if(e.getSource() == play){
                System.out.println("play");
                gameModePanel.setPanelVisible();
                //select.playClip();
            }
            else if(e.getSource() == options){
                System.out.println("options");
                gameModePanel.setPanelInvisible();
                //select.playClip();

            }
            else if(e.getSource() == gameModePanel.exit){
                gameModePanel.setPanelInvisible();
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
        //private AudioLoad hover;

        public MenuButton(String imagePath){
            x = posX;
            y = posY;
            //hover = new AudioLoad("Hover.wav");

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

    class PanelButton extends JButton{

        public PanelButton(){
            //setText();
            //setBounds();
            setOpaque(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
        }
    }
}
