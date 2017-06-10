package we.software.Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

public class ServerThread extends Thread {
	private Socket socket;
	private HashMap<String, Client> clients;
	private String username;
	private Client transmitter;
	private Database db;
	private String inmessage;
	private String receiver;
	private String message;
	public LiveServerHandler lsh;

	public ServerThread(Socket socket, Database db, HashMap<String, Client> clients,LiveServerHandler lsh) {
		super();
		this.socket = socket;
		this.clients = clients;
		this.username = null;
		this.message = null;
		this.db = db;
		this.lsh =lsh;

	}

	public void run() {

		transmitter = new Client(this.socket, currentThread());
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while ((inmessage = br.readLine()) != null) {
				try {
					String[] info = inmessage.split("%", 2);
					username = info[0].split(":")[1];
					receiver = info[0].split(":")[2];
					message = info[1];
					if (inmessage.startsWith("add")) {
						if(!lsh.isHide()) System.out.println(username + "--> send add user message to Server");
						addJob(message);

					} else if (inmessage.startsWith("close")) {
						closeJob();
						if(!lsh.isHide())System.out.println(transmitter.getName() + " disconnected");
						break;
					} else if (inmessage.startsWith("message")) {
						// Antispam restriction 1
						if (!message.equals("") || !message.equals(" ")) {
							if(!lsh.isHide())System.out.println(
									transmitter.getName() + "--> send message to " + receiver + ": " + message);
							messageJob();
						}
					} else if (inmessage.startsWith("allmessage")) {
						// Antispam restriction 1
						if (!message.equals("") || !message.equals(" ")) {
							if(!lsh.isHide())System.out.println(transmitter.getName() + "--> send message to everyone: " + message);
							messageAllJob();
						}
					} else if (inmessage.startsWith("request")) {
						if(!lsh.isHide())System.out.println(
								transmitter.getName() + "--> send request message to " + receiver + ": " + message);
						requestJob();
					} else if (inmessage.startsWith("play")) {
						if(!lsh.isHide())System.out.println(
								transmitter.getName() + "--> send play message to " + receiver + ": " + message);
						playJob();
					} else if (inmessage.startsWith("score")) {
						if(!lsh.isHide())System.out.println(
								transmitter.getName() + "--> send score message to " + receiver + ": " + message);
						scoreJob();
					} else if (inmessage.startsWith("sethighscore")) {
						if(!lsh.isHide())System.out.println(transmitter.getName() + "--> send highscore message to Server: " + message);
						setHighScoreJob(message);
					} else if (inmessage.startsWith("login")) {
						if(!lsh.isHide())System.out.println(username + "--> send login message to Server ");
						logInJob(message);
					} else if (inmessage.startsWith("gethighscores")) {
						if(!lsh.isHide())System.out.println(transmitter.getName() + "--> asks for hisghscores to Server: " + message);
						getHighScoresJob();
					} else if(inmessage.startsWith("getonlineplayers")){
						if(!lsh.isHide())System.out.println(transmitter.getName() + "--> asks for online players to Server: " + message);
						getOnlinePlayersJob();
					}

				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Check message format");
					// System.out.println(e.);
					Thread.sleep(100);
				} catch (SQLException e) {
					System.out.println("Sql problem");
					System.out.println(e.getMessage());
				}
				if(!lsh.isHide()){System.out.print("[");
				int count = 0;
				for (Entry<String, Client> entry : clients.entrySet()) {
					count++;
					String key = entry.getKey();
					System.out.print(key);
					if (!(count == clients.size())) {
						System.out.print(", ");
					}

				}
				System.out.print("]");
				System.out.println();
				System.out.println(clients.size());}
			} // end of while
		} catch (IOException | InterruptedException | NullPointerException e) {
			if (transmitter != null) {
				if (transmitter.getName() != null) {

					try {
						closeJob();
					} catch (IOException e1) {
						System.out.println("Socket is unclosable #thuglife");

					}
					if(!lsh.isHide()){System.out.println(transmitter.getName() + " disconnected");

					if (!clients.isEmpty()) {
						System.out.print("[");
						int count = 0;
						for (Entry<String, Client> entry : clients.entrySet()) {
							count++;
							String key = entry.getKey();
							System.out.print(key);
							if (!(count == clients.size())) {
								System.out.print(", ");
							}

						}
						System.out.print("]");
						System.out.println();

					}
					System.out.println(clients.size());}
				} else {
					if(!lsh.isHide())System.out.println("A user has disconnected");
				}
			} else {
				if(!lsh.isHide())System.out.println("A user has disconnected");
			}

		}

	}// end of run method

	// O transmitter stelnei aitima gia na kataxwrithei sto mitrwo tou server
	// kai na mporei na paiksei me allous. Ean to onoma einai diathesimo tote
	// pairnei pisw to minima 'ok' alliws pairnei to minima 'taken'
	private void addJob(String password) throws UnknownHostException, IOException, InterruptedException, SQLException {
		if (!db.usernameExists(username)) {
			db.addUser(username, password);
			transmitter.setName(username);
			clients.put(transmitter.getName(), transmitter);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(transmitter.getSocket().getOutputStream()));
			bw.write("add:server: %ok");
			bw.newLine();
			bw.flush();

		} else {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(transmitter.getSocket().getOutputStream()));
			bw.write("add:server: %taken");
			bw.newLine();
			bw.flush();
		}

	}

	private void logInJob(String password) throws SQLException, IOException {
		if (db.passwordCheck(username, password)) {
			transmitter.setName(username);
			clients.put(transmitter.getName(), transmitter);
			db.updateDate(transmitter.getName());
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(transmitter.getSocket().getOutputStream()));
			bw.write("login:server:" + username + "%ok");
			bw.newLine();
			bw.flush();

		} else {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(transmitter.getSocket().getOutputStream()));
			bw.write("login:server:" + username + "%not");
			bw.newLine();
			bw.flush();

		}

	}

	// O transmitter stelnei minima ston receiver an to onoma tou receiver einai
	// kataxwrimeno sto HAshMap 'clients' tou server to minima prowtheite
	// kanonika se diaforetiki periptwsi o transmitter pairnei minima apo ton
	// server 'receiver notconnected'
	private void messageJob() throws UnknownHostException, IOException, InterruptedException {
		if (!clients.isEmpty() && clients.containsKey(receiver)) {
			clients.get(receiver).getThread().join(1);
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(clients.get(receiver).getSocket().getOutputStream()));
			bw.write("message:" + transmitter.getName() + ":" + receiver + "%" + message);
			bw.newLine();
			bw.flush();

		} else {
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(clients.get(transmitter.getName()).getSocket().getOutputStream()));
			bw.write("message:" + "Server" + ":" + transmitter.getName() + "%User " + receiver + " is not online...");
			bw.newLine();
			bw.flush();
			if(!lsh.isHide())System.out.println(receiver + " not online");

		}
	}

	/*
	 * O transmitter stelnei aitima ston receiver gia na paiksoun. An o receiver
	 * einai online kai dexthei tote stelnei minima 'ok' an einai offline
	 * stelnete minima 'notconnected', an den thelei stelnete minima
	 * 'anotherday'
	 */
	private void requestJob() throws InterruptedException, IOException {
		if (clients.containsKey(receiver)) {
			clients.get(receiver).getThread().join(1);
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(clients.get(receiver).getSocket().getOutputStream()));
			bw.write("request:" + transmitter.getName() + ":" + receiver + "%" + message);
			bw.newLine();
			bw.flush();

		} else {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(transmitter.getSocket().getOutputStream()));
			bw.write("request:server:" + transmitter.getName() + "%" + receiver + " notconnected");
			bw.newLine();
			bw.flush();

		}
	}

	// H prospatheia(mantepsia) tou transmitter stelnete ston receiver gia na
	// elexthei
	private void playJob() throws IOException, InterruptedException {
		clients.get(receiver).getThread().join(1);
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(clients.get(receiver).getSocket().getOutputStream()));
		bw.write("play:" + transmitter.getName() + ":" + receiver + "%" + message);
		bw.newLine();
		bw.flush();

	}

	/*
	 * To score tis prospatheias tou reciever afou elexthei apo ton transmitter
	 * stelnete pisw ston receiver
	 */
	private void scoreJob() throws InterruptedException, IOException {
		clients.get(receiver).getThread().join(1);
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(clients.get(receiver).getSocket().getOutputStream()));
		bw.write("score:" + transmitter.getName() + ":" + receiver + "%" + message);
		bw.newLine();
		bw.flush();

	}

	// To teliko score kathe partidas apostelete apo ton transmitter ston
	// receiver
	private void setHighScoreJob(String highscore)
			throws InterruptedException, IOException, NumberFormatException, SQLException {
		db.updateHighScore(transmitter.getName(), Integer.parseInt(highscore));
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(clients.get(transmitter.getName()).getSocket().getOutputStream()));
		bw.write("sethighscore:server:" + transmitter.getName() + "%ok");
		bw.newLine();
		bw.flush();

	}

	private void getHighScoresJob() throws InterruptedException, IOException, NumberFormatException, SQLException {
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(clients.get(transmitter.getName()).getSocket().getOutputStream()));
		bw.write("gethighscores:server:" + transmitter.getName() + "%" + db.getHighScores());
		bw.newLine();
		bw.flush();

	}

	private void messageAllJob() throws UnknownHostException, IOException, InterruptedException {
		if (!clients.isEmpty() && clients.size() > 1) {
			for (Entry<String, Client> entry : clients.entrySet()) {
				Client value = entry.getValue();
				if (!value.getName().equals(transmitter.getName())) {
					value.getThread().join(1);

					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(value.getSocket().getOutputStream()));
					bw.write("messageall:" + transmitter.getName() + ":" + value.getName() + "%" + message);
					bw.newLine();
					bw.flush();
				}
			}

			System.out.println("Message sent to everyone!");
		} else {
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(clients.get(transmitter.getName()).getSocket().getOutputStream()));
			bw.write(
					"message:" + "Server" + ":" + transmitter.getName() + "% there is nobody online at this moment...");
			bw.newLine();
			bw.flush();

		}
	}
	
	private void getOnlinePlayersJob() throws IOException{
		String names = String.join(",", clients.keySet());
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(clients.get(transmitter.getName()).getSocket().getOutputStream()));
		bw.write("getonlineplayers:server:" + transmitter.getName() + "%" + names);
		bw.newLine();
		bw.flush();
		
	}

	private void closeJob() throws IOException {
		if (transmitter != null) {
			if (transmitter.getName() != null) {
				clients.remove(transmitter.getName());
			}
		} else {
			// do nothing
		}
		socket.close();
	}

}
