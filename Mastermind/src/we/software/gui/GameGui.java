package we.software.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import we.software.mastermind.Client;
import we.software.mastermind.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 * Created by Bill on 06-Apr-17. This class is the frame that hosts each game.
 */
public class GameGui extends JFrame {

	private MainMenu previous;
	private final int WIDTH = 1280;
	private final int HEIGHT = WIDTH / 16 * 9;
	private ButtonListener btnListener = new ButtonListener();
	private MenuButton exitButton, minimize, optionsButton, backButton, music, soundFx; // Functionality
																						// buttons
	public HistoryPanel turnHistory; // The panel that holds all the turns of
										// the game
	private ChatGui chatGui; // The chat used in pvsp game mode.
	public Client client = null; // The client for the chat to work
	public SelectionButton selectionBtn1, selectionBtn2, selectionBtn3, selectionBtn4; // The
																						// buttons
																						// for
																						// the
																						// each
																						// place
																						// in
																						// the
																						// code.
	private SelectionButton checkBtn, sBtn; // checkBtn to register each round.
											// sBtn keeps the currently selected
											// selectionBtn.
	private SelectionButton redBtn, greenBtn, blueBtn, yellowBtn, whiteBtn, blackBtn; // The
																						// buttons
																						// for
																						// the
																						// color
																						// selection
	public NumbersPanel numbersPanel; // The panel that holds the number of each
										// round.
	public Game game;
	private Timer timer;
	private AudioLoad gameMusic;
	private int selectedBtn; // Integer that keeps the position of the selected
								// selectionBtn
	private int turn = 1; // Integer that holds current turn.
	private int[] turnGuess = { 0, 0, 0, 0 }; // Array to hold the current
												// turn's guess.
	private boolean notValid = false; // Boolean variable to check if
										// requirements have been met to
										// register each turn's guess
	private int gameMode; // 0 for pvsAi, 1 for pvsP

	public GameGui(MainMenu previous, int gM, ChatGui chat) {

		gameMusic = new AudioLoad("gameMusic.wav");
		if (MainMenu.musicOn)
			gameMusic.playMenuClip();
		this.gameMode = gM;
		if (gM == 0) {
			this.game = new Game(0);
		} else {
			this.game = new Game();
		}

		chatGui = chat;
		chatGui.setBoundsForGameGui();

		this.previous = previous;
		setUpButtons();
		initFrame();

	}

	public int getTurn() {
		return turn;
	}

