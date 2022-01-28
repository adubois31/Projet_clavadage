package FloppaChat.Network;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import FloppaChat.DataBase.ActiveUserManager;
import FloppaChat.DataBase.DBController;
import FloppaChat.GUI.Global;

//manages all the connections as a client when chatting with the users
public class MultiClientConnections {
	
	public static ArrayList<MessageClient> ClientConnections = new ArrayList<>();
	
	
	public static void SendMessAsClient(String TargetIP, String Message) {
		boolean alreadyConnected = false;
		for (MessageClient MC : ClientConnections) {
			//if the (server) user connection is already made and stored in ClientConnection sends it to him
			if (MC.getRemoteIP().equals(TargetIP)){
				MC.SendMessToServer(Message);
				alreadyConnected=true;
				break;
			}
		}
		//if it's the 1st time we want to send a message to this server
		if(!(alreadyConnected)) {
			Socket socket = null;
			ActiveUserManager aUM = new ActiveUserManager();
			try {
				//we create a client socket and MessageClient
				socket = new Socket(TargetIP,Global.MessServNb);
				MessageClient MC = new MessageClient(socket);
				//we add him to our client connections and to the db if not already done
				ClientConnections.add(MC);
				DBController DBC = new DBController(Global.dbName);
				DBC.createUser(aUM.getActiveUserPseudo(TargetIP),TargetIP);
				//starts the message client thread to wait for the server messages and sends the message to the server
				MC.RecvMessFromServer();
				MC.SendMessToServer(Message);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
	
	//closing all the connections with the servers (as a client)
	public static void ClosingClients() {
		Iterator<MessageClient> itr = ClientConnections.iterator();
		while(itr.hasNext()) {
			MessageClient MC = itr.next();
			MC.EndChat();
			removeClient(itr);
		}
	}
	
	//removes safely a connection to a server from our ClientConnections iterator
	public static synchronized void removeClient(Iterator<MessageClient> itr) {
		itr.remove();
>>>>>>> 4da3faf4ee9018b8af9e6b63fedc703886cb7622
	}

}
