package we.software.mastermind;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Client extends Player{
	Socket socket;
	int TPORT = 1248;
	String serverIp;
	String username = "O Klain";
	String enemy = "Mr. Mastermind";
	String dirPATH = (System.getProperty("user.home") + "\\Appdata\\Roaming\\Mastermind");
	String fileGamePATH = (System.getProperty("user.home") + "\\Appdata\\Roaming\\Mastermind\\game.log");
	PrintWriter bw = null;
	FileWriter fw = null;
	ArrayList<Integer> pins = new ArrayList<Integer>();

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void setTPORT(int tPORT) {
		TPORT = tPORT;
	}

	public Socket getSocket() {
		return socket;
	}

	public int getTPORT() {
		return TPORT;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		serverIp = serverIp;
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

	// Γράφει
	public void SaveGame(int turn) {
		try {
			File file = new File(dirPATH);
			if (!file.exists()){
				file.mkdir();
			}
			fw = new FileWriter(fileGamePATH,true);
			bw = new PrintWriter(fw);
			
			bw.print(Integer.toString(turn)+".");
			for(int pin:pins){
			bw.print(pin+" ");
			}
			bw.println();
			System.out.println("Done");
		} catch (IOException e) {
			System.out.println("Den anoigei oute gia plaka...Hint: Tsekare to path");
		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				System.out.println("Den kleinei me tipota...");

			}

		}
	}

	public void SubmitButton(int turn) throws IOException {
		SaveGame(turn);
		socket = new Socket(serverIp, TPORT);
		String sline = Files.readAllLines(Paths.get(fileGamePATH)).get(turn);
		sline = sline.split(".", 2)[1].toString().split(" ")[2];
		PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
		printWriter.println("play:" + username + ":" + enemy + "%" + sline);
		socket.close();

	}
	@Override
	public void addPin(NormalPeg pin){
		pins.add(0, pin.getColor());
		
	}

}
