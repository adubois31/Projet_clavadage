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

public class NetInterface {
	private static String MyInterface = "eth0";
	
	static ActiveUserManager aUM= new ActiveUserManager();
	public static boolean ChoosePseudo (String Pseudo) throws UnknownHostException, IOException {
		boolean PseudoOK=true;
		BroadcastClient.broadcast(Packet.Hello(Pseudo,NetInterface.GetIP()), InetAddress.getByName("10.1.255.255"));
		long start = System.currentTimeMillis();
		long end = start + 10*1000;
		while (System.currentTimeMillis() < end) {
		    if(!(Process.getHelloAccepted())) {
		    	PseudoOK=false;
		    	break;
		    }
		}
		if (PseudoOK) {
			aUM.InitActiveUser(Pseudo);
		}
		aUM.PrintActiveUsers();
		Process.SetHelloAccepted(true);
		return PseudoOK;
	}
	public static boolean ChangePseudo(String OldPseudo,String NewPseudo) throws UnknownHostException, IOException {
		boolean ChangePseudoOk =true;
		BroadcastClient.broadcast(Packet.ChangePseudo(OldPseudo, NewPseudo), InetAddress.getByName("10.1.255.255"));
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
			BroadcastClient.broadcast(Packet.ConfirmedNewPseudo(NewPseudo), InetAddress.getByName("10.1.255.255"));
		}
		Process.setChangePseudoAccepted(true);
		return ChangePseudoOk;
	}
	
	public static String GetIP() throws SocketException {
		String RetAddr = null;
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets))
        	if (netint.getName().equals(MyInterface)) {
        		System.out.printf("Name: %s\n", netint.getName());
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
	/*public static String GetIP() throws SocketException {
		String RetAddr = null;
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets))
        	if(netint.getName().equals(MyInterface)) {
        		RetAddr=GrabIPbyName(netint);
        	}
		return RetAddr;
    }

    private static String GrabIPbyName(NetworkInterface netint) throws SocketException {
    	String RetAddr = null;
        System.out.println("Name: "+netint.getName());
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            System.out.println("InetAddress: "+ inetAddress.toString());
            RetAddr = inetAddress.toString();
            break;
        }
        return RetAddr;
     }*/

}
