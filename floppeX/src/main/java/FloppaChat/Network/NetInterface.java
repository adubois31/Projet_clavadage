package FloppaChat.Network; 

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetInterface {
	public static boolean ChoosePseudo (String Pseudo) throws UnknownHostException, IOException {
		boolean PseudoOK=true;
		BroadcastClient.broadcast(Packet.Hello(Pseudo), InetAddress.getByName("255.255.255.255"));
		long start = System.currentTimeMillis();
		long end = start + 4*1000;
		while (System.currentTimeMillis() < end) {
		    if(!(Process.getHelloAccepted())) {
		    	PseudoOK=false;
		    	break;
		    }
		}
		return PseudoOK;
	}
	public static void main(String[] args) throws IOException {
        if (ChoosePseudo("Viktor")) {
        	System.out.println("Viktor est ok");
        	}
        else {
        	System.out.println("Viktor n'est pas ok");
        	}
        }
}
