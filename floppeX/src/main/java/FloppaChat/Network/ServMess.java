package FloppaChat.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServMess extends Thread{
	private int ServPort;
	private boolean isRunning = true;
	private ServerSocket ServSock;
	private ArrayList<MessServWorker> ClientList = new ArrayList<>();
	
	public ServMess(int Port) {
		this.ServPort=Port;
	}
	
	@Override
	public void run() {
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
		}
	}
	
	@Override
	public void interrupt() {
		for (MessServWorker target : ClientList) {
			if (target.isAlive()) {
				target.interrupt();
			}
		}
		super.interrupt();
		try {
			ServSock.close();
		} catch (IOException e) {
			System.out.println("Closing Serv Message");
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
