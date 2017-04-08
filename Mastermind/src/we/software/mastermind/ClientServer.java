package we.software.mastermind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientServer extends Thread {
	Socket socket;
	final static String fileScorePATH = (System.getProperty("user.home") + "\\Appdata\\Roaming\\Mastermind\\Score.log");
	final static String dirPATH = (System.getProperty("user.home") + "\\Appdata\\Roaming\\Mastermind");
	final static String filePlayPATH = (System.getProperty("user.home") + "\\Appdata\\Roaming\\Mastermind\\game.log");
	Client client;

	public ClientServer(Socket socket, Client client) {
		this.socket = socket;
		this.client = client;
	}

	public void run() {

		String transmitter;
		String reciever;
		String inmessage;
		String message = null;

		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while ((inmessage = bufferedReader.readLine()) != null) {

				try {
					String[] info = inmessage.split("%", 2);
					transmitter = info[0].split(":")[1];
					reciever = info[0].split(":")[2];
					message = info[1];

					if (inmessage.startsWith("add")) {
						if (message.equals("ok")) {
							client.setAddMeValue("ok");
						} else {
							client.setAddMeValue("taken");
						}
					} else if (inmessage.startsWith("request")) {
						if (message.equals("wannaplay")) {
							if (!client.isInGame()) {
								// asks for user permission to start the game
								// if user accepts then:
								client.AcceptGameRequest();
								client.setInGame(true);
								// if he rejects the client.RejectGameRequest();
							} else {
								client.RejectGameRequest();
							}
						} else if (message.equals("ok")) {
							// accepted and game starts
						} else if (message.equals("anotherday")) {
							// rejected
						}
					} else if (inmessage.startsWith("play")) {
						// do the playThing and saves the progress
					} else if (inmessage.startsWith("message")) {
						// System.out.println("From:"+transmitter+":
						// "+message);
						// shows message to user

					} else if (inmessage.startsWith("score")) {
						// do the scoreThing and saves progress
					} else if (inmessage.startsWith("fscore")) {
						// Shows the final score to user
					}

				} catch (ArrayIndexOutOfBoundsException aioe) {
					System.out.println("Sit on it .!.");
				}
			} // end of while
		} catch (IOException e) {
			System.out.println("Check me...");
		}

	}// end of method run

}
