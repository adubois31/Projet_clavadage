package FloppaChat.Network; 

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import FloppaChat.DataBase.ActiveUserManager;
import FloppaChat.GUI.Global;

public class NetInterface {
	static ActiveUserManager aUM= new ActiveUserManager();

	// Method for local user to make sure that the pseudo he chose is ok
	public static boolean ChoosePseudo (String Pseudo) throws UnknownHostException, IOException {
		BroadcastClient BC = new BroadcastClient(Global.BroadServNb);
		boolean PseudoOK=true;
		BC.broadcast(Packet.Hello(Pseudo), InetAddress.getByName(Global.BroadAdress));
		aUM.PrintActiveUsers();
		Process.SetHelloAccepted(true);
		return PseudoOK;
	}
	
	// Method for local user to make sure own pseudo is ok
	public static boolean ChangePseudo(String OldPseudo,String NewPseudo) throws UnknownHostException, IOException {
		boolean ChangePseudoOk;
		BroadcastClient BC = new BroadcastClient(Global.BroadServNb);
		BC.broadcast(Packet.ChangePseudo(OldPseudo, NewPseudo), InetAddress.getByName(Global.BroadAdress));
		ChangePseudoOk = Process.getChangePseudoAccepted();
		System.out.println("Compteur fini "+ChangePseudoOk);
		if (ChangePseudoOk) {
			BC.broadcast(Packet.ConfirmedNewPseudo(NewPseudo), InetAddress.getByName(Global.BroadAdress));
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
