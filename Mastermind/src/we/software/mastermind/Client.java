package we.software.mastermind;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	String username;
	String enemy;
	ClientServer cServer;
	Socket socket;
	boolean addMeValue;
	boolean isInGame = false;
	String server = "";
	int PORT = 1;
	String response;
	BufferedWriter bw;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Client() {
		this.enemy = null;
		this.username = null;
		this.addMeValue = false;
		this.response = null;
		try {
			this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			System.out.println("Couldn't create bufferedWriter: " + e);
		}
	}

	public boolean isInGame() {
		return isInGame;
	}

	public void setInGame(boolean isInGame) {
		this.isInGame = isInGame;
	}

	public boolean getAddMeValue() {
		return addMeValue;
	}

	public void setAddMeValue(boolean addMeValue) {
		this.addMeValue = addMeValue;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEnemy() {
		return enemy;
	}

	public void setEnemy(String enemy) {
		this.enemy = enemy;
	}

	public void startListening() throws UnknownHostException, IOException {
		socket = new Socket(server, PORT);
		cServer = new ClientServer(this, socket);
		cServer.start();
	}

	// asks server's availability for current username
	public void addMe() throws IOException {
		bw.write("add:" + username + ": % ");
		bw.newLine();
		bw.flush();

	}

	// send a game request
	public void SendGameRequest() throws IOException {
		bw.write("request:" + username + ":" + enemy + "%wannaplay");
		bw.newLine();
		bw.flush();
	}

	// accepts a game request
	public void AcceptGameRequest() throws IOException {
		bw.write("request:" + username + ":" + enemy + "%ok");
		bw.newLine();
		bw.flush();
		// game starts
	}

	// rejects a game request
	public void RejectGameRequest() throws IOException {
		bw.write("request:" + username + ":" + enemy + "%not");
		bw.newLine();
		bw.flush();
	}

	// it will change soon
	public void SendGamePlay() throws IOException {
		bw.write("play:" + username + ":" + enemy + "%" + "");
		bw.newLine();
		bw.flush();
	}

	// it will change soon
	public void SendGameRoundScore() throws IOException {
		bw.write("score:" + username + ":" + enemy + "%" + "");
		bw.newLine();
		bw.flush();
	}

	// it will change soon
	public void SendFinalScore(String someone, String mes) throws IOException {
		bw.write("fscore:" + username + ":" + enemy + "%" + "");
		bw.newLine();
		bw.flush();
	}

	// sends this message when the game closes to inform server
	public void SendCloseMessage() throws IOException {
		bw.write("close:" + username + ":" + "server" + "%close");
		bw.newLine();
		bw.flush();
	}

	// sends a chat message
	public void SendMessage(String someone, String mes) throws IOException {
		bw.write("message:" + username + ":" + someone + "%" + mes);
		bw.newLine();
		bw.flush();
	}

}
