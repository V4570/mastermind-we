package we.software.mastermind;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.Timer;

import javafx.scene.paint.Color;
import we.software.gui.ChatGui;
import we.software.gui.GameGui;
import we.software.gui.MainMenu;
import we.software.gui.GameGui.SimpleTimer;
import we.software.gui.GameGui.TimeClass;

public class ClientListener extends Thread {
	// private static final java.awt.Color Color.AQUA = null;
	private Client client;
	private Socket socket;
	private MainMenu mainMenu;
	private GameGui gameGui;
	private ChatGui chatGui;
	private Game game;
	private String transmitter;
	private String reciever;
	private String inmessage;
	private String message = null;
	protected Timer timer;

	public ClientListener(Client client, Socket socket) {
		this.client = client;
		this.socket = socket;
		this.chatGui = chatGui;
	}

	public void setChatGui(ChatGui chatGui) {
		this.chatGui = chatGui;
	}

	public void setGameGui(GameGui gameGui) {
		this.gameGui = gameGui;
	}

	public void setMainMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public void run() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((inmessage = bufferedReader.readLine()) != null) {
				try {
					String[] info = inmessage.split("%", 2);
					transmitter = info[0].split(":")[1];
					reciever = info[0].split(":")[2];
					message = info[1];
					if (inmessage.startsWith("add")) {
						if (message.equals("ok")) {
							client.setUsername(reciever);
						} else if (message.equals("taken")) {
							mainMenu.getUsername();
						}
					} else if (inmessage.startsWith("request")) {
						requestHandler();
					} else if (inmessage.startsWith("playpin")) {
						playPinHandler();

					} else if (inmessage.startsWith("playcheck")) {
						playCheckHandler();

						// game ends

					} else if (inmessage.startsWith("playresult")) {
						playResultHandler();
					} else if (inmessage.startsWith("message")) {
						messageHandler();

					} else if (inmessage.startsWith("allmessage")) {
						chatGui.appendToPane("From " + transmitter + " to everyone: ", 7);
						chatGui.appendToPane(message + "\n", 0);

					} else if (inmessage.startsWith("fscore")) {
						game.getP2().setTotalScore(game.getP2().getTotalScore() + Integer.parseInt(message));
						// Shows the final score to user
					} else if (inmessage.startsWith("gethighscores")) {
						int i = 0;
						String[] names = new String[message.split(",").length];
						String[] scores = new String[message.split(",").length];
						for (String str : message.split(",")) {
							String[] val = str.split(" ");
							names[i] = val[0];
							scores[i] = val[1];
							i++;
						}
						String highScores = "High Scores:\n";
						highScores += "--------------------\n";
						for(int j=0;j<names.length;j++){
							highScores += j+1+". "+names[j]+" : "+scores[j]+"\n";
						}
						chatGui.appendToPane(highScores, 10);

					} else if (inmessage.startsWith("sethighscore")) {
						// ok
					} else if (inmessage.startsWith("login")) {
						if (message.equals("ok")) {
							//
						} else if (message.equals("not")) {
							//
						}
					} else if (inmessage.startsWith("getonlineplayers")) {
						chatGui.appendToPane("Online players: ", 1);
						chatGui.appendToPane(message + "\n", 0);
					}

				} catch (ArrayIndexOutOfBoundsException aioe) {
					System.out.println("Sit on it .!. aioe");
				}
			} // end of while
		} catch (IOException e) {
			System.out.println("Check me...");
		}

	}// end of method run

	public void playCheckHandler() throws IOException {
		if (client.isCodeMaker()) {
			for (int i = 0; i < gameGui.getGame().getP2().getGuess().size(); i++) {
				gameGui.turnHistory.addToRounds(gameGui.getGame().getP2().getGuess().get(i));
			}
			for (Integer i : gameGui.getGame().checkGuess()) {
				gameGui.turnHistory.addToClues(i);
			}
			client.sendGameRoundResult(gameGui.getGame().getResult());
			gameGui.selectionBtn1.setUncolored();
			gameGui.selectionBtn2.setUncolored();
			gameGui.selectionBtn3.setUncolored();
			gameGui.selectionBtn4.setUncolored();
			gameGui.turnHistory.repaint();
			gameGui.getGame().addCurrentRound();
			if (gameGui.getGame().checkIfWin() || gameGui.getGame().getCurrentRound() == 10) {
				gameGui.getGame().addCurrentGame();
				gameGui.getGame().getP2()
						.setTotalScore(gameGui.getGame().getP2().getTotalScore() + gameGui.getGame().getGameScore());
				if (gameGui.getGame().getCurrentGame() < 4) {
					chatGui.appendToPane("Mastermind: ", 2);
					chatGui.appendToPane(
							client.getEnemy().getName() + "'s score is: " + gameGui.getGame().getGameScore() + " !\n",
							0);

					gameGui.makeButtonsUnavailable();
					chatGui.appendToPane("Game will change in", 9);

					new SimpleTimer(5).run();
				} else {
					chatGui.appendToPane("Mastermind: ", 2);
					chatGui.appendToPane(client.getEnemy().getName() + "'s totalscore is: "
							+ gameGui.getGame().getP2().getTotalScore() + " and your score is "
							+ gameGui.getGame().getP1().getTotalScore() + "!\n", 0);
					if (gameGui.getGame().getP2().getTotalScore() < gameGui.getGame().getP1().getTotalScore()) {
						chatGui.appendToPane("Mastermind: ", 2);
						chatGui.appendToPane("Congratulations!!! You won!!!\n", 0);
					} else if (gameGui.getGame().getP2().getTotalScore() == gameGui.getGame().getP1().getTotalScore()) {
						chatGui.appendToPane("Mastermind: ", 2);
						chatGui.appendToPane("Nice try... Draw!\n", 0);
					} else {
						chatGui.appendToPane("Mastermind: ", 2);
						chatGui.appendToPane("You lost... Better luck next time.\n", 0);
					}
					gameGui.makeButtonsUnavailable();
					chatGui.appendToPane("Game will exit in", 9);
					new SimpleTimer(5).run();
				}
			} else {

				gameGui.addTurn();
				gameGui.numbersPanel.changeRound();
			}
		} else {
			gameGui.makeButtonsAvailable();
			chatGui.appendToPane(transmitter + ": ", 1);
			chatGui.appendToPane("My code is ready. You can start breaking it!\n", 0);

		}

	}

	public void requestHandler() throws IOException {

		if (message.equals("wannaplay")) {
			if (!client.isInGame()) {
				chatGui.appendToPane("Player " + transmitter
						+ " send a game invitation. To accept invitation just type invite:accept:name.\n", 1);
				client.addUserToPending(transmitter);

			} else {
				client.rejectGameRequest(transmitter);
			}
		} else if (message.equals("ingame")) {
			chatGui.appendToPane(transmitter + " is in game.\n", 1);
		} else if (message.equals("ok")) {
			gameGui = new GameGui(mainMenu, 1, chatGui);
			gameGui.setClient(client);
			mainMenu.setVisible(false);
			client.setEnemy(new Player());
			client.getEnemy().setName(transmitter);
			gameGui.getGame().setP2(client.getEnemy());
			gameGui.getGame().setP1(client);
			chatGui.appendToPane(transmitter + " accepted game invitation.\n", 1);
			client.setInGame(true);
			client.setCodeMaker(false);
			gameGui.makeButtonsUnavailable();
			gameGui.getGame().initializeArrays();

			chatGui.appendToPane(transmitter + ": ", 1);
			chatGui.appendToPane("I'm setting my code!\n", 0);
			chatGui.appendToPane("System: ", 2);
			chatGui.appendToPane("You are playing as Code Breaker!\n", 0);
		} else if (message.equals("not")) {
			chatGui.appendToPane(transmitter + " rejected game invitation.\n", 1);
		} else {
			chatGui.appendToPane("Server: ", 6);
			chatGui.appendToPane(message.split(" ")[0] + " is not online.\n", 0);
		}
	}

	private void playPinHandler() {
		int pos = Integer.parseInt(message.split(" ")[0]);
		int color = Integer.parseInt(message.split(" ")[1]);
		switch (pos) {
		case 0: {
			gameGui.getGame().getP2().addPin(0, color);
			gameGui.selectionBtn1.setColored(color - 1);
			gameGui.selectionBtn1.setUnselected();
			break;
		}
		case 1: {
			gameGui.getGame().getP2().addPin(1, color);
			gameGui.selectionBtn2.setColored(color - 1);
			gameGui.selectionBtn2.setUnselected();
			break;
		}
		case 2: {
			gameGui.getGame().getP2().addPin(2, color);
			gameGui.selectionBtn3.setColored(color - 1);
			gameGui.selectionBtn3.setUnselected();
			break;
		}
		case 3: {
			gameGui.getGame().getP2().addPin(3, color);
			gameGui.selectionBtn4.setColored(color - 1);
			gameGui.selectionBtn4.setUnselected();
			break;
		}
		}
	}

	private void messageHandler() {

		if (transmitter.equals("Server")) {
			chatGui.appendToPane("From Server : ", 6);
			chatGui.appendToPane(message + "\n", 0);
		} else if (transmitter.equals("liveServer")) {
			chatGui.appendToPane("From Server: ", 6);
			chatGui.appendToPane(message + "\n", 0);
		} else {
			chatGui.appendToPane("From " + transmitter + ": ", 3);
			chatGui.appendToPane(message + "\n", 0);
		}

	}

	private void playResultHandler() {
		int i = 0;
		for (String val : message.split(" ")) {
			gameGui.turnHistory.addToClues(Integer.parseInt(val));
			gameGui.getGame().addToResult(i, Integer.parseInt(val));
			i++;
		}
		gameGui.getGame().setRoundScore(gameGui.getGame().getResult());
		gameGui.turnHistory.repaint();
		gameGui.getGame().addCurrentRound();

		if (gameGui.getGame().checkIfWin() || gameGui.getGame().getCurrentRound() == 10) {
			gameGui.getGame().addCurrentGame();
			gameGui.getGame().getP1()
					.setTotalScore(gameGui.getGame().getP1().getTotalScore() + gameGui.getGame().getGameScore());
			chatGui.appendToPane("Mastermind: ", 2);
			chatGui.appendToPane("your score is " + gameGui.getGame().getGameScore() + "!\n", 0);
			if (gameGui.getGame().getCurrentGame() < 4) {
				gameGui.makeButtonsUnavailable();
				chatGui.appendToPane("Game will change in", 9);

				new SimpleTimer(5).run();
			} else {
				chatGui.appendToPane("Mastermind: ", 2);
				chatGui.appendToPane(
						client.getEnemy().getName() + "'s totalscore is: " + gameGui.getGame().getP2().getTotalScore()
								+ " and your score is " + gameGui.getGame().getP1().getTotalScore() + "!\n",
						0);
				if (gameGui.getGame().getP2().getTotalScore() < gameGui.getGame().getP1().getTotalScore()) {
					chatGui.appendToPane("Mastermind: ", 2);
					chatGui.appendToPane("Congratulations!!! You won!!!\n", 0);
				} else if (gameGui.getGame().getP2().getTotalScore() == gameGui.getGame().getP1().getTotalScore()) {
					chatGui.appendToPane("Mastermind: ", 2);
					chatGui.appendToPane("Nice try... Draw!\n", 0);
				} else {
					chatGui.appendToPane("Mastermind: ", 2);
					chatGui.appendToPane("You lost... Better luck next time.\n", 0);
				}
				gameGui.makeButtonsUnavailable();
				chatGui.appendToPane("Game will exit now in", 9);
				new SimpleTimer(5).run();
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
				chatGui.appendToPane("\n", 0);
				timer.stop();
				if (gameGui.getGame().getCurrentGame() < 4) {
					gameGui.clearGame();
					gameGui.getGame().clearGame();
					if (client.isCodeMaker()) {
						client.setCodeMaker(false);
						gameGui.makeButtonsUnavailable();
						chatGui.appendToPane("System: ", 2);
						chatGui.appendToPane("You are playing as Code Breaker!\n", 0);
					} else {
						client.setCodeMaker(true);
						gameGui.makeButtonsAvailable();
						chatGui.appendToPane("System: ", 2);
						chatGui.appendToPane("You are playing as CodeMaker. You can start making your code.\n", 0);

					}
				} else {
					gameGui.dispose();
					try {
						client.sendHighScore(gameGui.getGame().getP1().getTotalScore());
					} catch (IOException e) {
						e.printStackTrace();
					}
					mainMenu.setFrameVisible(gameGui);
					client.setInGame(false);

				}
			}
		}

	}

}
