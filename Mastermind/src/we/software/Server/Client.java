package we.software.Server;

import java.io.BufferedWriter;
import java.net.Socket;

public class Client {
	private String name;
	private Socket socket;
	private Thread thread;
	private BufferedWriter bw;

	public Client(Socket socket, Thread thread) {
		this.socket = socket;
		this.thread = thread;
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