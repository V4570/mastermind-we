package we.software.mastermind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import javafx.scene.paint.Color;
import we.software.gui.ChatGui;
import we.software.gui.GameGui;
import we.software.gui.MainMenu;
import we.software.gui.GameGui.SimpleTimer;

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
						
					} else if (inmessage.startsWith("playpin")) {
						

					} else if (inmessage.startsWith("playcheck")) {
						
						
						
							//game ends
						
					} else if (inmessage.startsWith("playresult")) {
						
					} else if (inmessage.startsWith("message")) {
						
						
					} else if (inmessage.startsWith("allmessage")) {
						chatGui.appendToPane("From " + transmitter + " to everyone: ", 7);
						chatGui.appendToPane(message+"\n", 0);
						
					} else if (inmessage.startsWith("fscore")) {
						game.getP2().setTotalScore(game.getP2().getTotalScore()+Integer.parseInt(message));
						// Shows the final score to user
					} else if (inmessage.startsWith("gethighscores")) {
						for(String str : message.split(",")){
							//do something
						}
					} else if (inmessage.startsWith("sethighscore")) {
						//ok
					} else if(inmessage.startsWith("login")){
						if(message.equals("ok")){
							//join
						} else if(message.equals("not")){
							//shows relogin
						}
					} else if(inmessage.startsWith("getonlineplayers")){
						chatGui.appendToPane("Online players: ", 1);
						chatGui.appendToPane(message+"\n", 0);
					}

				} catch (ArrayIndexOutOfBoundsException aioe) {
					System.out.println("Sit on it .!. aioe");
				}
			} // end of while
		} catch (IOException e) {
			System.out.println("Check me...");
		}

	}// end of method run
	
	public void playCheckHandler() throws IOException{
		if(client.isCodeMaker()){
			for (int i = 0; i < game.getP2().getCode().size(); i++) {
				gameGui.turnHistory.addToRounds(game.getP2().getCode().get(i));

			}
			for(Integer i : game.checkGuess()){
                gameGui.turnHistory.addToClues(i);
            }
			client.sendGameRoundResult(game.checkGuess());
			gameGui.selectionBtn1.setUncolored();
			gameGui.selectionBtn2.setUncolored();
			gameGui.selectionBtn3.setUncolored();
			gameGui.selectionBtn4.setUncolored();
			gameGui.turnHistory.repaint();
			 if(game.checkIfWin() || gameGui.getTurn()==10){
                	chatGui.appendToPane("Mastermind:", 2);
                	chatGui.appendToPane(client.getEnemy().getName()+"'s score is: "+game.getGameScore()+" !\n", 0);
                	
                }else{

                gameGui.addTurn();
                gameGui.numbersPanel.changeRound();
                }
		}else{
			//gameGui.makeButtonsAvailable();
			chatGui.appendToPane(transmitter+": ", 1);
        	chatGui.appendToPane("My code is ready. You can start breaking it!\n", 0);
        	gameGui.makeButtonsAvailable();
		}
		
	}
	
	public void requestHandler() throws IOException{

		if (message.equals("wannaplay")) {
			if (!client.isInGame()) {
				chatGui.appendToPane("Player "+transmitter+" send a game invitation. To accept invitation just type invite:accept:name.\n", 1);
				client.addUserToPending(transmitter);
				
			} else {
				client.rejectGameRequest(transmitter);
			}
		} else if(message.equals("ingame")){
			chatGui.appendToPane(transmitter+" is in game.\n", 1);
		}
		else if (message.equals("ok")) {
		
			client.setEnemy(new Player());
			client.getEnemy().setName(transmitter);
			game.setP2(client.getEnemy());
			chatGui.appendToPane(transmitter+" accepted game invitation.\n", 1);
			client.setInGame(true);
			client.setCodeMaker(false);
			gameGui.makeButtonsUnavailable();
			//starts gamegui se mode 1
			//setGameGui(gamegui	)
			//chatGui.appendToPane(transmitter+": ", 1);
        	//chatGui.appendToPane("I'm setting my code!\n", 0);
			// accepted and game starts
		} else if (message.equals("not")) {
			chatGui.appendToPane(transmitter+" rejected game invitation.\n", 1);
			// rejected
		} else{
			chatGui.appendToPane("Server: ", 6);
			chatGui.appendToPane(message.split(" ")[0]+" is not online.\n", 0);
		}
	}
	
	private void playPinHandler(){
		int pos = Integer.parseInt(message.split(" ")[0]);
		int color = Integer.parseInt(message.split(" ")[1]);
		switch (pos) {
		case 0: {
			game.getP2().addPin(0, color);
			gameGui.selectionBtn1.setColored(color-1);
			gameGui.selectionBtn1.setUnselected();
			break;
		}
		case 1: {
			game.getP2().addPin(1, color);
			gameGui.selectionBtn2.setColored(color-1);
			gameGui.selectionBtn2.setUnselected();
			break;
		}
		case 2: {
			game.getP2().addPin(2, color);
			gameGui.selectionBtn3.setColored(color-1);
			gameGui.selectionBtn3.setUnselected();
			break;
		}
		case 3: {
			game.getP2().addPin(3, color);
			gameGui.selectionBtn3.setColored(color-1);
			gameGui.selectionBtn3.setUnselected();
			break;
		}
		}
	}
	
	private void messageHandler(){

		if (transmitter.equals("Server")) {
			chatGui.appendToPane("From Server : ", 6);
			chatGui.appendToPane(message+"\n", 0);
		} else if (transmitter.equals("liveServer")) {
			chatGui.appendToPane("From Server: " , 6);
			chatGui.appendToPane(message+"\n", 0);
		} else {
			chatGui.appendToPane("From " + transmitter + ": ", 3);
			chatGui.appendToPane(message+"\n", 0);
		}
	
	}
	
	private void playResultHandler(){
		for(String val : message.split(" ")){
			gameGui.turnHistory.addToClues(Integer.parseInt(val));
		}
		gameGui.turnHistory.repaint();
	}

}
