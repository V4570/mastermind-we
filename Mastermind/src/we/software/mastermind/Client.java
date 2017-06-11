package we.software.mastermind;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import we.software.gui.ChatGui;
import we.software.gui.GameGui;
import we.software.gui.MainMenu;

public class Client extends Player {
	private Player enemy;
	private ClientListener cListener;
	private Socket socket;
	private boolean inGame;
	private String server = "83.212.99.117";
	private int PORT = 12498;
	private boolean codeMaker;
	private ArrayList<String> pending;
	
	ChatGui chatGui;

	public Client() throws UnknownHostException, IOException {
		super();
		enemy = null;
		username = null;
		inGame = false;
		pending =new ArrayList<String>();
		startListening();
	}
	public void clearPending(){
		pending.clear();
	}
	
	public ArrayList<String> getPending() {
		return pending;
	}

	public void addUserToPending(String name) {
		pending.add(name);
	}

	public boolean isCodeMaker() {
		return codeMaker;
	}

	public void setCodeMaker(boolean codeMaker) {
		this.codeMaker = codeMaker;
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

	public void setEnemy(Player enemy) {
		this.enemy = enemy;
	}

	public Player getEnemy() {
		return enemy;
	}

	public ClientListener getcListener() {
		return cListener;
	}

	// Starts the client ServerThread
	public void startListening() throws UnknownHostException, IOException {
		socket = new Socket(server, PORT);
		cListener = new ClientListener(this, socket);
		cListener.start();
	}

	// add a new player with username and password
	public void addMe(String name, String password) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("add:" + name + ": %" + password);
		bw.newLine();
		bw.flush();

	}

	// check credentials of user in order to log in
	public void logMeIn(String name, String password) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("login:" + name + ": %" + password);
		bw.newLine();
		bw.flush();

	}

	// send a game request
	public void sendGameRequest(String someone) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("request:" + username + ":" + someone + "%wannaplay");
		bw.newLine();
		bw.flush();
	}

	// accepts a game request
	public void acceptGameRequest(String someone) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("request:" + username + ":" + someone + "%ok");
		bw.newLine();
		bw.flush();
		// game starts
	}

	// rejects a game request
	public void rejectGameRequest(String someone) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("request:" + username + ":" + someone + "%not");
		bw.newLine();
		bw.flush();
	}
	
	public void rejectInGameRequest(String someone) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("request:" + username + ":" + someone + "%ingame");
		bw.newLine();
		bw.flush();
	}

	// it will change soon
	public void sendGamePin(int position, int color) throws IOException {
		addPin(position, color);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("playpin:" + username + ":" + enemy.getName() + "%" + position + " " + color);
		bw.newLine();
		bw.flush();
	}

	public void sendGameCheck() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("playcheck:" + username + ":" + enemy.getName() + "%ok");
		bw.newLine();
		bw.flush();
	}

	// it will change soon
	public void sendGameRoundResult(ArrayList<Integer> result) throws IOException {
		String[] res= new String[result.size()];
		for(int i=0;i<result.size();i++){
			res[i] = Integer.toString(result.get(i));
		}
		
		String m = String.join(" ", res);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("playresult:" + username + ":" + enemy.getName() + "%" + m);
		bw.newLine();
		bw.flush();
	}

	// it will change soon
	public void sendFinalScore() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("fscore:" + username + ":" + enemy.getName() + "%" + "");
		bw.newLine();
		bw.flush();

	}

	public void sendHighScore(int highscore) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("sethighscore:" + username + ":server%" + highscore);
		bw.newLine();
		bw.flush();
	}

	public void getHighScore() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("gethighscores:" + username + ":server% ");
		bw.newLine();
		bw.flush();
	}

	public void getOnlinePlayers() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("getonlineplayers:" + username + ":server% ");
		bw.newLine();
		bw.flush();
	}

	// sends this message when the game closes to inform server
	public void sendCloseMessage() throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("close:" + username + ":" + "server" + "%close");
		bw.newLine();
		bw.flush();

	}

	// sends a chat message
	public void sendMessage(String someone, String mes) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("message:" + username + ":" + someone + "%" + mes);
		bw.newLine();
		bw.flush();
	}


	public void sendAllMessage(String mes) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write("allmessage:" + username + ": %" + mes);
		bw.newLine();
		bw.flush();


	}
}

