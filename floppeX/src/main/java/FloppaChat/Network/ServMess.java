package FloppaChat.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import FloppaChat.DataBase.ActiveUserManager;
import FloppaChat.DataBase.DBController;
import FloppaChat.GUI.Global;

public class ServMess extends Thread{
	private int ServPort;
	private boolean isRunning = true;
	private ServerSocket ServSock;
	private ActiveUserManager aUM = new ActiveUserManager();
	public static ArrayList<MessServWorker> ClientList = new ArrayList<>();

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
				DBController DBC = new DBController(Global.dbName);
				String ClientIP = clientSock.getInetAddress().toString().substring(1);
				DBC.createUser(aUM.getActiveUserPseudo(ClientIP), ClientIP);
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
				System.out.println("Stopping client thread "+target);
				target.interrupt();
			}
		}
		for (MessServWorker target : ClientList) {
			if (target.isAlive()) {
				System.out.println(target+" is alive");
			}
		}
		super.interrupt();
		try {
			ServSock.close();
		} catch (IOException e) {
			System.out.println("Closing Serv Message");
		}
	}
	
	
}
