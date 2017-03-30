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

    private MenuButton howToPlay, play, options;
    private JButton exitButton;
    private GameMode gameModePanel;
    private Options optionsPanel;
    private static int WIDTH = 1024;
    private static int HEIGHT = WIDTH / 12*9;
    private int posX = 130;
    private int posY = 230;
    private ButtonListener b = new ButtonListener();

    public MainMenu(){
        gameModePanel = new GameMode();
        optionsPanel = new Options();

        howToPlay = addMenuButton("howtoplay.png");
        play = addMenuButton("play.png");
        options = addMenuButton("options.png");

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
        add(optionsPanel);
        add(exitButton);

        setUndecorated(true);
        setVisible(true);
    }

    /**
     * Μέθοδος που αρχικοποιεί τα κουμπιά για το μενού. Το posY αυξάνεται κατά 70
     * κάθε φορά που προστίθεται ένα κουμπί ώστε να γίνεται σωστή κατακόρυφη
     * στοίχιση.
     */
    private MenuButton addMenuButton(String path){
        posY += 70;
        MenuButton button = new MenuButton(path, posX, posY);
        button.addActionListener(b);
        return button;
    }

    //==================================================================================================setUpButtons
    /**
     * Μέθοδος που αρχικοποιεί τα κουμπιά που θέλουμε να έχουμε στο μενού. Αφορά
     * την αριστερή στήλη του μενού. Η μεταβλητή posY που αλλάζει αυξάνεται κάθε
     * φορά που προστίθεται ένα κουμπί κατά 70 pixel για σωστή στοίχιση.
     */
    private void setUpButtons(){

        exitButton = new JButton();

        try {
            exitButton.setIcon(new ImageIcon(ImageIO.read(LoadAssets.load("exit.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    exitButton.setIcon(new ImageIcon(ImageIO.read(LoadAssets.load("hexit.png"))));
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
        private ImageIcon exitIcon, exitIconHover, titleImage,
                 pvaiTitle, pvpTitle;
        private OptionsButton pVsAi, pVsP;
        private JButton exit;
        private JLabel title;
        private boolean flag = false;
        private boolean flagOptions = true;

        private GameMode(){
            title = new JLabel();
            try {
                titleImage = new ImageIcon(LoadAssets.load("selectgamemode.png"));
                pvaiTitle = new ImageIcon(LoadAssets.load("titlepvai.png"));
                pvpTitle = new ImageIcon(LoadAssets.load("titlepvp.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            title.setIcon(titleImage);
            title.setBounds(60, 11, titleImage.getIconWidth(), titleImage.getIconHeight());

            pVsAi = new OptionsButton("pvai.png", 39, 46);
            pVsAi.addActionListener(b);

            pVsP = new OptionsButton("pvp.png", 39, 98);
            pVsP.addActionListener(b);


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
            add(pVsAi);
            add(pVsP);

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

        /**
         * Θέτει το Panel visible εφόσον ελέγξει ότι είναι κλειστό.
         */
        public void setPanelVisible(){
            if(!flag){
                setVisible(!flag);
                flag = !flag;
            }
        }

        /**
         * Θέτει το Panel invisible εφόσον ελέγξει ότι είναι ανοιχτό.
         */
        public void setPanelInvisible(){
            if(flag){
                setVisible(!flag);
                flag = !flag;
            }
        }

        /**
         * Θέτει το gameOptions Panel στην αρχική του κατάσταση.
         */
        public void panelRestart(){
            pVsP.setVisible(true);
            pVsAi.setVisible(true);
            title.setIcon(titleImage);
            title.setBounds(60, 11, titleImage.getIconWidth(), titleImage.getIconHeight());
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
                optionsPanel.setPanelInvisible();
                gameModePanel.setPanelVisible();
                if(!optionsPanel.flagOptions){
                    optionsPanel.panelRestart();
                }
                //select.playClip();
            }
            else if(e.getSource() == options){
                System.out.println("options");
                gameModePanel.setPanelInvisible();
                optionsPanel.setPanelVisible();
                if(!gameModePanel.flagOptions){
                    gameModePanel.panelRestart();
                }
                //select.playClip();
            }
            /**
             * Κλείνει το gameOptions Panel και καλεί την μέθοδο
             * panelRestart() που θέτει το Panel στην αρχική του
             * κατάσταση εφόσον έχει πατηθεί κάποιο κουμπί μέσα
             * στο Panel και έχει αλλάξει η δομή του.
             */
            else if(e.getSource() == gameModePanel.exit){
                gameModePanel.setPanelInvisible();
                if(!gameModePanel.flagOptions){
                    gameModePanel.panelRestart();
                }
            }
            /**
             * Κάθε φορά που πατιέται το κουμπί Player vs Player εξαφανίζει τα υπάρχοντα κουμπιά
             * και αλλάζει τον τίτλο του gameOptions Panel σε Player vs Player. Επίσης αλλάζει
             * τα Bounds για να τοποθετείται σωστά μέσα στο panel.
             */
            else if(e.getSource() == gameModePanel.pVsP){
                gameModePanel.pVsP.setVisible(false);
                gameModePanel.pVsAi.setVisible(false);
                gameModePanel.title.setBounds(30, -2, gameModePanel.pvpTitle.getIconWidth(), gameModePanel.pvpTitle.getIconHeight());
                gameModePanel.title.setIcon(gameModePanel.pvpTitle);
                gameModePanel.flagOptions = false;
            }
            /**
             * Κάθε φορά που πατιέται το κουμπί Player vs A.I εξαφανίζει τα υπάρχοντα κουμπιά
             * και αλλάζει τον τίτλο του gameOptions Panel σε Player vs A.I. Επίσης αλλάζει
             * τα Bounds για να τοποθετείται σωστά μέσα στο panel.
             */
            else if(e.getSource() == gameModePanel.pVsAi){
                gameModePanel.pVsP.setVisible(false);
                gameModePanel.pVsAi.setVisible(false);
                gameModePanel.title.setIcon(gameModePanel.pvaiTitle);
                gameModePanel.title.setBounds(30, -2, gameModePanel.pvaiTitle.getIconWidth(), gameModePanel.pvaiTitle.getIconHeight());
                gameModePanel.flagOptions = false;
            }
            else if(e.getSource() == optionsPanel.exit){
                optionsPanel.setPanelInvisible();
                if(!optionsPanel.flagOptions){
                    optionsPanel.panelRestart();
                }
            }
            else{
                System.exit(0);
                //setState(Frame.ICONIFIED);
            }
        }
    }

    class Options extends JPanel{

        private Image background;
        private ImageIcon exitIcon, exitIconHover, titleImage,
                pvaiTitle, pvpTitle;
        private JButton exit;
        private JLabel title;
        private boolean flag = false;
        private boolean flagOptions = true;

        public Options(){
            title = new JLabel();
            try {
                titleImage = new ImageIcon(LoadAssets.load("titleoptions.png"));
                pvaiTitle = new ImageIcon(LoadAssets.load("titlepvai.png"));
                pvpTitle = new ImageIcon(LoadAssets.load("titlepvp.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            title.setIcon(titleImage);
            title.setBounds(130, 11, titleImage.getIconWidth(), titleImage.getIconHeight());


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

            setBounds(420, 350, background.getWidth(null), background.getHeight(null));
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

                public void mouseEntered(MouseEvent e) {
                    exit.setBounds(316, 7, exitIconHover.getIconWidth(), exitIconHover.getIconHeight());
                    exit.setIcon(exitIconHover);
                }

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

        /**
         * Θέτει το Panel visible εφόσον ελέγξει ότι είναι κλειστό.
         */
        public void setPanelVisible(){
            if(!flag){
                setVisible(!flag);
                flag = !flag;
            }
        }

        /**
         * Θέτει το Panel invisible εφόσον ελέγξει ότι είναι ανοιχτό.
         */
        public void setPanelInvisible(){
            if(flag){
                setVisible(!flag);
                flag = !flag;
            }
        }

        /**
         * Θέτει το gameOptions Panel στην αρχική του κατάσταση.
         */
        public void panelRestart(){
            title.setIcon(titleImage);
            title.setBounds(60, 11, titleImage.getIconWidth(), titleImage.getIconHeight());
        }
    }
}
