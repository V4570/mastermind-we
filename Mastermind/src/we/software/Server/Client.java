package we.software.Server;

import java.net.Socket;

public class Client {
	public String name;
	public Socket socket;
	public Thread thread;

	public Client(Socket socket, Thread thread) {
		this.socket = socket;
		this.thread = thread;
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