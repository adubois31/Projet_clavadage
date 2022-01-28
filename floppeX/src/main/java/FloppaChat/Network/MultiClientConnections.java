package FloppaChat.Network;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import FloppaChat.DataBase.ActiveUserManager;
import FloppaChat.DataBase.DBController;
import FloppaChat.GUI.Global;

public class MultiClientConnections {
	
	public static ArrayList<MessageClient> ClientConnections = new ArrayList<>();
	
	public static void SendMessAsClient(String TargetIP, String Message) {
		boolean alreadyConnected = false;
		for (MessageClient MC : ClientConnections) {
			if (MC.getRemoteIP().equals(TargetIP)){
				MC.SendMessToServer(Message);
				alreadyConnected=true;
				break;
			}
		}
		if(!(alreadyConnected)) {
			Socket socket = null;
			ActiveUserManager aUM = new ActiveUserManager();
			try {
				socket = new Socket(TargetIP,Global.MessServNb);
				MessageClient MC = new MessageClient(socket);
				ClientConnections.add(MC);
				DBController DBC = new DBController(Global.dbName);
				DBC.createUser(aUM.getActiveUserPseudo(TargetIP),TargetIP);
				MC.RecvMessFromServer();
				MC.SendMessToServer(Message);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//ED5318165BDABD82696C3E226FFE7FF4a
			
		}
	}
<<<<<<< HEAD
	
	public static void ClosingClients() {
		for (MessageClient MC : ClientConnections) {
			MC.EndChat();
		}
=======
	public static void PrintingClientConnections() {
		for (MessageClient MC : ClientConnections) {
			System.out.println("Client Connection is alive : "+MC);
		}
	}
	public static void ClosingClients() {
		PrintingClientConnections();
		Iterator<MessageClient> itr = ClientConnections.iterator();
		while(itr.hasNext()) {
			MessageClient MC = itr.next();
			System.out.println("Interrupting client : "+MC);
			MC.EndChat();
			removeClient(itr);
		}
		System.out.println("Clients who survived :");
		PrintingClientConnections();
	}
	
	public static synchronized void removeClient(Iterator<MessageClient> itr) {
		itr.remove();
>>>>>>> 4da3faf4ee9018b8af9e6b63fedc703886cb7622
	}

}