	public void addTurn() {
		turn++;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Game getGame() {
		return game;
	}

	/**
	 * Initializes the frame and all of its components. All items added to the
	 * frame go here.
	 */
	private void initFrame() {

		try {
			setIconImage(ImageIO.read(LoadAssets.load("master.png")));
			setContentPane(new JLabel(new ImageIcon(ImageIO.read(LoadAssets.load("GameGuiv2.png")))));
		} catch (IOException exc) {
			exc.printStackTrace();
		}

		turnHistory = new HistoryPanel();
		numbersPanel = new NumbersPanel();

		add(exitButton);
		add(minimize);
		add(backButton);
		add(music);
		add(soundFx);
		add(selectionBtn1);
		add(selectionBtn2);
		add(selectionBtn3);
		add(selectionBtn4);
		add(checkBtn);
		add(blackBtn);
		add(whiteBtn);
		add(yellowBtn);
		add(redBtn);
		add(greenBtn);
		add(blueBtn);
		add(turnHistory);
		add(numbersPanel);
		add(chatGui);

		setTitle("Mastermind WE - Pre Alpha 0.0.1");
		setSize(WIDTH, HEIGHT);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

	}

	public void clearGame() {

		numbersPanel.resetRounds();
		turnHistory.resetHistoryPanel();
		notValid = false;
		turn = 1;
		repaint();
	}

	/**
	 * In this method all buttons of the frame are initialized. ~Great
	 * functionality comes with great responsibility.~
	 */
	private void setUpButtons() {

		exitButton = new MenuButton("exitv2.png", 1240, 5, 0, 0);
		exitButton.addActionListener(btnListener);

		minimize = new MenuButton("minimize.png", 1200, 5, 0, 0);
		minimize.addActionListener(btnListener);

		backButton = new MenuButton("backtomenuv2.png", 969, 660, 0, 0);
		backButton.addActionListener(btnListener);

		music = new MenuButton("switch.png", 1158, 583, 0, 0);
		if (!MainMenu.musicOn)
			music.staySelected();
		music.addActionListener(btnListener);

		soundFx = new MenuButton("switch.png", 1158, 626, 0, 0);
		if (!MainMenu.soundfxOn)
			soundFx.staySelected();
		soundFx.addActionListener(btnListener);

		selectionBtn1 = new SelectionButton("glassButton.png", 300, 350);
		selectionBtn1.addActionListener(btnListener);

		selectionBtn2 = new SelectionButton("glassButton.png", 400, 350);
		selectionBtn2.addActionListener(btnListener);

		selectionBtn3 = new SelectionButton("glassButton.png", 500, 350);
		selectionBtn3.addActionListener(btnListener);

		selectionBtn4 = new SelectionButton("glassButton.png", 600, 350);
		selectionBtn4.addActionListener(btnListener);

		checkBtn = new SelectionButton("CheckButton.png", 700, 380);
		checkBtn.addActionListener(btnListener);

		redBtn = new SelectionButton("redbtn.png", 300, 193);
		redBtn.addActionListener(btnListener);

		greenBtn = new SelectionButton("greenbtn.png", 400, 193);
		greenBtn.addActionListener(btnListener);

		blueBtn = new SelectionButton("bluebtn.png", 500, 193);
		blueBtn.addActionListener(btnListener);

		yellowBtn = new SelectionButton("yellowbtn.png", 600, 193);
		yellowBtn.addActionListener(btnListener);

		whiteBtn = new SelectionButton("whitebtn.png", 700, 193);
		whiteBtn.addActionListener(btnListener);

		blackBtn = new SelectionButton("blackbtn.png", 800, 193);
		blackBtn.addActionListener(btnListener);

	}

	public void makeButtonsUnavailable() {
		selectionBtn1.setEnabled(false);
		selectionBtn2.setEnabled(false);
		selectionBtn3.setEnabled(false);
		selectionBtn4.setEnabled(false);
		checkBtn.setEnabled(false);
	}

	public void makeButtonsAvailable() {
		selectionBtn1.setEnabled(true);
		selectionBtn2.setEnabled(true);
		selectionBtn3.setEnabled(true);
		selectionBtn4.setEnabled(true);
		checkBtn.setEnabled(true);
	}

	/**
	 * This panel shows up next to 'TURN' and displays the current turn.
	 */
	public class NumbersPanel extends JPanel {

		private ArrayList<BufferedImage> numbers;
		private int round = 1;

		public NumbersPanel() {

			numbers = (ArrayList<BufferedImage>) PreloadImages.getNumbers().clone();

			setBounds(850, 615, numbers.get(round - 1).getWidth(), numbers.get(round - 1).getHeight());
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			g.drawImage(numbers.get(round - 1), 0, 0, null);
		}

		public void changeRound() {

			round++;
			if (round > 10)
				round = 10;
			setBounds(850, 615, numbers.get(round - 1).getWidth(), numbers.get(round - 1).getHeight());
			repaint();
		}

		public void resetRounds() {
			round = 1;
		}
	}

	/**
	 * Special panel that shows the guess and the corresponding clues for each
	 * turn.
	 */
	public class HistoryPanel extends JPanel {

		private ArrayList<BufferedImage> rounds, clues, pegs;

		public HistoryPanel() {

			rounds = new ArrayList<>();
			clues = new ArrayList<>();

			pegs = (ArrayList<BufferedImage>) PreloadImages.getPegs().clone();

			setBounds(969, 35, 290, 538);
			setOpaque(false);

		}

		/**
		 * To show each turn in the special history panel, the code overrides
		 * paintComponent to paint the contents of the two arrayLists, rounds
		 * and clues which hold the guess and clues of the guess for each turn.
		 * Anytime a round is added to the arrayLists the code calls for the
		 * repaint method which repaints the content of the arrayLists.
		 */
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			int counter = 0; // Counts peg. Resets when it hits 4.
			int evalCounter = 0; // Counts clues. Resets when it hits 4.

			int round_X = 34; // Represents the width value for the colors.
								// Updates up to 4 colors then resets.
			int round_Y = 480;

			int eval_X = 228; // Represents the width value for the clues.
								// Updates up to 2 and then resets.
			int eval_Y = 478;

			for (BufferedImage img : rounds) {

				if (counter > 3) { // Resets round_X to start of line, moves one
									// line up and resets the counter.
					round_Y -= 50;
					round_X = 34;
					counter = 0;
				}

				g.drawImage(img, round_X, round_Y, null);
				round_X += 48;
				counter++;
			}

			for (BufferedImage img : clues) {

				// if(img == null) continue;

				if (evalCounter > 3) {
					eval_X = 228;
					eval_Y -= 50;
					evalCounter = 0;
				}
				if (evalCounter == 2) {
					g.drawImage(img, 228, eval_Y + 17, null);
				} else if (evalCounter == 3) {
					g.drawImage(img, 228 + 18, eval_Y + 17, null);
				} else
					g.drawImage(img, eval_X, eval_Y, null);

				evalCounter++;
				eval_X += 18;
			}

		}

		public void addToRounds(int id) {

			switch (id) {
			case 0:
				rounds.add(null);
				break;
			case 1:
				rounds.add(pegs.get(0));
				break;
			case 2:
				rounds.add(pegs.get(1));
				break;
			case 3:
				rounds.add(pegs.get(2));
				break;
			case 4:
				rounds.add(pegs.get(3));
				break;
			case 5:
				rounds.add(pegs.get(4));
				break;
			case 6:
				rounds.add(pegs.get(5));
				break;
			}
		}

		public void addToClues(int clue) {

			switch (clue) {
			case 0:
				clues.add(null);
				break;
			case 1:
				clues.add(pegs.get(6));
				break;
			case 2:
				clues.add(pegs.get(7));
				break;
			}
		}

		public void resetHistoryPanel() {

			rounds.clear();
			clues.clear();
		}
	}

	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == selectionBtn1) {

				selectionBtn1.staySelected();
				if (sBtn != null && sBtn != selectionBtn1)
					sBtn.setUnselected();
				selectedBtn = 0;
				sBtn = selectionBtn1;
			} else if (e.getSource() == selectionBtn2) {

				selectionBtn2.staySelected();
				if (sBtn != null && sBtn != selectionBtn2)
					sBtn.setUnselected();
				selectedBtn = 1;
				sBtn = selectionBtn2;
			} else if (e.getSource() == selectionBtn3) {

				selectionBtn3.staySelected();
				if (sBtn != null && sBtn != selectionBtn3)
					sBtn.setUnselected();
				selectedBtn = 2;
				sBtn = selectionBtn3;
			} else if (e.getSource() == selectionBtn4) {

				selectionBtn4.staySelected();
				if (sBtn != null && sBtn != selectionBtn4)
					sBtn.setUnselected();
				selectedBtn = 3;
				sBtn = selectionBtn4;
			} else if (e.getSource() == checkBtn) {

				if (sBtn != null) {
					sBtn.setUnselected();
					sBtn = null;
				}

				for (int guess : turnGuess) {

					if (guess == 0) {

						notValid = true;
						chatGui.appendToPane("System: ", 2);
						chatGui.appendToPane("Please select a color for each Peg and then press check\n", 0);
						break;
					}
				}

				if (!notValid && turn <= 10) {

					if (gameMode == 0) {
						for (int i = 0; i < turnGuess.length; i++) {

							turnHistory.addToRounds(turnGuess[i]);
							turnGuess[i] = 0;
						}

						for (Integer i : game.checkGuess()) {
							turnHistory.addToClues(i);
						}

						selectionBtn1.setUncolored();
						selectionBtn2.setUncolored();
						selectionBtn3.setUncolored();
						selectionBtn4.setUncolored();
						turnHistory.repaint();
						game.addCurrentRound();
						if (game.checkIfWin() || turn == 10) {
							chatGui.appendToPane("Mastermind:", 5);
							chatGui.appendToPane(
									" Not bad for a newbie... Your score is: " + game.getGameScore() + " !\n", 0);
							chatGui.appendToPane("System: ", 2);
							chatGui.appendToPane("Press 'BACK TO MENU' to return to the Main Menu.\n", 0);
							makeButtonsUnavailable();
							chatGui.appendToPane("Game will exit in", 9);
							new SimpleTimer(5).run();

						} else {
							
							turn += 1;
							numbersPanel.changeRound();
						}
					} else {
						if (client.isCodeMaker()) {
							try {
								client.sendGameCheck();
							} catch (IOException e1) {
								System.out.println("game check couldn t send");
								;
							}

							selectionBtn1.setUncolored();
							selectionBtn2.setUncolored();
							selectionBtn3.setUncolored();
							selectionBtn4.setUncolored();
							turnHistory.repaint();
							makeButtonsUnavailable();

						} else {
							for (int i = 0; i < turnGuess.length; i++) {

								turnHistory.addToRounds(turnGuess[i]);
								turnGuess[i] = 0;
							}
							selectionBtn1.setUncolored();
							selectionBtn2.setUncolored();
							selectionBtn3.setUncolored();
							selectionBtn4.setUncolored();
							turnHistory.repaint();
							try {
								client.sendGameCheck();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							turn += 1;
							numbersPanel.changeRound();
						}

					}
				}

				notValid = false;
			} else if (e.getSource() == redBtn) {

				if (sBtn != null) {

					sBtn.setColored(0);
					sBtn.setUnselected();
					turnGuess[selectedBtn] = 1;
					if (gameMode == 0) {
						game.getP1().addPin(selectedBtn, 1);
					} else if (gameMode == 1) {
						if (!client.isCodeMaker()) {
							try {
								client.sendGamePin(selectedBtn, 1);
								game.getP1().addPin(selectedBtn, 1);
							} catch (IOException e1) {
								System.out.println("Can t send game pin");
							}
						} else {
							client.createCode(selectedBtn, 1);
						}
					}
					sBtn = null;
				}
			} else if (e.getSource() == greenBtn) {

				if (sBtn != null) {

					sBtn.setColored(1);
					sBtn.setUnselected();
					turnGuess[selectedBtn] = 2;
					if (gameMode == 0) {
						game.getP1().addPin(selectedBtn, 2);
					} else if (gameMode == 1) {
						if (!client.isCodeMaker()) {
							try {
								client.sendGamePin(selectedBtn, 2);
								game.getP1().addPin(selectedBtn, 1);
							} catch (IOException e1) {
								System.out.println("Can t send game pin");
							}
						} else {
							client.createCode(selectedBtn, 2);
						}
					}
					sBtn = null;
				}
			} else if (e.getSource() == blueBtn) {

				if (sBtn != null) {

					sBtn.setColored(2);
					sBtn.setUnselected();
					turnGuess[selectedBtn] = 3;
					if (gameMode == 0) {
						game.getP1().addPin(selectedBtn, 3);
					} else if (gameMode == 1) {
						if (!client.isCodeMaker()) {
							try {
								client.sendGamePin(selectedBtn, 3);
								game.getP1().addPin(selectedBtn, 3);
							} catch (IOException e1) {
								System.out.println("Can t send game pin");
							}
						} else {
							client.createCode(selectedBtn, 3);
						}
					}
					sBtn = null;
				}
			} else if (e.getSource() == yellowBtn) {

				if (sBtn != null) {

					sBtn.setColored(3);
					sBtn.setUnselected();
					turnGuess[selectedBtn] = 4;
					if (gameMode == 0) {
						game.getP1().addPin(selectedBtn, 4);
					} else if (gameMode == 1) {
						if (!client.isCodeMaker()) {
							try {
								client.sendGamePin(selectedBtn, 4);
								game.getP1().addPin(selectedBtn, 4);
							} catch (IOException e1) {
								System.out.println("Can t send game pin");
							}
						} else {
							client.createCode(selectedBtn, 4);
						}
					}
					sBtn = null;
				}
			} else if (e.getSource() == whiteBtn) {

				if (sBtn != null) {

					sBtn.setColored(4);
					sBtn.setUnselected();
					turnGuess[selectedBtn] = 5;
					if (gameMode == 0) {
						game.getP1().addPin(selectedBtn, 5);
					} else if (gameMode == 1) {
						if (!client.isCodeMaker()) {
							try {
								client.sendGamePin(selectedBtn, 5);
								game.getP1().addPin(selectedBtn, 5);
							} catch (IOException e1) {
								System.out.println("Can t send game pin");
							}
						} else {
							client.createCode(selectedBtn, 5);
						}
					}
					sBtn = null;
				}
			} else if (e.getSource() == blackBtn) {

				if (sBtn != null) {

					sBtn.setColored(5);
					sBtn.setUnselected();
					turnGuess[selectedBtn] = 6;
					if (gameMode == 0) {
						game.getP1().addPin(selectedBtn, 6);
					} else if (gameMode == 1) {
						if (!client.isCodeMaker()) {
							try {
								client.sendGamePin(selectedBtn, 6);
								game.getP1().addPin(selectedBtn, 6);
							} catch (IOException e1) {
								System.out.println("Can t send game pin");
							}
						} else {
							client.createCode(selectedBtn, 6);
						}
					}
					sBtn = null;
				}
			} else if (e.getSource() == backButton) {

				if (gameMode == 0 && !previous.getClient().equals(null))
					previous.getClient().setInGame(false);
				if (gameMode == 1) {
					try {
						client.setInGame(false);
						client.playerLeftWhilePlaying();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (MainMenu.musicOn)
					gameMusic.closeClip();
				dispose();
				previous.setFrameVisible(GameGui.this); // Sets the previous as
														// frame visible
				if (sBtn != null) {

					sBtn.setUnselected();
					sBtn = null;
				}
			} else if (e.getSource() == music) {

				if (MainMenu.musicOn) {
					MainMenu.musicOn = false;
					gameMusic.closeClip();
					music.staySelected();
				} else {
					MainMenu.musicOn = true;
					gameMusic.playMenuClip();
					music.setUnselected();
				}
			} else if (e.getSource() == soundFx) {

				if (MainMenu.soundfxOn) {
					MainMenu.soundfxOn = false;
					soundFx.staySelected();
				} else {
					MainMenu.soundfxOn = true;
					soundFx.setUnselected();
				}
			} else if (e.getSource() == minimize) {
				setState(Frame.ICONIFIED);
			} else if (e.getSource() == exitButton) {

				int exit = JOptionPane.showConfirmDialog(GameGui.this, "Are you sure you want to exit?", "Exit",
						JOptionPane.YES_NO_OPTION);
				if (exit == 0)
					System.exit(0);
			}
		}
	}

	public class SimpleTimer implements Runnable {

		int count;

		public SimpleTimer(int count) {
			this.count = count;

		}

		public void run() {
			chatGui.appendToPane(" " + count, 9);
			TimeClass tc = new TimeClass(count);
			timer = new Timer(1000, tc);
			timer.start();

		}
	}

	public class TimeClass implements ActionListener {
		int counter;

		public TimeClass(int counter) {
			this.counter = counter;

		}

		public void actionPerformed(ActionEvent tc) {
			counter--;
			if (counter >= 1) {
				chatGui.appendToPane(" " + counter, 9);
			} else {
				chatGui.appendToPane("\n", 9);
				timer.stop();
				if (gameMode == 0) {
					if (!previous.getClient().equals(null))
						previous.getClient().setInGame(false);
					if (MainMenu.musicOn)
						gameMusic.closeClip();
					dispose();
					previous.setFrameVisible(GameGui.this);
				} else {
					GameGui.this.clearGame();
					game.clearGame();
					if (game.getCurrentGame() <= 4) {
						if (client.isCodeMaker()) {
							client.setCodeMaker(false);
						} else {
							client.setCodeMaker(true);
						}
					} else {
						if (MainMenu.musicOn)
							gameMusic.closeClip();
						dispose();
						previous.setFrameVisible(GameGui.this);
					}
				}
			}

		}
	}
}
