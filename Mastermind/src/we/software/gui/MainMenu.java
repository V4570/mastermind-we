package we.software.gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import we.software.mastermind.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by bill on 3/28/17.
 * This class is the frame that hosts the menu of the game.
 */
public class MainMenu extends JFrame {

	private MenuButton howToPlay, play, options;            //Main menu buttons for functionality
	private GameMode gameModePanel;                         //The panel that appears for the user to select a game-mode
	private Options optionsPanel;                           //The panel that appears for the user to select game options
	private HowToPlay howToPlayPanel;
    private final int WIDTH = 1280;
	private final int HEIGHT = WIDTH / 16 * 9;
	private int posY = 230;                                 //The starting vertical position of the menu buttons
	private ButtonListener b = new ButtonListener();
	public static boolean musicOn = true;                  //Variable that controls the music (on/off)
	public static boolean soundfxOn = false;                //Variable that controls the sound effects (on/off)
	private AudioLoad menuMusic;                            //The audio file of the music tha plays in the menu
	private String username = null;
	private JButton minimizeButton;
	private Client client = null;
	private GameGui previous = null;
	private boolean modeSelected = false;
	private MenuButton selected = null;
	private ChatGui chatGui;


	public MainMenu() {
			
        try {
        	client = new Client();
            client.logMeIn("test1", "test1");

        } catch (IOException e) {
            e.printStackTrace();
        }
		client.getcListener().setMainMenu(this);
        
		menuMusic = new AudioLoad("MainMenu.wav");
		gameModePanel = new GameMode();
		optionsPanel = new Options();
		howToPlayPanel = new HowToPlay();
		chatGui = new ChatGui();
		chatGui.setBoundsForMainMenu();
		client.getcListener().setChatGui(chatGui);
		chatGui.setClient(client);
		chatGui.setMainMenu(this);

		howToPlay = addMenuButton("howtoplayv2.png");
		play = addMenuButton("playv2.png");
		options = addMenuButton("optionsv2.png");

		PreloadImages.preloadImages();
		initFrame();

	}

    /**
     * Initializes the frame. Moved from the constructor for cleaner code.
     */
	private void initFrame() {

		JButton exitButton = new MenuButton("exitv2.png", 1240, 5, 0, 0);
		minimizeButton = new MenuButton("minimize.png", 1200, 5, 0, 0);
		exitButton.addActionListener(b);
		minimizeButton.addActionListener(b);

		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		try {
			setIconImage(ImageIO.read(LoadAssets.load("master.png")));
			setContentPane(new JLabel(new ImageIcon(ImageIO.read(LoadAssets.load("Backgroundv2.png")))));
		} catch (IOException exc) {
			exc.printStackTrace();
		}

		while(!modeSelected){

		    modeSelected = true;
		}

		add(howToPlay);
		add(play);
		add(options);
		add(gameModePanel);
		add(howToPlayPanel);
		add(optionsPanel);
		add(exitButton);
		add(minimizeButton);
		add(chatGui);

		setTitle("Mastermind WE - Pre Alpha 0.0.1");
		setUndecorated(true);
		setVisible(true);
		if(musicOn) menuMusic.playMenuClip();

		//getUsername();

	}


	public void setFrameVisible(GameGui previous) {
	    this.previous = previous;
	    previous.dispose();
		setVisible(true);
		if(musicOn) menuMusic.playMenuClip();
		selected.setUnselected();
		selected = null;
		chatGui.setBoundsForMainMenu();
		add(chatGui);
	}

