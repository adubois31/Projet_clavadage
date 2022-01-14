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
		BC.broadcast(Packet.Hello(Pseudo,NetInterface.GetIP()), InetAddress.getByName(Global.BroadAdress));
		long start = System.currentTimeMillis();
		long end = start + 1*1000;
		while (System.currentTimeMillis() < end) {
		    if(!(Process.getHelloAccepted())) {
		    	PseudoOK=false;
		    	break;
		    }
		}
		if (PseudoOK) {
			//aUM.InitActiveUser(Pseudo);
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
	
	public static String GetIP() throws SocketException {
		String RetAddr = null;
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets))
        	if (netint.getName().equals(MyInterface)) {
        		RetAddr=GrabIPbyName(netint);	
        	}
        return RetAddr;
            
    }

    static String GrabIPbyName (NetworkInterface netint) throws SocketException {
    	String RetAddr = null;
        
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
        	if (inetAddress instanceof Inet4Address) {
        		RetAddr = inetAddress.toString().substring(1);
        		break;
        	}
        }
        return RetAddr;
     }
}
