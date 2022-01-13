package FloppaChat.Network;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

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
			try {
				socket = new Socket(TargetIP,Global.MessServNb);
				MessageClient MC = new MessageClient(socket);
				ClientConnections.add(MC);
				MC.RecvMessFromServer();
				MC.SendMessToServer(Message);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

}
