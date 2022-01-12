package FloppaChat.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServMess extends Thread{
	private int ServPort;
	private boolean isRunning = true;
	private ArrayList<MessServWorker> ClientList = new ArrayList<>();
	
	public ServMess(int Port) {
		this.ServPort=Port;
	}
	
	@Override
	public void run() {
		ServerSocket ServSock;
		try {
			ServSock = new ServerSocket(ServPort);
			Socket clientSock;
			System.out.println("Server Started");
			while(isRunning) {
				clientSock=ServSock.accept();
				System.out.println("Client connected");
				MessServWorker Client = new MessServWorker(clientSock);
				ClientList.add(Client);
				Client.start();
			}
			ServSock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void SendMessToClient(String TargetIP,String Mess) {
		for (MessServWorker target : ClientList) {
			if(target.ClientIP().equals(TargetIP)) {
				System.out.println("Message envoyé");
				target.SendMessToClient(Mess);
			}
		}
	}
}