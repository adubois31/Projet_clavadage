package FloppaChat.Network;

import java.io.*;
import java.net.*;
;

public class BroadcastClient {
	private static DatagramSocket socket = null;



    public static void broadcast(String broadcastMessage, InetAddress address) throws IOException {
    	int nbThread = 0;
        socket = new DatagramSocket();
        socket.setBroadcast(true);

        byte[] buffer = broadcastMessage.getBytes();

        DatagramPacket packet 
          = new DatagramPacket(buffer, buffer.length, address, 6969);
        socket.send(packet);
        
        byte[] buff_answer = new byte[512];
        DatagramPacket packet1 = new DatagramPacket(buff_answer,buff_answer.length);
        socket.setSoTimeout(9000);
            
		 while(true){
	            try {
	            	System.out.println("Attente packet numéro :  "+(nbThread+1));
	                socket.receive(packet1);
	                BroadcastMultAnsHandler BMAH = new BroadcastMultAnsHandler(socket,packet1);
	                System.out.println("Starting thread ");
	                nbThread++;
	                BMAH.start();
	            }
	            catch (SocketTimeoutException e) {
	                // timeout exception.
	            	System.out.println("Fin Timer");
	                socket.close();
	                break;
	            }
	        }
        
    }
}
