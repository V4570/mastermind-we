package we.software.Server;

import java.io.BufferedWriter;
import java.net.Socket;

public class Client {
	private String name;
	private Socket socket;
	private Thread thread;
	private BufferedWriter bw;
	private String userThatPlayWith=null;
	private boolean isInGame=false;

	public Client(Socket socket, Thread thread) {
		this.socket = socket;
		this.thread = thread;
	}
	
	public boolean isInGame() {
		return isInGame;
	}

	public void setInGame(boolean isInGame) {
		this.isInGame = isInGame;
	}
	

	public String getUserThatPlayWith() {
		return userThatPlayWith;
	}

	public void setUserThatPlayWith(String userThatPlayWith) {
		this.userThatPlayWith = userThatPlayWith;
	}
	
	public BufferedWriter getBw() {
		return bw;
	}

	public void setBw(BufferedWriter bw) {
		this.bw = bw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}
}