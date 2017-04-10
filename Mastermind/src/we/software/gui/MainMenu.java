package we.software.gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import we.software.mastermind.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by bill on 3/28/17.
 */
public class MainMenu extends JFrame {

	private MenuButton howToPlay, play, options;
	private GameMode gameModePanel;
	private Options optionsPanel;
	private final int WIDTH = 1024;
	private final int HEIGHT = WIDTH / 12 * 9;
	private int posY = 230;
	private ButtonListener b = new ButtonListener();
	public static boolean musicOn = true;
	public static boolean soundfxOn = true;
	private AudioLoad l;
	private String username = null;

	public MainMenu() {

		l = new AudioLoad("MainMenu.wav");
		gameModePanel = new GameMode();
		optionsPanel = new Options();

		howToPlay = addMenuButton("howtoplay.png");
		play = addMenuButton("play.png");
		options = addMenuButton("options.png");

		initFrame();

	}

	/**
	 * Ξ•Ξ΄Ο� Ξ±Ο�Ο‡ΞΉΞΊΞΏΟ€ΞΏΞΉΞµΞ―Ο„Ξ±ΞΉ Ο„ΞΏ frame ΞΌΞ±Ξ¶Ξ― ΞΌΞµ Ο�Ο„ΞΉ
	 * ΟƒΟ„ΞΏΞΉΟ‡ΞµΞ―Ξ± ΞΈΞ­Ξ»ΞΏΟ…ΞΌΞµ Ξ½Ξ± Ο€Ο�ΞΏΟƒΞΈΞ­ΟƒΞΏΟ…ΞΌΞµ.
	 * Ξ�Ξ±Ξ»ΞµΞ―Ο„Ξ±ΞΉ Ο„ΞµΞ»ΞµΟ…Ο„Ξ±Ξ―Ξ± ΟƒΟ„ΞΏΞ½ constructor, Ξ±Ο†ΞΏΟ�
	 * Ξ­Ο‡ΞΏΟ…Ξ½ Ξ΄Ξ·ΞΌΞΉΞΏΟ…Ο�Ξ³Ξ·ΞΈΞµΞ― Ο�Ξ»Ξ± Ο„Ξ± ΞµΟ€ΞΉ- ΞΌΞ­Ο�ΞΏΟ…Ο‚
	 * ΟƒΟ„ΞΏΞΉΟ‡ΞµΞ―Ξ± Ο�Ο€Ο‰Ο‚ Buttons, Panels, ΞΊΞ»Ο€.
	 */
	private void initFrame() {

		JButton exitButton = new MenuButton("exit.png", 1001, 5, 0);
		exitButton.addActionListener(b);

		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		try {
			setIconImage(ImageIO.read(LoadAssets.load("master.png")));
			setContentPane(new JLabel(new ImageIcon(ImageIO.read(LoadAssets.load("Background.png")))));
		} catch (IOException exc) {
			exc.printStackTrace();
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

		getUsername();

	}

	private void getUsername() {

		String[] options = { "OK" };
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Enter Your username: ");
		JTextField txt = new JTextField(10);
		Client player = null;
		boolean isOk = true;
		int selectedOption = 0;

		panel.add(lbl);
		panel.add(txt);
		// Checking Server connectivity remove 99-105, 107, 113-127 to make it
		// like it was
		// H allagh pou egine einai proxeirorammeni alla leitourgikh
		// Zitaei apo ton xrhsth na dwsei onoma kai stin sunexeia tsekarei an
		// ekeinh th stigmh einai kapoios me auto to onoma sundedemenos ston
		// server an nai tote tou zitaei na
		// ksanavalei onoma an oxi sunexizei kanonika
		//gia na xrisimopoieisetai ton server tha anevasw ta stoixeia tou server pou prepei
		//na alaxthoun sto arxeio Client stis seires 16 'Server' kai 17 'PORT'
		//parakalw na min ginoun upload sto github gia logous asfaleias
		//o server einai panw sxedon 24/7
		player = new Client();
		try {
			player.startListening();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while ((selectedOption != 1 && txt.getText().equals(""))) {
			while (!player.getAddMeValue()) {
				selectedOption = JOptionPane.showOptionDialog(null, panel, "Login", JOptionPane.NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				username = txt.getText();

				try {
					player.setUsername(username);
					player.addMe();
					try {
						Thread.currentThread().sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (IOException e) {
					System.out.println("Couldn t create Client object");
				}

			}
		}
	}

	/**
	 * Ξ�Ξ­ΞΈΞΏΞ΄ΞΏΟ‚ Ο€ΞΏΟ… Ξ±Ο�Ο‡ΞΉΞΊΞΏΟ€ΞΏΞΉΞµΞ― Ο„Ξ± ΞΊΞΏΟ…ΞΌΟ€ΞΉΞ¬ Ξ³ΞΉΞ±
	 * Ο„ΞΏ ΞΌΞµΞ½ΞΏΟ�. Ξ¤ΞΏ posY Ξ±Ο…ΞΎΞ¬Ξ½ΞµΟ„Ξ±ΞΉ ΞΊΞ±Ο„Ξ¬ 70 ΞΊΞ¬ΞΈΞµ
	 * Ο†ΞΏΟ�Ξ¬ Ο€ΞΏΟ… Ο€Ο�ΞΏΟƒΟ„Ξ―ΞΈΞµΟ„Ξ±ΞΉ Ξ­Ξ½Ξ± ΞΊΞΏΟ…ΞΌΟ€Ξ― Ο�ΟƒΟ„Ξµ Ξ½Ξ±
	 * Ξ³Ξ―Ξ½ΞµΟ„Ξ±ΞΉ ΟƒΟ‰ΟƒΟ„Ξ® ΞΊΞ±Ο„Ξ±ΞΊΟ�Ο�Ο…Ο†Ξ· ΟƒΟ„ΞΏΞ―Ο‡ΞΉΟƒΞ·.
	 */
	private MenuButton addMenuButton(String path) {

		posY += 70;
		int posX = 130;
		MenuButton button = new MenuButton(path, posX, posY, 51);
		button.addActionListener(b);
		return button;
	}

	/**
	 * Ξ•ΟƒΟ‰Ο„ΞµΟ�ΞΉΞΊΞ® ΞΊΞ»Ξ¬ΟƒΞ· Ο„Ο�Ο€ΞΏΟ… JPanel Ξ· ΞΏΟ€ΞΏΞ―Ξ±
	 * Ο‡Ο�Ξ·ΟƒΞΉΞΌΞΏΟ€ΞΏΞΉΞµΞ―Ο„Ξ±ΞΉ Ξ³ΞΉΞ± Ξ½Ξ± ΞµΞΌΟ†Ξ±Ξ½Ξ―Ξ¶ΞµΟ„Ξ±ΞΉ Ο„ΞΏ
	 * Ο€Ξ±Ο�Ξ±ΞΈΟ…Ο�Ξ¬ΞΊΞΉ ΞµΟ€ΞΉΞ»ΞΏΞ³Ξ®Ο‚ game mode Ξ±Ο†ΞΏΟ� Ο€Ξ±Ο„Ξ·ΞΈΞµΞ―
	 * Ο„ΞΏ ΞΊΞΏΟ…ΞΌΟ€Ξ― Play.
	 */
	class GameMode extends JPanel {

		private Image background;
		private ImageIcon exitIcon, exitIconHover, titleImage, pvaiTitle, pvpTitle;
		private MenuButton pVsAi, pVsP;
		private JButton exit;
		private JLabel title;
		private boolean flag = false;
		private boolean flagOptions = true;

		private GameMode() {

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

		private void readImages() {

			try {
				background = ImageIO.read(LoadAssets.load("gameoptions.png"));
				exitIcon = new ImageIcon(ImageIO.read(LoadAssets.load("exit.png")));
				exitIconHover = new ImageIcon(ImageIO.read(LoadAssets.load("sexit.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		private void initPanel() {
			setLayout(null);
			add(exit);
			add(title);
			add(pVsAi);
			add(pVsP);

			setBounds(420, 280, background.getWidth(null), background.getHeight(null));
			setOpaque(false);
			setVisible(flag);
		}

		private void addExit() {
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
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(background, 0, 0, null);
		}

		/**
		 * Ξ�Ξ­Ο„ΞµΞΉ Ο„ΞΏ Panel visible ΞµΟ†Ο�ΟƒΞΏΞ½ ΞµΞ»Ξ­Ξ³ΞΎΞµΞΉ Ο�Ο„ΞΉ
		 * ΞµΞ―Ξ½Ξ±ΞΉ ΞΊΞ»ΞµΞΉΟƒΟ„Ο�.
		 */
		public void setPanelVisible() {
			if (!flag) {
				setVisible(!flag);
				flag = !flag;
			}
		}

		/**
		 * Ξ�Ξ­Ο„ΞµΞΉ Ο„ΞΏ Panel invisible ΞµΟ†Ο�ΟƒΞΏΞ½ ΞµΞ»Ξ­Ξ³ΞΎΞµΞΉ Ο�Ο„ΞΉ
		 * ΞµΞ―Ξ½Ξ±ΞΉ Ξ±Ξ½ΞΏΞΉΟ‡Ο„Ο�.
		 */
		public void setPanelInvisible() {
			if (flag) {
				setVisible(!flag);
				flag = !flag;
			}
		}

		/**
		 * Ξ�Ξ­Ο„ΞµΞΉ Ο„ΞΏ gameOptions Panel ΟƒΟ„Ξ·Ξ½ Ξ±Ο�Ο‡ΞΉΞΊΞ® Ο„ΞΏΟ…
		 * ΞΊΞ±Ο„Ξ¬ΟƒΟ„Ξ±ΟƒΞ·.
		 */
		public void panelRestart() {
			pVsP.setVisible(true);
			pVsAi.setVisible(true);
			title.setIcon(titleImage);
			title.setBounds(60, 11, titleImage.getIconWidth(), titleImage.getIconHeight());
		}
	}

	class Options extends JPanel {

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

		public Options() {

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

		private void readImages() {

			try {
				background = ImageIO.read(LoadAssets.load("gameoptions.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			exitIcon = new ImageIcon(LoadAssets.load("exit.png"));
			exitIconHover = new ImageIcon(LoadAssets.load("sexit.png"));
			optionsSquare = new ImageIcon(LoadAssets.load("redsquare.png"));
		}

		private void initPanel() {
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

		private void addExit() {
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
		protected void paintComponent(Graphics g) {

			super.paintComponent(g);
			g.drawImage(background, 0, 0, null);
		}

		/**
		 * Ξ�Ξ­Ο„ΞµΞΉ Ο„ΞΏ Panel visible ΞµΟ†Ο�ΟƒΞΏΞ½ ΞµΞ»Ξ­Ξ³ΞΎΞµΞΉ Ο�Ο„ΞΉ
		 * ΞµΞ―Ξ½Ξ±ΞΉ ΞΊΞ»ΞµΞΉΟƒΟ„Ο�.
		 */
		public void setPanelVisible() {
			if (!flag) {
				setVisible(!flag);
				flag = !flag;
			}
		}

		/**
		 * Ξ�Ξ­Ο„ΞµΞΉ Ο„ΞΏ Panel invisible ΞµΟ†Ο�ΟƒΞΏΞ½ ΞµΞ»Ξ­Ξ³ΞΎΞµΞΉ Ο�Ο„ΞΉ
		 * ΞµΞ―Ξ½Ξ±ΞΉ Ξ±Ξ½ΞΏΞΉΟ‡Ο„Ο�.
		 */
		public void setPanelInvisible() {
			if (flag) {
				setVisible(!flag);
				flag = !flag;
			}
		}

		/**
		 * Ξ�Ξ­Ο„ΞµΞΉ Ο„ΞΏ Options Panel ΟƒΟ„Ξ·Ξ½ Ξ±Ο�Ο‡ΞΉΞΊΞ® Ο„ΞΏΟ…
		 * ΞΊΞ±Ο„Ξ¬ΟƒΟ„Ξ±ΟƒΞ·.
		 */
		public void panelRestart() {
			title.setIcon(titleImage);
			title.setBounds(60, 11, titleImage.getIconWidth(), titleImage.getIconHeight());
		}
	}

	/**
	 * Ξ•ΞΉΞ΄ΞΉΞΊΞ® ΞΊΞ»Ξ¬ΟƒΞ· Ξ³ΞΉΞ± Ξ½Ξ± ΞµΞ»Ξ­Ξ³Ο‡ΞµΞΉ Ο€ΞΏΞΉΟ� ΞΊΞΏΟ…ΞΌΟ€Ξ―
	 * Ο€Ξ±Ο„Ξ®ΞΈΞ·ΞΊΞµ ΞΊΞ±ΞΉ Ξ½Ξ± Ξ±ΞΊΞΏΞ»ΞΏΟ…ΞΈΞµΞΉ Ο„Ξ± Ξ±Ξ½Ο„Ξ―ΟƒΟ„ΞΏΞΉΟ‡Ξ±
	 * Ξ²Ξ®ΞΌΞ±Ο„Ξ±.
	 */
	class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == howToPlay) {

				if (soundfxOn)
					howToPlay.playSound();
			}

			else if (e.getSource() == play) {

				optionsPanel.setPanelInvisible();
				gameModePanel.setPanelVisible();

				if (!optionsPanel.flagOptions) {
					optionsPanel.panelRestart();
				}

				if (soundfxOn)
					play.playSound();
			}

			else if (e.getSource() == options) {

				gameModePanel.setPanelInvisible();
				optionsPanel.setPanelVisible();

				if (!gameModePanel.flagOptions) {
					gameModePanel.panelRestart();
				}

				if (soundfxOn)
					options.playSound();

			}
			/*
			 * Ξ�Ξ»ΞµΞ―Ξ½ΞµΞΉ Ο„ΞΏ gameOptions Panel ΞΊΞ±ΞΉ ΞΊΞ±Ξ»ΞµΞ― Ο„Ξ·Ξ½
			 * ΞΌΞ­ΞΈΞΏΞ΄ΞΏ panelRestart() Ο€ΞΏΟ… ΞΈΞ­Ο„ΞµΞΉ Ο„ΞΏ Panel ΟƒΟ„Ξ·Ξ½
			 * Ξ±Ο�Ο‡ΞΉΞΊΞ® Ο„ΞΏΟ… ΞΊΞ±Ο„Ξ¬ΟƒΟ„Ξ±ΟƒΞ· ΞµΟ†Ο�ΟƒΞΏΞ½ Ξ­Ο‡ΞµΞΉ
			 * Ο€Ξ±Ο„Ξ·ΞΈΞµΞ― ΞΊΞ¬Ο€ΞΏΞΉΞΏ ΞΊΞΏΟ…ΞΌΟ€Ξ― ΞΌΞ­ΟƒΞ± ΟƒΟ„ΞΏ Panel
			 * ΞΊΞ±ΞΉ Ξ­Ο‡ΞµΞΉ Ξ±Ξ»Ξ»Ξ¬ΞΎΞµΞΉ Ξ· Ξ΄ΞΏΞΌΞ® Ο„ΞΏΟ….
			 */
			else if (e.getSource() == gameModePanel.exit) {

				gameModePanel.setPanelInvisible();

				if (!gameModePanel.flagOptions) {
					gameModePanel.panelRestart();
				}

				if (soundfxOn)
					options.playSound();

			}
			/*
			 * Ξ�Ξ¬ΞΈΞµ Ο†ΞΏΟ�Ξ¬ Ο€ΞΏΟ… Ο€Ξ±Ο„ΞΉΞ­Ο„Ξ±ΞΉ Ο„ΞΏ ΞΊΞΏΟ…ΞΌΟ€Ξ―
			 * Player vs Player ΞµΞΎΞ±Ο†Ξ±Ξ½Ξ―Ξ¶ΞµΞΉ Ο„Ξ± Ο…Ο€Ξ¬Ο�Ο‡ΞΏΞ½Ο„Ξ±
			 * ΞΊΞΏΟ…ΞΌΟ€ΞΉΞ¬ ΞΊΞ±ΞΉ Ξ±Ξ»Ξ»Ξ¬Ξ¶ΞµΞΉ Ο„ΞΏΞ½ Ο„Ξ―Ο„Ξ»ΞΏ Ο„ΞΏΟ…
			 * gameOptions Panel ΟƒΞµ Player vs Player. Ξ•Ο€Ξ―ΟƒΞ·Ο‚
			 * Ξ±Ξ»Ξ»Ξ¬Ξ¶ΞµΞΉ Ο„Ξ± Bounds Ξ³ΞΉΞ± Ξ½Ξ± Ο„ΞΏΟ€ΞΏΞΈΞµΟ„ΞµΞ―Ο„Ξ±ΞΉ
			 * ΟƒΟ‰ΟƒΟ„Ξ¬ ΞΌΞ­ΟƒΞ± ΟƒΟ„ΞΏ panel.
			 */
			else if (e.getSource() == gameModePanel.pVsP) {

				gameModePanel.pVsP.setVisible(false);
				gameModePanel.pVsAi.setVisible(false);

				gameModePanel.title.setBounds(30, -2, gameModePanel.pvpTitle.getIconWidth(),
						gameModePanel.pvpTitle.getIconHeight());
				gameModePanel.title.setIcon(gameModePanel.pvpTitle);

				gameModePanel.flagOptions = false;
				if (soundfxOn)
					gameModePanel.pVsP.playSound();
			}
			/*
			 * Ξ�Ξ¬ΞΈΞµ Ο†ΞΏΟ�Ξ¬ Ο€ΞΏΟ… Ο€Ξ±Ο„ΞΉΞ­Ο„Ξ±ΞΉ Ο„ΞΏ ΞΊΞΏΟ…ΞΌΟ€Ξ―
			 * Player vs A.I ΞµΞΎΞ±Ο†Ξ±Ξ½Ξ―Ξ¶ΞµΞΉ Ο„Ξ± Ο…Ο€Ξ¬Ο�Ο‡ΞΏΞ½Ο„Ξ±
			 * ΞΊΞΏΟ…ΞΌΟ€ΞΉΞ¬ ΞΊΞ±ΞΉ Ξ±Ξ»Ξ»Ξ¬Ξ¶ΞµΞΉ Ο„ΞΏΞ½ Ο„Ξ―Ο„Ξ»ΞΏ Ο„ΞΏΟ…
			 * gameOptions Panel ΟƒΞµ Player vs A.I. Ξ•Ο€Ξ―ΟƒΞ·Ο‚ Ξ±Ξ»Ξ»Ξ¬Ξ¶ΞµΞΉ
			 * Ο„Ξ± Bounds Ξ³ΞΉΞ± Ξ½Ξ± Ο„ΞΏΟ€ΞΏΞΈΞµΟ„ΞµΞ―Ο„Ξ±ΞΉ ΟƒΟ‰ΟƒΟ„Ξ¬
			 * ΞΌΞ­ΟƒΞ± ΟƒΟ„ΞΏ panel.
			 */
			else if (e.getSource() == gameModePanel.pVsAi) {

				gameModePanel.pVsP.setVisible(false);
				gameModePanel.pVsAi.setVisible(false);

				gameModePanel.title.setIcon(gameModePanel.pvaiTitle);
				gameModePanel.title.setBounds(30, -2, gameModePanel.pvaiTitle.getIconWidth(),
						gameModePanel.pvaiTitle.getIconHeight());

				gameModePanel.flagOptions = false;
				if (soundfxOn)
					gameModePanel.pVsAi.playSound();
				new GameGui(MainMenu.this);
				setVisible(false);

				gameModePanel.panelRestart();
				gameModePanel.setPanelInvisible();
				if (musicOn)
					l.closeClip();
			}

			else if (e.getSource() == optionsPanel.exit) {

				optionsPanel.setPanelInvisible();

				if (!optionsPanel.flagOptions) {
					optionsPanel.panelRestart();
				}

				options.playSound();
			}

			else if (e.getSource() == optionsPanel.musicButton) {

				optionsPanel.musicButton.setIcon("hredsquare.png");
				if (musicOn) {
					musicOn = false;
					l.closeClip();
				} else {
					musicOn = true;
					l.playMenuClip();
				}
			}

			else if (e.getSource() == optionsPanel.soundFXButton) {

				optionsPanel.soundFXButton.setIcon("hredsquare.png");
				if (soundfxOn)
					soundfxOn = false;
				else
					soundfxOn = true;
			}

			else {

				int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit",
						JOptionPane.YES_NO_OPTION);
				if (exit == 0)
					System.exit(0);
				// setState(Frame.ICONIFIED);
			}
		}
	}

	public void setFrameVisible() {
		setVisible(true);
		l.playMenuClip();
	}
}
