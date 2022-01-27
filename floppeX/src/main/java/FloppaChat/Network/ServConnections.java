package FloppaChat.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import FloppaChat.DataBase.ActiveUserManager;
import FloppaChat.DataBase.DBController;
import FloppaChat.GUI.Global;

public class ServConnections extends Thread{
	private int ServPort;
	private boolean isRunning = true;
	private ServerSocket ServSock;
	private ActiveUserManager aUM = new ActiveUserManager();
	public static ArrayList<MessServWorker> ClientList = new ArrayList<>();

	public ServConnections(int Port) {
		this.ServPort=Port;
	}

	@Override
	public void run() {
		try {
			ServSock = new ServerSocket(ServPort);
			Socket clientSock;
			while(isRunning) {
				//waits for a client to connect
				clientSock=ServSock.accept();
				DBController DBC = new DBController(Global.dbName);
				String ClientIP = clientSock.getInetAddress().toString().substring(1);
				//add the user to our db and starts a client thread to manage him
				DBC.createUser(aUM.getActiveUserPseudo(ClientIP), ClientIP);
				MessServWorker Client = new MessServWorker(clientSock);
				Client.start();
				ClientList.add(Client);
			}
			ServSock.close();
		} catch (IOException e) {
		}
	}
	
	//method to remove safely a client to our client list iterator
	public static synchronized void removeClientConnection(Iterator<MessServWorker> itr) {
		itr.remove();
	}

	@Override
	public void interrupt() {
		Iterator<MessServWorker> itr = ClientList.iterator();
		//interrupting all connections with clients when stopping our server
		while(itr.hasNext()) {
			MessServWorker target = itr.next();
			target.interrupt();
			removeClientConnection(itr);
		}
		
		try {
			ServSock.close();
		} catch (IOException e) {
			System.out.println("error closing ServSock");
			super.interrupt();
		}
		super.interrupt();
	}
	
	public static void PrintClientList() {
		for (MessServWorker target: ClientList) {
			System.out.println("Worker alive "+ target+" "+target.ClientIP());
		}
	}
	
	
	
	
}
