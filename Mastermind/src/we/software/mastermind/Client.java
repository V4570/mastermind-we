package we.software.mastermind;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import we.software.gui.GameGui;
import we.software.gui.MainMenu;

public class Client extends Player{
	private String username;
	private String enemy;
	ClientListener cServer;
	Socket socket;
	boolean inGame;
	String server = "-";
	int PORT = 12498;
	static boolean codeMaker;
	static int rounds;

	public Client() {
		super();
		this.enemy = "";
		this.username = "";
		this.inGame = false;
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public void setEnemy(String enemy) {
		this.enemy = enemy;
	}
	//Starts the client ServerThread 
	public void startListening(MainMenu mainMenu) throws UnknownHostException, IOException {
		socket = new Socket(server, PORT);
		cServer = new ClientListener(this, socket, mainMenu);
		cServer.start();
	}

	// add a new player with username and password
	public void addMe(String name, String password) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("add:" + name + ": %" + password);
		bw.newLine();
		bw.flush();
		bw.close();

	}
	
	// check credentials of user in order to log in
	public void logMeIn(String name, String password) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("add:" + name + ": %" + password);
		bw.newLine();
		bw.flush();
		bw.close();

	}

	// send a game request
	public void sendGameRequest(String someone) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("request:" + username + ":" + someone + "%wannaplay");
		bw.newLine();
		bw.flush();
		bw.close();
	}

	// accepts a game request
	public void acceptGameRequest(String someone) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("request:" + username + ":" + someone + "%ok");
		bw.newLine();
		bw.flush();
		bw.close();
		// game starts
	}

	// rejects a game request
	public void rejectGameRequest(String someone) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("request:" + username + ":" + someone + "%not");
		bw.newLine();
		bw.flush();
		bw.close();
	}

	// it will change soon
	public void sendGamePlay() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("play:" + username + ":" + enemy + "%" + "");
		bw.newLine();
		bw.flush();
		bw.close();
	}

	// it will change soon
	public void sendGameRoundScore() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("score:" + username + ":" + enemy + "%" + "");
		bw.newLine();
		bw.flush();
		bw.close();
	}

	// it will change soon
	public void sendFinalScore() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("fscore:" + username + ":" + enemy + "%" + "");
		bw.newLine();
		bw.flush();
		bw.close();
		
	}
	
	public void sendHighScore() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("hscore:" + username + ":server%" + "");
		bw.newLine();
		bw.flush();
		bw.close();
	}

	// sends this message when the game closes to inform server
	public void sendCloseMessage() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("close:" + username + ":" + "server" + "%close");
		bw.newLine();
		bw.flush();
		bw.close();
		
	}

	// sends a chat message
	public void sendMessage(String someone, String mes) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("message:" + username + ":" + someone + "%" + mes);
		bw.newLine();
		bw.flush();
		bw.close();
	}

}
