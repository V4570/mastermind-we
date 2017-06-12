package we.software.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
	private static final int PORT = 12498;
	private HashMap<String, Client> clients = new HashMap<String, Client>();
	private ServerSocket server;
	private Database db;
	private LiveServerHandler lsh = new LiveServerHandler(clients);
	
	public static void main(String[] args) throws IOException {
		new Server().runServer();
	}
	
	//here is where server starts
	public void runServer() throws IOException {
		server = new ServerSocket(PORT,100);
		db = new Database();
		Socket socket;
		lsh.start();
		System.out.println("Server is running....");
		while (true) {
			socket = server.accept(); //every accepted connection has its own thread
			new ServerThread(socket,db, clients,lsh).start(); //starts new thread for the client
		}

	}
	
}
