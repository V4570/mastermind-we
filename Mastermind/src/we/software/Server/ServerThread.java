package we.software.Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class ServerThread extends Thread {
	public Socket socket;
	public HashMap<String, Client> clients;
	String host;
	Client transmitter;

	public ServerThread(Socket socket, HashMap<String, Client> clients) {
		super();
		this.socket = socket;
		this.clients = clients;
		this.host = null;
	}

	public void run() {

		String inmessage;
		String reciever;
		String message;
		transmitter = new Client(this.socket, currentThread());
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while ((inmessage = br.readLine()) != null) {
				if (inmessage.isEmpty()) {
					System.out.println(host + " disconnected");
					System.out.println("Waiting " + host + " to recconect...");
					Thread.sleep(1000);
				} else {
					try {
						String[] info = inmessage.split("%", 2);
						host = info[0].split(":")[1];
						reciever = info[0].split(":")[2];
						message = info[1];
						if (inmessage.startsWith("add")) {
							addJob(transmitter,host);

						} else if (inmessage.startsWith("close")) {
							closeJob(transmitter);
							System.out.println(transmitter.getName() + " disconnected");
							break;
						} else if (inmessage.startsWith("message")) {
							// Antispam restriction 1
							if (!message.equals("") || !message.equals(" ")) {
								messageJob(transmitter, reciever, message);
							}
						} else if (inmessage.startsWith("request")) {
							requestJob(transmitter, reciever, message);
						} else if (inmessage.startsWith("play")) {
							playJob(transmitter, reciever, message);
						} else if (inmessage.startsWith("score")) {
							scoreJob(transmitter, reciever, message);
						} else if (inmessage.startsWith("fscore")) {
							fscoreJob(transmitter, reciever, message);
						}

					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("Mathe na grafeis");
						// System.out.println(e.);
						Thread.sleep(100);
					}
					System.out.println(inmessage);
					System.out.println(clients);
				} // end of else
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
				closeJob(transmitter);
			} catch (IOException e1) {
				System.out.println("Socket is unclosable ;)");
				;
			}

		}

	}// end of run method

	// O transmitter stelnei aitima gia na kataxwrithei sto mitrwo tou server
	// kai na mporei na paiksei me allous. Ean to onoma einai diathesimo tote
	// pairnei pisw to minima 'ok' alliws pairnei to minima 'taken'
	void addJob(Client transmitter, String host) throws UnknownHostException, IOException, InterruptedException {
		if (!clients.containsKey(transmitter.getName())) {
			transmitter.setName(host);
			clients.put(transmitter.getName(), transmitter);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(transmitter.getSocket().getOutputStream()));
			bw.write("add:server: %ok");
			bw.newLine();
			bw.flush();
			System.out.println(clients.size());
			System.out.println("Done");

		} else {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(transmitter.getSocket().getOutputStream()));
			bw.write("add:server: %taken");
			bw.newLine();
			bw.flush();
			System.out.println("Taken");
		}
	}

	// O transmitter stelnei minima ston reciever an to onoma tou reciever einai
	// kataxwrimeno sto HAshMap 'clients' tou server to minima prowtheite
	// kanonika se diaforetiki periptwsi o transmitter pairnei minima apo ton
	// server 'reciever notconnected'
	void messageJob(Client transmitter, String reciever, String message)
			throws UnknownHostException, IOException, InterruptedException {
		if (!clients.isEmpty() && clients.containsKey(reciever)) {
			clients.get(reciever).getThread().join(100);
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(clients.get(reciever).getSocket().getOutputStream()));
			bw.write("message:" + transmitter.getName() + ":" + reciever + "%" + message);
			bw.newLine();
			bw.flush();
			System.out.println("Message sent!");
		} else {
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(clients.get(transmitter.getName()).getSocket().getOutputStream()));
			bw.write("message:" + "server" + ":" + transmitter.getName() + "%" + reciever + " notconnected");
			bw.newLine();
			bw.flush();
			System.out.println(reciever + " disconnected");

		}
	}

	/*
	 * O transmitter stelnei aitima ston reciever gia na paiksoun. An o reciever
	 * einai online kai dexthei tote stelnei minima 'ok' an einai offline
	 * stelnete minima 'notconnected', an den thelei stelnete minima
	 * 'anotherday'
	 */
	void requestJob(Client transmitter, String reciever, String message) throws InterruptedException, IOException {
		if (clients.containsKey(reciever)) {
			clients.get(reciever).getThread().join(100);
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(clients.get(reciever).getSocket().getOutputStream()));
			bw.write("request:" + transmitter.getName() + ":" + reciever + "%" + message);
			bw.newLine();
			bw.flush();

		} else {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(transmitter.getSocket().getOutputStream()));
			bw.write("request:server:" + transmitter.getName() + "%" + reciever + " notconnected");
			bw.newLine();
			bw.flush();

		}
	}

	// H prospatheia(mantepsia) tou transmitter stelnete ston reciever gia na
	// elexthei
	void playJob(Client transmitter, String reciever, String message) throws IOException, InterruptedException {
		clients.get(reciever).getThread().join(100);
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(clients.get(reciever).getSocket().getOutputStream()));
		bw.write("play:" + transmitter.getName() + ":" + reciever + "%" + message);
		bw.newLine();
		bw.flush();
	}

	/*
	 * To score tis prospatheias tou reciever afou elexthei apo ton transmitter
	 * stelnete pisw ston reciever
	 */
	void scoreJob(Client transmitter, String reciever, String message) throws InterruptedException, IOException {
		clients.get(reciever).getThread().join(100);
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(clients.get(reciever).getSocket().getOutputStream()));
		bw.write("score:" + transmitter.getName() + ":" + reciever + "%" + message);
		bw.newLine();
		bw.flush();
	}

	// To teliko score kathe partidas apostelete apo ton transmitter ston
	// reciever
	void fscoreJob(Client transmitter, String reciever, String message) throws InterruptedException, IOException {
		clients.get(reciever).getThread().join(100);
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(clients.get(reciever).getSocket().getOutputStream()));
		bw.write("fscore:" + transmitter.getName() + ":" + reciever + "%" + message);
		bw.newLine();
		bw.flush();

	}

	void closeJob(Client transmitter) throws IOException {
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
