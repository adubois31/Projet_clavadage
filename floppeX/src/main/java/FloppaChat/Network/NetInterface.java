package FloppaChat.Network; 

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import FloppaChat.DataBase.ActiveUserManager;
import FloppaChat.GUI.Global;

public class NetInterface {

	static ActiveUserManager aUM= new ActiveUserManager();
	
	//method used to ask other users if our pseudo is free : 
	//returns true if it's the case, false if it's taken


	public static boolean ChoosePseudo (String Pseudo) throws UnknownHostException, IOException {
		BroadcastClient BC = new BroadcastClient(Global.BroadServNb);
		boolean PseudoOK=true;
		BC.broadcast(Packet.Hello(Pseudo), InetAddress.getByName(Global.BroadAdress));
		aUM.PrintActiveUsers();
		Process.SetHelloAccepted(true);
		return PseudoOK;
	}
	
	//asks the other users if my new pseudo is taken
	//if it's free it notifies the users that we are changing our pseudo for the new one and returns true
	//if it's taken it returns false

	public static boolean ChangePseudo(String OldPseudo,String NewPseudo) throws UnknownHostException, IOException {
		boolean ChangePseudoOk;
		BroadcastClient BC = new BroadcastClient(Global.BroadServNb);
		BC.broadcast(Packet.ChangePseudo(OldPseudo, NewPseudo), InetAddress.getByName(Global.BroadAdress));
		ChangePseudoOk = Process.getChangePseudoAccepted();
		if (ChangePseudoOk) {
			BC.broadcast(Packet.ConfirmedNewPseudo(NewPseudo,Global.userPseudo), InetAddress.getByName(Global.BroadAdress));

		}
		Process.setChangePseudoAccepted(true);
		return ChangePseudoOk;
	}


	// Send packet to all users when disconnected
	public static void Disconnect() {
		BroadcastClient BC = new BroadcastClient(Global.BroadServNb);
		try {
			BC.broadcast(Packet.Disconnected(Global.userPseudo), InetAddress.getByName(Global.BroadAdress));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
