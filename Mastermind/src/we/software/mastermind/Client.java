package we.software.mastermind;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
	String username;
	String enemy;
	Socket socket;
	String addMeValue = null;
	boolean isInGame = false;

	public boolean isInGame() {
		return isInGame;
	}

	public void setInGame(boolean isInGame) {
		this.isInGame = isInGame;
	}

	public String getAddMeValue() {
		return addMeValue;
	}

	public void setAddMeValue(String addMeValue) {
		this.addMeValue = addMeValue;
	}

	public Client(Socket socket) {
		this.socket = socket;
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

	public void addMe(String name) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("add:" + name + ": % ");
		bw.newLine();
		bw.flush();

	}

	public void SendGameRequest() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("request:" + username + ":" + enemy + "%wannaplay");
		bw.newLine();
		bw.flush();
	}

	public void AcceptGameRequest() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("request:" + username + ":" + enemy + "%ok");
		bw.newLine();
		bw.flush();
		// game starts
	}

	public void RejectGameRequest() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("request:" + username + ":" + enemy + "%anotherday");
		bw.newLine();
		bw.flush();
	}

	public void SendCloseMessage() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("close:" + username + ":" + "server" + "%close");
		bw.newLine();
		bw.flush();
	}

	public void SendMessage(String someone, String mes) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("message:" + username + ":" + someone + "%" + mes);
		bw.newLine();
		bw.flush();
	}

}
