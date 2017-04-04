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
    private GameMode gameModePanel;
    private Options optionsPanel;
    private final int WIDTH = 1024;
    private final int HEIGHT = WIDTH / 12*9;
    private int posY = 230;
    private ButtonListener b = new ButtonListener();
    private boolean musicOn = true;
    public static boolean soundfxOn = true;
    private AudioLoad l;
    private String username;

    public MainMenu(){

        l = new AudioLoad("MainMenu.wav");
        gameModePanel = new GameMode();
        optionsPanel = new Options();

        howToPlay = addMenuButton("howtoplay.png");
        play = addMenuButton("play.png");
        options = addMenuButton("options.png");

        initFrame();

    }

    /**
     * Εδώ αρχικοποιείται το frame μαζί με ότι στοιχεία θέλουμε να προσθέσουμε.
     * Καλείται τελευταία στον constructor, αφού έχουν δημιουργηθεί όλα τα επι-
     * μέρους στοιχεία όπως Buttons, Panels, κλπ.
     */
    private void initFrame(){

        JButton exitButton = new MenuButton("exit.png", 1001, 5, 0);
        exitButton.addActionListener(b);

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        l.playMenuClip();

        do{
            username = JOptionPane.showInputDialog(null, "Give a username.");
        }while(username == null || JOptionPane.showInputDialog(null, "Give a username.").isEmpty());
    }

    /**
     * Μέθοδος που αρχικοποιεί τα κουμπιά για το μενού. Το posY αυξάνεται κατά 70
     * κάθε φορά που προστίθεται ένα κουμπί ώστε να γίνεται σωστή κατακόρυφη
     * στοίχιση.
     */
    private MenuButton addMenuButton(String path){

        posY += 70;
        int posX = 130;
        MenuButton button = new MenuButton(path, posX, posY, 51);
        button.addActionListener(b);
        return button;
    }

    /**
     * Εσωτερική κλάση τύπου JPanel η οποία χρησιμοποιείται για να εμφανίζεται
     * το παραθυράκι επιλογής game mode αφού πατηθεί το κουμπί Play.
     */
    class GameMode extends JPanel{

        private Image background;
        private ImageIcon exitIcon, exitIconHover, titleImage,
                 pvaiTitle, pvpTitle;
        private MenuButton pVsAi, pVsP;
        private JButton exit;
        private JLabel title;
        private boolean flag = false;
        private boolean flagOptions = true;

        private GameMode(){

            title = new JLabel();

            titleImage = new ImageIcon(LoadAssets.load("selectgamemode.png"));
            pvaiTitle = new ImageIcon(LoadAssets.load("titlepvai.png"));
            pvpTitle = new ImageIcon(LoadAssets.load("titlepvp.png"));

            title.setIcon(titleImage);
            title.setBounds(60, 11, titleImage.getIconWidth(), titleImage.getIconHeight());

            pVsAi = new MenuButton("pvai.png", 39, 46, 0);
            pVsAi.addActionListener(b);

            pVsP = new MenuButton("pvp.png", 39, 98, 0);
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

    class Options extends JPanel{

        private Image background;
        private ImageIcon exitIcon, exitIconHover, titleImage, musicTitle, soundFXTitle, optionsSquare;
        private JLabel music;
        private JLabel soundFX;
        private MenuButton musicButton;
        private MenuButton soundFXButton;
        private JButton exit;
        private JLabel title;
        private boolean flag = false;
        private boolean flagOptions = true;

        public Options(){

            title = new JLabel();
            music = new JLabel();
            soundFX = new JLabel();

            titleImage = new ImageIcon(LoadAssets.load("titleoptions.png"));
            musicTitle = new ImageIcon(LoadAssets.load("titlemusic.png"));
            soundFXTitle = new ImageIcon(LoadAssets.load("titlesoundfx.png"));

            title.setIcon(titleImage);
            title.setBounds(130, 11, titleImage.getIconWidth(), titleImage.getIconHeight());

            music.setIcon(musicTitle);
            music.setBounds(44, 60, musicTitle.getIconWidth(), musicTitle.getIconHeight());

            soundFX.setIcon(soundFXTitle);
            soundFX.setBounds(44, 110, soundFXTitle.getIconWidth(), soundFXTitle.getIconHeight());

            musicButton = new MenuButton("redsquare.png", 250, 60, 0);
            musicButton.addActionListener(b);
            soundFXButton = new MenuButton("redsquare.png", 250, 110, 0);
            soundFXButton.addActionListener(b);

            readImages();
            addExit();
            initPanel();
        }

        private void readImages(){

            try {
                background = ImageIO.read(LoadAssets.load("gameoptions.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            exitIcon = new ImageIcon(LoadAssets.load("exit.png"));
            exitIconHover = new ImageIcon(LoadAssets.load("sexit.png"));
            optionsSquare = new ImageIcon(LoadAssets.load("redsquare.png"));
        }


        private void initPanel(){
            setLayout(null);
            add(exit);
            add(title);
            add(music);
            add(soundFX);
            add(musicButton);
            add(soundFXButton);

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
         * Θέτει το Options Panel στην αρχική του κατάσταση.
         */
        public void panelRestart(){
            title.setIcon(titleImage);
            title.setBounds(60, 11, titleImage.getIconWidth(), titleImage.getIconHeight());
        }
    }

    /**
     * Ειδική κλάση για να ελέγχει ποιό κουμπί πατήθηκε και να
     * ακολουθει τα αντίστοιχα βήματα.
     */
    class ButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == howToPlay){

                if(soundfxOn) howToPlay.playSound();
            }

            else if(e.getSource() == play){

                optionsPanel.setPanelInvisible();
                gameModePanel.setPanelVisible();

                if(!optionsPanel.flagOptions){
                    optionsPanel.panelRestart();
                }

                if(soundfxOn) play.playSound();
            }

            else if(e.getSource() == options){

                gameModePanel.setPanelInvisible();
                optionsPanel.setPanelVisible();

                if(!gameModePanel.flagOptions){
                    gameModePanel.panelRestart();
                }

                if(soundfxOn) options.playSound();

            }
            /*
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

                if(soundfxOn) options.playSound();

            }
            /*
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
                if(soundfxOn) gameModePanel.pVsP.playSound();
            }
            /*
              Κάθε φορά που πατιέται το κουμπί Player vs A.I εξαφανίζει τα υπάρχοντα κουμπιά
              και αλλάζει τον τίτλο του gameOptions Panel σε Player vs A.I. Επίσης αλλάζει
              τα Bounds για να τοποθετείται σωστά μέσα στο panel.
             */
            else if(e.getSource() == gameModePanel.pVsAi){

                gameModePanel.pVsP.setVisible(false);
                gameModePanel.pVsAi.setVisible(false);

                gameModePanel.title.setIcon(gameModePanel.pvaiTitle);
                gameModePanel.title.setBounds(30, -2, gameModePanel.pvaiTitle.getIconWidth(), gameModePanel.pvaiTitle.getIconHeight());

                gameModePanel.flagOptions = false;
                if(soundfxOn) gameModePanel.pVsAi.playSound();
            }

            else if(e.getSource() == optionsPanel.exit){

                optionsPanel.setPanelInvisible();

                if(!optionsPanel.flagOptions){
                    optionsPanel.panelRestart();
                }

                options.playSound();
            }

            else if(e.getSource() == optionsPanel.musicButton){

                optionsPanel.musicButton.setIcon("hredsquare.png");
                if(musicOn){
                    musicOn = false;
                    l.closeClip();
                }
                else{
                    musicOn = true;
                    l.playMenuClip();
                }
            }

            else if(e.getSource() == optionsPanel.soundFXButton){

                optionsPanel.soundFXButton.setIcon("hredsquare.png");
                if(soundfxOn) soundfxOn = false;
                else soundfxOn = true;
            }

            else{
                System.exit(0);
                //setState(Frame.ICONIFIED);
            }
        }
    }
}
