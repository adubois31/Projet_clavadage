package FloppaChat.Network; 

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;

import FloppaChat.DataBase.ActiveUserManager;
import FloppaChat.GUI.Global;

public class NetInterface {
	private static String MyInterface = "eth0";
	
	static ActiveUserManager aUM= new ActiveUserManager();
	public static boolean ChoosePseudo (String Pseudo) throws UnknownHostException, IOException {
		BroadcastClient BC = new BroadcastClient(Global.BroadServNb);
		boolean PseudoOK=true;
		BC.broadcast(Packet.Hello(Pseudo), InetAddress.getByName(Global.BroadAdress));
		long start = System.currentTimeMillis();
		long end = start + 1*1000;
		while (System.currentTimeMillis() < end) {
		    if(!(Process.getHelloAccepted())) {
		    	PseudoOK=false;
		    	break;
		    }
		}
		aUM.PrintActiveUsers();
		Process.SetHelloAccepted(true);
		return PseudoOK;
	}
	public static boolean ChangePseudo(String OldPseudo,String NewPseudo) throws UnknownHostException, IOException {
		boolean ChangePseudoOk =true;
		BroadcastClient BC = new BroadcastClient(Global.BroadServNb);
		BC.broadcast(Packet.ChangePseudo(OldPseudo, NewPseudo), InetAddress.getByName(Global.BroadAdress));
		long start = System.currentTimeMillis();
		long end = start + 4*1000;
		while (System.currentTimeMillis() < end) {
		    if(!(Process.getChangePseudoAccepted())) {
		    	ChangePseudoOk=false;
		    	break;
		    }
		}
		System.out.println("Compteur fini "+ChangePseudoOk);
		if (ChangePseudoOk) {
			aUM.UpdateActiveUserPseudo("127.0.0.1", NewPseudo);
			BC.broadcast(Packet.ConfirmedNewPseudo(NewPseudo), InetAddress.getByName("10.1.255.255"));
		}
		Process.setChangePseudoAccepted(true);
		return ChangePseudoOk;
	}

	
    public static void Disconnect() {
    	BroadcastClient BC = new BroadcastClient(Global.BroadServNb);
		try {
			BC.broadcast(Packet.Disconnected(Global.userPseudo), InetAddress.getByName(Global.BroadAdress));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
}
