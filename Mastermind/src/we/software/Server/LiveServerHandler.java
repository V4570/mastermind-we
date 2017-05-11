package we.software.Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map.Entry;

public class LiveServerHandler extends Thread{
	private HashMap<String,Client> clients;
	public boolean hide= false;
	
	public LiveServerHandler(HashMap<String,Client> clients) {
		super();
		this.clients = clients;
	}

	public void run(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true){
		
		try {
			String mes = br.readLine();
			this.commandHandler(mes);
			
		} catch (IOException | InterruptedException e) {
			System.out.println(e.getMessage());
		}
		
		
		}
		
	}
	
	public void commandHandler(String m) throws UnknownHostException, IOException, InterruptedException{
		if(m.equals("hide")){
			hide = true;
		}else if(m.equals("show")){
			hide = false;
		}else{
		String[] info = m.split("%",2);
		String cmd = info[0];
		String message = info[1];
		
		if(cmd.startsWith("allmessage")){
			this.messageAllJob(message);
		}
		else if(cmd.startsWith("message")){
			String receiver = cmd.split(":",2)[1];
			this.messageJob(receiver,message);
		}
		}
		
	}
	
	private void messageAllJob(String message) throws UnknownHostException, IOException, InterruptedException {
		if (!clients.isEmpty()) {
			for (Entry<String, Client> entry : clients.entrySet()) {
				Client value = entry.getValue();
					value.getThread().join(1);

					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(value.getSocket().getOutputStream()));
					bw.write("message:liveServer:" + value.getName() + "%" + message);
					bw.newLine();
					bw.flush();
				}
			

			System.out.println("Message sent to everyone!");
		} else {
			System.out.println("Noone connected at this moment");

		}
	}
	
	private void messageJob(String receiver, String message) throws UnknownHostException, IOException, InterruptedException {
		if (!clients.isEmpty() && clients.containsKey(receiver)) {
			clients.get(receiver).getThread().join(1);
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(clients.get(receiver).getSocket().getOutputStream()));
			bw.write("message:liveServer:" + receiver + "%" + message);
			bw.newLine();
			bw.flush();

		} else {
			System.out.println(receiver + " not online");

		}
	}
	
	
}