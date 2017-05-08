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

public class ServerThread extends Thread {
	public Socket socket;
	public HashMap<String, Client> clients;
	private String username;
	private Client transmitter;
	private Database db;
	private String inmessage;
	private String reciever;
	private String message;

	public ServerThread(Socket socket, Database db, HashMap<String, Client> clients) {
		super();
		this.socket = socket;
		this.clients = clients;
		this.username = null;
		this.message = null;
		this.db = db;

	}

	public void run() {

		transmitter = new Client(this.socket, currentThread());
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while ((inmessage = br.readLine()) != null) {
				try {
					String[] info = inmessage.split("%", 2);
					username = info[0].split(":")[1];
					reciever = info[0].split(":")[2];
					message = info[1];
					if (inmessage.startsWith("add")) {
						addJob(message);

					} else if (inmessage.startsWith("close")) {
						closeJob();
						System.out.println(transmitter.getName() + " disconnected");
						break;
					} else if (inmessage.startsWith("message")) {
						// Antispam restriction 1
						if (!message.equals("") || !message.equals(" ")) {
							messageJob();
						}
					} else if (inmessage.startsWith("request")) {
						requestJob();
					} else if (inmessage.startsWith("play")) {
						playJob();
					} else if (inmessage.startsWith("score")) {
						scoreJob();
					} else if (inmessage.startsWith("sethighscore")) {
						setHighScoreJob(message);
					} else if (inmessage.startsWith("login")) {
						logInJob(message);
					} else if (inmessage.startsWith("gethighscores")) {
						setHighScoreJob(message);
					}

				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Mathe na grafeis");
					// System.out.println(e.);
					Thread.sleep(100);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(inmessage);
				System.out.println(clients);

			} // end of while
		} catch (IOException | InterruptedException | NullPointerException e) {
			if (transmitter != null) {
				if (transmitter.getName() != null) {
					System.out.println(transmitter.getName() + " disconnected");
				} else {
					System.out.println("A user has disconnected");
				}
			} else {
				System.out.println("A user has disconnected");
			}
			try {
				closeJob();
			} catch (IOException e1) {
				System.out.println("Socket is unclosable ;)");

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
			bw.close();
			System.out.println(clients.size());
			System.out.println("Done");

		} else {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(transmitter.getSocket().getOutputStream()));
			bw.write("add:server: %taken");
			bw.newLine();
			bw.flush();
			bw.close();
			System.out.println("Taken");
		}

	}

	private void logInJob(String password) throws SQLException, IOException {
		if (db.passwordCheck(username, password)) {
			transmitter.setName(username);
			clients.put(transmitter.getName(), transmitter);
			db.updateDate(password);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(transmitter.getSocket().getOutputStream()));
			bw.write("login:server:" + username + "%ok");
			bw.newLine();
			bw.flush();
			bw.close();

		} else {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(transmitter.getSocket().getOutputStream()));
			bw.write("login:server:" + username + "%not");
			bw.newLine();
			bw.flush();
			bw.close();
		}

	}

	// O transmitter stelnei minima ston reciever an to onoma tou reciever einai
	// kataxwrimeno sto HAshMap 'clients' tou server to minima prowtheite
	// kanonika se diaforetiki periptwsi o transmitter pairnei minima apo ton
	// server 'reciever notconnected'
	private void messageJob() throws UnknownHostException, IOException, InterruptedException {
		if (!clients.isEmpty() && clients.containsKey(reciever)) {
			clients.get(reciever).getThread().join(100);
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(clients.get(reciever).getSocket().getOutputStream()));
			bw.write("message:" + transmitter.getName() + ":" + reciever + "%" + message);
			bw.newLine();
			bw.flush();
			bw.close();
			System.out.println("Message sent!");
		} else {
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(clients.get(transmitter.getName()).getSocket().getOutputStream()));
			bw.write("message:" + "server" + ":" + transmitter.getName() + "%" + reciever + " notconnected");
			bw.newLine();
			bw.flush();
			bw.close();
			System.out.println(reciever + " disconnected");

		}
	}

	/*
	 * O transmitter stelnei aitima ston reciever gia na paiksoun. An o reciever
	 * einai online kai dexthei tote stelnei minima 'ok' an einai offline
	 * stelnete minima 'notconnected', an den thelei stelnete minima
	 * 'anotherday'
	 */
	private void requestJob() throws InterruptedException, IOException {
		if (clients.containsKey(reciever)) {
			clients.get(reciever).getThread().join(100);
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(clients.get(reciever).getSocket().getOutputStream()));
			bw.write("request:" + transmitter.getName() + ":" + reciever + "%" + message);
			bw.newLine();
			bw.flush();
			bw.close();

		} else {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(transmitter.getSocket().getOutputStream()));
			bw.write("request:server:" + transmitter.getName() + "%" + reciever + " notconnected");
			bw.newLine();
			bw.flush();
			bw.close();

		}
	}

	// H prospatheia(mantepsia) tou transmitter stelnete ston reciever gia na
	// elexthei
	private void playJob() throws IOException, InterruptedException {
		clients.get(reciever).getThread().join(100);
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(clients.get(reciever).getSocket().getOutputStream()));
		bw.write("play:" + transmitter.getName() + ":" + reciever + "%" + message);
		bw.newLine();
		bw.flush();
		bw.close();
	}

	/*
	 * To score tis prospatheias tou reciever afou elexthei apo ton transmitter
	 * stelnete pisw ston reciever
	 */
	private void scoreJob() throws InterruptedException, IOException {
		clients.get(reciever).getThread().join(100);
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(clients.get(reciever).getSocket().getOutputStream()));
		bw.write("score:" + transmitter.getName() + ":" + reciever + "%" + message);
		bw.newLine();
		bw.flush();
		bw.close();
	}

	// To teliko score kathe partidas apostelete apo ton transmitter ston
	// reciever
	private void setHighScoreJob(String highscore)
			throws InterruptedException, IOException, NumberFormatException, SQLException {
		db.updateHighScore(transmitter.getName(), Integer.parseInt(highscore));
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(clients.get(transmitter.getName()).getSocket().getOutputStream()));
		bw.write("sethighscore:server:" + transmitter.getName() + "%ok");
		bw.newLine();
		bw.flush();
		bw.close();

	}

	private void getHighScoreJob() throws InterruptedException, IOException, NumberFormatException, SQLException {
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(clients.get(transmitter.getName()).getSocket().getOutputStream()));
		bw.write("gethighscores:server:" + transmitter.getName() + "%" + db.getHighScores());
		bw.newLine();
		bw.flush();
		bw.close();

	}

	private void closeJob() throws IOException, SQLException {
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
