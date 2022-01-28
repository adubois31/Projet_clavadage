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
	private static int Count = 1;

	public ServConnections(int Port) {
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
				Client.setName("Client n° "+Count);
				Count++;
				Client.start();
				ClientList.add(Client);
				PrintClientList();
			}
			ServSock.close();
		} catch (IOException e) {
		}
	}
	
	public static synchronized void removeClientConnection(Iterator<MessServWorker> itr) {
		itr.remove();
	}

	@Override
	public void interrupt() {
		PrintClientList();
		System.out.println("----------------------------------------");
		System.out.println("Interupting ServMess... ");
		Iterator<MessServWorker> itr = ClientList.iterator();
		while(itr.hasNext()) {
			MessServWorker target = itr.next();
			System.out.println("Interupting Client Thread : "+target.getId());
			target.interrupt();
			removeClientConnection(itr);
		}
		PrintClientList();
		
		try {
			ServSock.close();
		} catch (IOException e) {
			System.out.println("error closing ServSock");
		}
		System.out.println("---------------------------------------");
		System.out.println("Closing Serv Message");
		super.interrupt();
		System.out.println("Interrupting thread "+super.getId());
	}
	
	public static void PrintClientList() {
		for (MessServWorker target: ClientList) {
			System.out.println("Worker alive "+ target+" "+target.ClientIP());
		}
	}
	
	
	
	
}
