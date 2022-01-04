package FloppaChat.Network; 

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import FloppaChat.DataBase.ActiveUserManager;

public class NetInterface {
	static ActiveUserManager aUM= new ActiveUserManager();
	public static boolean ChoosePseudo (String Pseudo) throws UnknownHostException, IOException {
		boolean PseudoOK=true;
		BroadcastClient.broadcast(Packet.Hello(Pseudo), InetAddress.getByName("10.1.255.255"));
		long start = System.currentTimeMillis();
		long end = start + 4*1000;
		while (System.currentTimeMillis() < end) {
		    if(!(Process.getHelloAccepted())) {
		    	PseudoOK=false;
		    	break;
		    }
		}
		Process.SetHelloAccepted(true);
		return PseudoOK;
	}
	public static boolean ChangePseudo(String OldPseudo,String NewPseudo) throws UnknownHostException, IOException {
		boolean ChangePseudoOk =true;
		BroadcastClient.broadcast(Packet.ChangePseudo(OldPseudo, NewPseudo), InetAddress.getByName("10.1.255.255"));
		long start = System.currentTimeMillis();
		long end = start + 2*1000;
		while (System.currentTimeMillis() < end) {
		    if(!(Process.getChangePseudoAccepted())) {
		    	ChangePseudoOk=false;
		    	break;
		    }
		}
		if (ChangePseudoOk) {
			aUM.UpdateActiveUserPseudo("127.0.0.1", NewPseudo);
			BroadcastClient.broadcast(Packet.ConfirmedNewPseudo(NewPseudo), InetAddress.getByName("10.1.255.255"));
		}
		Process.setChangePseudoAccepted(true);
		return ChangePseudoOk;
	}
	/*public static void main(String[] args) throws IOException {
        if (ChoosePseudo("Viktor")) {
        	System.out.println("Viktor est ok");
        	}
        else {
        	System.out.println("Viktor n'est pas ok");
        	}
        }*/
}
