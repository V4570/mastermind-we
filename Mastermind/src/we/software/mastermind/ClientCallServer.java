package we.software.mastermind;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientCallServer extends Thread {
	public static final int PORT = 54162;
	ServerSocket serverSocket;
	public void run(){
		try{
		serverSocket = new ServerSocket(PORT);
		System.out.println("Server is up and running...");
		//serverSocket.
		while(true){
		Socket socket = serverSocket.accept();
		new ClientServer(socket).start();
		}
		}catch(IOException e){
			System.out.println("Paraligo tha douleue");
			
		}
	}

}
