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
	private LiveServerHandler lsh;
	private BufferedWriter bw;

	public ServerThread(Socket socket, Database db, HashMap<String, Client> clients, LiveServerHandler lsh) {
		super();
		this.socket = socket;
		this.clients = clients;
		this.username = null;
		this.message = null;
		this.db = db;
		this.lsh = lsh;

	}

	public void run() {

		transmitter = new Client(this.socket, currentThread());

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(transmitter.getSocket().getOutputStream()));
			transmitter.setBw(bw);
			while ((inmessage = br.readLine()) != null) {
				try {
					String[] info = inmessage.split("%", 2);
					username = info[0].split(":")[1];
					receiver = info[0].split(":")[2];
					message = info[1];
					if (inmessage.startsWith("add")) {
						if (!lsh.isHide())
							System.out.println(username + "--> send add user message to Server");
						addJob(message);

					} else if (inmessage.startsWith("close")) {
						closeJob();
						if (!lsh.isHide())
							System.out.println(transmitter.getName() + " disconnected");
						break;
					} else if (inmessage.startsWith("message")) {
						// Antispam restriction 1
						if (!message.equals("") || !message.equals(" ")) {
							if (!lsh.isHide())
								System.out.println(
										transmitter.getName() + "--> send message to " + receiver + ": " + message);
							messageJob();
						}
					} else if (inmessage.startsWith("allmessage")) {
						// Antispam restriction 1
						if (!message.equals("") || !message.equals(" ")) {
							if (!lsh.isHide())
								System.out.println(transmitter.getName() + "--> send message to everyone: " + message);
							messageAllJob();
						}
					} else if (inmessage.startsWith("playerleft")) {
						if (!lsh.isHide())
							System.out.println(transmitter.getName() + "--> left while he was in game");
						leftWhilePlayingJob();
					} else if (inmessage.startsWith("request")) {
						if (!lsh.isHide())
							System.out.println(
									transmitter.getName() + "--> send request message to " + receiver + ": " + message);
						requestJob();
					} else if (inmessage.startsWith("playcheck")) {
						if (!lsh.isHide())
							System.out.println(transmitter.getName() + "--> send playcheck message to " + receiver
									+ ": " + message);
						playCheckJob();
					} else if (inmessage.startsWith("playpin")) {
						if (!lsh.isHide())
							System.out.println(
									transmitter.getName() + "--> send playpin message to " + receiver + ": " + message);
						playPinJob();
					} else if (inmessage.startsWith("playresult")) {
						if (!lsh.isHide())
							System.out.println(transmitter.getName() + "--> send playresult message to " + receiver
									+ ": " + message);
						playResultJob();
					} else if (inmessage.startsWith("sethighscore")) {
						if (!lsh.isHide())
							System.out.println(
									transmitter.getName() + "--> send highscore message to Server: " + message);
						setHighScoreJob(message);
					} else if (inmessage.startsWith("login")) {
						if (!lsh.isHide())
							System.out.println(username + "--> send login message to Server ");
						logInJob(message);
					} else if (inmessage.startsWith("gethighscores")) {
						if (!lsh.isHide())
							System.out
									.println(transmitter.getName() + "--> asks for hisghscores to Server: " + message);
						getHighScoresJob();
					} else if (inmessage.startsWith("getonlineplayers")) {
						if (!lsh.isHide())
							System.out.println(
									transmitter.getName() + "--> asks for online players to Server: " + message);
						getOnlinePlayersJob();
					}

				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Check message format");
					Thread.sleep(100);
				} catch (SQLException e) {
					System.out.println("Problem in sql");
					System.out.println(e.getMessage());
				}
				if (!lsh.isHide()) { // every time that a player sends a message
										// shows in server's terminal which
										// users are connected
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
					System.out.println(clients.size());
				}
			} // end of while
		} catch (IOException | InterruptedException | NullPointerException e) {
			if (transmitter != null) {
				if (transmitter.getName() != null) {

					try {
						leftWhilePlayingJob();
						closeJob();
					} catch (IOException e1) {
						System.out.println("Socket is unclosable #thuglife");

					}
					if (!lsh.isHide()) {
						System.out.println(transmitter.getName() + " disconnected");

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
						System.out.println(clients.size());
					}
				} else {
					if (!lsh.isHide())
						System.out.println("A user has disconnected");
				}
			} else {
				if (!lsh.isHide())
					System.out.println("A user has disconnected");
			}

		}

	}// end of run method

	// handles requests of new users
	private void addJob(String password) throws UnknownHostException, IOException, InterruptedException, SQLException {
		if (!db.usernameExists(username)) {
			db.addUser(username, password);
			transmitter.setName(username);
			clients.put(transmitter.getName(), transmitter);
			bw.write("add:server: %ok");
			bw.newLine();
			bw.flush();
			if (!lsh.isHide())
				System.out.println(transmitter.getName() + "--> added succesfully");

		} else {
			if (!lsh.isHide())
				System.out.println(transmitter.getName() + "--> name is taken");
			bw.write("add:server: %taken");
			bw.newLine();
			bw.flush();
			socket.close();

		}

	}

	// handles login requests
	private void logInJob(String password) throws SQLException, IOException {
		if (db.usernameExists(username)) {
			if (db.passwordCheck(username, password)) {
				transmitter.setName(username);
				clients.put(transmitter.getName(), transmitter);
				db.updateDate(transmitter.getName());
				bw.write("login:server:" + username + "%ok");
				bw.newLine();
				bw.flush();
				if (!lsh.isHide())
					System.out.println(transmitter.getName() + "--> log in successfully");

			} else {
				if (!lsh.isHide())
					System.out.println(transmitter.getName() + "--> invalid password");
				bw.write("login:server:" + username + "%wrongpass");
				bw.newLine();
				bw.flush();
				socket.close();
			}
		} else {
			if (!lsh.isHide())
				System.out.println(transmitter.getName() + "--> username does not exist");
			bw.write("login:server:" + username + "%wrongusername");
			bw.newLine();
			bw.flush();
			socket.close();
		}

	}

	// handles situations where one user left a pvp game while it was in
	// progress
	public void leftWhilePlayingJob() throws IOException {
		if (transmitter.isInGame()) {
			if (!clients.isEmpty() && clients.containsKey(transmitter.getUserThatPlayWith())
					&& !transmitter.getUserThatPlayWith().equals(null)) {

				clients.get(transmitter.getUserThatPlayWith()).getBw().write(
						"playerleft:" + transmitter.getName() + ":" + transmitter.getUserThatPlayWith() + "% ok");
				clients.get(transmitter.getUserThatPlayWith()).getBw().newLine();
				clients.get(transmitter.getUserThatPlayWith()).getBw().flush();

				transmitter.setInGame(false);
				clients.get(transmitter.getUserThatPlayWith()).setInGame(false);
				clients.get(transmitter.getUserThatPlayWith()).setUserThatPlayWith(null);
				transmitter.setUserThatPlayWith(null);

			}
		}
	}

	// handles all private messages
	private void messageJob() throws UnknownHostException, IOException, InterruptedException {
		if (!clients.isEmpty() && clients.containsKey(receiver)) { // if client
																	// is online
																	// sends the
																	// message
																	// else
																	// inform
																	// transmitter
																	// that
																	// client is
																	// offline
			clients.get(receiver).getBw().write("message:" + transmitter.getName() + ":" + receiver + "%" + message);
			clients.get(receiver).getBw().newLine();
			clients.get(receiver).getBw().flush();

		} else {
			bw.write("message:" + "Server" + ":" + transmitter.getName() + "%User " + receiver + " is not online...");
			bw.newLine();
			bw.flush();
			if (!lsh.isHide())
				System.out.println(receiver + " not online");

		}
	}

	// handles all game requests
	private void requestJob() throws InterruptedException, IOException {
		if (clients.containsKey(receiver)) { // if player is online continue
												// else send a message to player
												// to inform him
			if (message.equals("ok")) { // if message is ok it means that a
										// player accepted a game request
				transmitter.setUserThatPlayWith(receiver);
				transmitter.setInGame(true);
				clients.get(receiver).setUserThatPlayWith(transmitter.getName());
				clients.get(receiver).setInGame(true);
			}
			clients.get(receiver).getBw().write("request:" + transmitter.getName() + ":" + receiver + "%" + message);
			clients.get(receiver).getBw().newLine();
			clients.get(receiver).getBw().flush();

		} else {
			bw.write("request:server:" + transmitter.getName() + "%" + receiver + " notconnected");
			bw.newLine();
			bw.flush();

		}
	}

	// sends a check message to the enemy so to let him know that the player
	// finish his round
	private void playCheckJob() throws IOException, InterruptedException {
		clients.get(receiver).getBw().write("playcheck:" + transmitter.getName() + ":" + receiver + "%" + message);
		clients.get(receiver).getBw().newLine();
		clients.get(receiver).getBw().flush();

	}

	// sends the result of a round to the enemy player
	private void playResultJob() throws IOException, InterruptedException {
		clients.get(receiver).getBw().write("playresult:" + transmitter.getName() + ":" + receiver + "%" + message);
		clients.get(receiver).getBw().newLine();
		clients.get(receiver).getBw().flush();

	}

	// sends the position and the color to enemy player
	private void playPinJob() throws IOException, InterruptedException {
		clients.get(receiver).getBw().write("playpin:" + transmitter.getName() + ":" + receiver + "%" + message);
		clients.get(receiver).getBw().newLine();
		clients.get(receiver).getBw().flush();

	}

	// Total score of each player in a pvp game comapred with the one that is
	// stored in the database if is greater replaces it
	private void setHighScoreJob(String highscore)
			throws InterruptedException, IOException, NumberFormatException, SQLException {
		db.updateHighScore(transmitter.getName(), Integer.parseInt(highscore));
		bw.write("sethighscore:server:" + transmitter.getName() + "%ok");
		bw.newLine();
		bw.flush();
		transmitter.setInGame(false);
		transmitter.setUserThatPlayWith(null);

	}

	// sends a message to user which contains the 4 players with the greatest
	// highscores from database
	private void getHighScoresJob() throws InterruptedException, IOException, NumberFormatException, SQLException {
		bw.write("gethighscores:server:" + transmitter.getName() + "%" + db.getHighScores());
		bw.newLine();
		bw.flush();

	}

	// sends a message to every user connected at this time
	private void messageAllJob() throws UnknownHostException, IOException, InterruptedException {
		if (!clients.isEmpty() && clients.size() > 1) {
			for (Entry<String, Client> entry : clients.entrySet()) {
				Client value = entry.getValue();
				if (!value.getName().equals(transmitter.getName())) {
					value.getBw().write("allmessage:" + transmitter.getName() + ":" + value.getName() + "%" + message);
					value.getBw().newLine();
					value.getBw().flush();
				}
			}
		} else { // if noone is connected sends a message to inform user
			bw.write(
					"message:" + "Server" + ":" + transmitter.getName() + "% there is nobody online at this moment...");
			bw.newLine();
			bw.flush();

		}
	}

	// sends to player a string with all the online players at current time
	private void getOnlinePlayersJob() throws IOException {
		String names = String.join(",", clients.keySet());
		bw.write("getonlineplayers:server:" + transmitter.getName() + "%" + names);
		bw.newLine();
		bw.flush();

	}

	// when a user disconnects for a reason this method remove him from the
	// hashmap which contains all the online clients
	private void closeJob() throws IOException {
		if (transmitter != null) {
			if (transmitter.getName() != null) {
				clients.remove(transmitter.getName());
			}
		} else {
			// do nothing
		}
		leftWhilePlayingJob();
		socket.close();

	}

}
