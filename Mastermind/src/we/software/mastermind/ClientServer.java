package we.software.mastermind;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientServer extends Thread {
	Socket socket;
	String fileScorePATH = (System.getProperty("user.home") + "\\Appdata\\Roaming\\Mastermind\\Score.log");
	String dirPATH = (System.getProperty("user.home") + "\\Appdata\\Roaming\\Mastermind");
	String message = null;
	String fileGamePATH = (System.getProperty("user.home") + "\\Appdata\\Roaming\\Mastermind\\game.log");
	boolean requestCounter = true;
	int TPORT = 1248;
	String serverIp = "127.0.0.1";
	String doJob;
	String transmitter = "User";
	String reciever;
	String inmessage;

	public boolean isRequestCounter() {
		return requestCounter;
	}

	public void setRequestCounter(boolean requestCounter) {
		this.requestCounter = requestCounter;
	}

	public ClientServer(Socket socket) {
		super();
		this.socket = socket;
	}

	public void run() {
		try {

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			inmessage = bufferedReader.readLine();

			serverIp = socket.getInetAddress().toString().replace("/", "");
			String[] info = inmessage.split("%", 2);
			doJob = info[0].split(":")[0];
			transmitter = info[0].split(":")[1];
			reciever = info[0].split(":")[2];
			message = info[1];

			if (!inmessage.equals(null)) {
				switch (doJob) {
				case "score": {
					SaveScore();
					break;
				}
				case "message": {
					// show message
					System.out.println(transmitter + ": " + message);

				}
				case "play": {
					SaveGame();

				}
				case "request": {
					if (message.equals("accepted")) {
						// Pop up window OK and game starts
					} else if (message.equals("inGame")) {
						// Pop up window PLAYER IN GAME
					} else {
						requestJob();
					}

				}
				}
			}
			socket.close();
		} catch (IOException e) {
			System.out.println("Connection lost...");
		}
	}

	void SaveScore() {
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			File file = new File(dirPATH);
			if (!file.exists()) {
				file.mkdir();
			}
			fw = new FileWriter(fileScorePATH, true);
			pw = new PrintWriter(fw);
			if (message != null) {
				pw.println(message);

				System.out.println("Done");
			}

		} catch (IOException e) {
			System.out.println("Den anoigei oute gia plaka...Hint: Tsekare to path");
		} finally {

			try {

				if (pw != null)
					pw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				System.out.println("Den kleinei me tipota...");

			}

		}
	}

	void SaveGame() {
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			File file = new File(dirPATH);
			if (!file.exists()) {
				file.mkdir();
			}
			fw = new FileWriter(fileGamePATH, true);
			pw = new PrintWriter(fw);
			if (message != null) {
				pw.println(message);

				System.out.println("Done");
			}

		} catch (IOException e) {
			System.out.println("Den anoigei oute gia plaka...Hint: Tsekare to path");
		} finally {

			try {

				if (pw != null)
					pw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				System.out.println("Den kleinei me tipota...");

			}

		}
	}

	public void requestJob() throws UnknownHostException, IOException {
		if (isRequestCounter()) {
			// Pop up window request
			socket = new Socket(serverIp, TPORT);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
			printWriter.println("request:" + reciever + ":" + transmitter + "%" + "accepted");
			socket.close();
		} else {
			socket = new Socket(serverIp, TPORT);
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
			printWriter.println("request:" + reciever + ":" + transmitter + "%" + "inGame");
			socket.close();
		}
	}

}
