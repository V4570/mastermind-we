package we.software.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MainServer {
	static final int PORT = 12498;
	static final int gamePORT = 13498;
	public HashMap<String, Client> clients = new HashMap<String, Client>();
	ServerSocket server;
	public static void main(String[] args) throws IOException {
		new MainServer().runServer();
	}

	public void runServer() throws IOException {		
		server = new ServerSocket(PORT,100);
		Socket socket;
		System.out.println("Server is running....");
		while (true) {
			socket = server.accept();
			new ServerThread(socket, clients).start();
		}

	}
}