    /**
     * Prompts the user to enter a username and a password to enter the system.
     */
	public void getUsername() {

		String[] options = { "OK" };
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Enter Your username: ");
		JTextField txt = new JTextField(10);

		boolean isOk = true;
		int selectedOption = 0;

		panel.add(lbl);
		panel.add(txt);
		// Checking Server connectivity remove lines 127-131 to make it
		// like it was
		// H allagh pou egine einai proxeirorammeni alla leitourgikh
		// Zitaei apo ton xrhsth na dwsei onoma kai stin sunexeia tsekarei an
		// ekeinh th stigmh einai kapoios me auto to onoma sundedemenos ston
		// server an nai tote tou zitaei na
		// ksanavalei onoma an oxi sunexizei kanonika
		// gia na xrisimopoieisetai ton server tha anevasw ta stoixeia tou server pou prepei
		// na alaxthoun sto arxeio Client stis seires 16 'Server' kai 17 'PORT'
		// parakalw na min ginoun upload sto github gia logous asfaleias
		// o server einai panw sxedon 24/7


		while ((selectedOption != 1 && txt.getText().equals(""))) {
				selectedOption = JOptionPane.showOptionDialog(null, panel, "Login", JOptionPane.NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				username = txt.getText();

		}
		/*try {
			player.addMe(username);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

    /**
     *
     * Initializes and returns a menu button with the correct coordinates.
	 * This is used so we can initialize many buttons at the same time
	 * and put them in the correct position, without having to manually input
	 * the coordinates.
     */
	private MenuButton addMenuButton(String path) {

		posY += 120;
		int posX = 25;
		MenuButton button = new MenuButton(path, posX, posY, 0, 0);
		button.addActionListener(b);
		return button;
	}

	/**
     * This class handles the properties of the game mode panel.
     */
	class GameMode extends JPanel {

		private Image background;
		private MenuButton pVsAi, pVsP;
		private boolean flag = false;
		private boolean flagOptions = true;

		private GameMode() {

			pVsAi = new MenuButton("pvai.png", 800, 44, 0, 0);
			pVsAi.addActionListener(b);

			pVsP = new MenuButton("pvp.png", 506, 44, 0, 0);
			pVsP.addActionListener(b);

			readImages();
			initPanel();

		}

		private void readImages() {

			try {
				background = ImageIO.read(LoadAssets.load("playPanel.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		private void initPanel() {
			setLayout(null);
			add(pVsAi);
			add(pVsP);

			setBounds(193, 300, background.getWidth(null), background.getHeight(null));
			setOpaque(false);
			setVisible(flag);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(background, 0, 0, null);
		}

		private void setPanelVisible() {
			if (!flag) {
				setVisible(!flag);
				flag = !flag;
			}
		}

		private void setPanelInvisible() {
			if (flag) {
				setVisible(!flag);
				flag = !flag;
			}
		}

		private void panelRestart() {
			pVsP.setVisible(true);
			pVsAi.setVisible(true);
		}
	}

    /**
     * This class handles the properties of the options panel.
     */
	class Options extends JPanel {

		private Image background;                                                 //The background image of the panel
		private MenuButton musicButton, soundFXButton;
		private JLabel title;
		private boolean flag = false;
		private boolean flagOptions = true;

		public Options() {

			readImages();
			initPanel();
		}

		private void readImages() {

			try {
				background = ImageIO.read(LoadAssets.load("gameoptions.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		private void initPanel() {

            musicButton = new MenuButton("switch.png", 254, 45, 0, 0);
            musicButton.addActionListener(b);

            soundFXButton = new MenuButton("switch.png", 254, 85, 0, 0);
            soundFXButton.addActionListener(b);


			setLayout(null);
			add(musicButton);
			add(soundFXButton);

			setBounds(322, 538, background.getWidth(null), background.getHeight(null));
			setOpaque(false);
			setVisible(flag);
		}

		@Override
		protected void paintComponent(Graphics g) {

			super.paintComponent(g);
			g.drawImage(background, 0, 0, null);
		}

		/**
         * Sets the panel visible after checking it is invisible.
         */
		public void setPanelVisible() {
			if (!flag) {
				setVisible(!flag);
				flag = !flag;
			}
		}

		/**
         * Sets the panel invisible after checking it is visible.
         */
		public void setPanelInvisible() {
			if (flag) {
				setVisible(!flag);
				flag = !flag;
			}
		}
	}

    /**
     * The panel that appears after the users selects the "How to play" button.
     * Explains the rules of the game and how to play it.
     */
	class HowToPlay extends JPanel{

        private Image background;                                       //The background image of the panel
        private boolean flag = false;

        public HowToPlay(){

            try{
                background = ImageIO.read(LoadAssets.load("howToPlayv2.png"));
            }catch (IOException e){
                e.printStackTrace();
            }
            initPanel();
        }

        private void initPanel(){

            setLayout(null);

            setBounds(554, 36, background.getWidth(null), background.getHeight(null));
            setOpaque(false);
            setVisible(flag);
        }

        @Override
        protected void paintComponent(Graphics g){

            super.paintComponent(g);
            g.drawImage(background, 0, 0, null);
        }

        /**
         * Sets the panel visible after checking it is invisible.
         */
        public void setPanelVisible() {
            if (!flag) {
                setVisible(!flag);
                flag = !flag;
            }
        }

        /**
         * Sets the panel invisible after checking it is visible.
         */
        public void setPanelInvisible() {
            if (flag) {
                setVisible(!flag);
                flag = !flag;
            }
        }
    }

    /**
	 * Handles the action after a button has been pressed.
	 */
	class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == howToPlay) {

				if (soundfxOn)
					howToPlay.playSound();

                if(!(selected == null)) selected.setUnselected();
				howToPlay.staySelected();
				selected = howToPlay;
				optionsPanel.setPanelInvisible();
				gameModePanel.setPanelInvisible();
				howToPlayPanel.setPanelVisible();
			}
			else if (e.getSource() == play) {

				optionsPanel.setPanelInvisible();
				howToPlayPanel.setPanelInvisible();
				gameModePanel.setPanelVisible();
                if(!(selected == null)) selected.setUnselected();
				play.staySelected();
				selected = play;

				if (soundfxOn)
					play.playSound();
			}
			else if (e.getSource() == options) {

				gameModePanel.setPanelInvisible();
				howToPlayPanel.setPanelInvisible();
				optionsPanel.setPanelVisible();
				if(!(selected == null)) selected.setUnselected();
				options.staySelected();
				selected = options;

				if (!gameModePanel.flagOptions) {
					gameModePanel.panelRestart();
				}

				if (soundfxOn)
					options.playSound();

			}

			else if (e.getSource() == gameModePanel.pVsP) {

				gameModePanel.pVsP.setVisible(false);
				gameModePanel.pVsAi.setVisible(false);

				gameModePanel.flagOptions = false;
				if (soundfxOn)
					gameModePanel.pVsP.playSound();

				GameGui gameGui = new GameGui(MainMenu.this, 1, chatGui);
				setVisible(false);

				gameModePanel.panelRestart();
				gameModePanel.setPanelInvisible();
				if (musicOn)
					menuMusic.closeClip();
			}
			else if (e.getSource() == gameModePanel.pVsAi) {

				gameModePanel.pVsP.setVisible(false);
				gameModePanel.pVsAi.setVisible(false);

				gameModePanel.flagOptions = false;
				if (soundfxOn)
					gameModePanel.pVsAi.playSound();

				GameGui gameGui = new GameGui(MainMenu.this, 0, chatGui);
				setVisible(false);

				gameModePanel.panelRestart();
				gameModePanel.setPanelInvisible();
				if (musicOn)
					menuMusic.closeClip();
			}
			else if (e.getSource() == optionsPanel.musicButton) {

				if (musicOn) {
					musicOn = false;
					menuMusic.closeClip();
					optionsPanel.musicButton.staySelected();
				} else {
					musicOn = true;
					optionsPanel.musicButton.setUnselected();
					menuMusic.playMenuClip();
				}
			}
			else if (e.getSource() == optionsPanel.soundFXButton) {

				if (soundfxOn) {
                    soundfxOn = false;
                    optionsPanel.soundFXButton.staySelected();
                }
				else {
                    soundfxOn = true;
                    optionsPanel.soundFXButton.setUnselected();
                }
			}
            else if (e.getSource() == minimizeButton){
			    setState(Frame.ICONIFIED);
            }
			else {

				int exit = JOptionPane.showConfirmDialog(MainMenu.this, "Are you sure you want to exit?", "Exit",
						JOptionPane.YES_NO_OPTION);
				if (exit == 0)
					System.exit(0);
				// setState(Frame.ICONIFIED);
			}
		}
	}
}
