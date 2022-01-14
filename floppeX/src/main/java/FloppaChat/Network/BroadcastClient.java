package FloppaChat.Network;

import java.io.*;
import java.net.*;
;

public class BroadcastClient {
	private static DatagramSocket socket = null;
	private static int PortNb;

	public BroadcastClient(int PortNB) {
		PortNb=PortNB;
	}

    public void broadcast(String broadcastMessage, InetAddress address) throws IOException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);

        byte[] buffer = broadcastMessage.getBytes();

        DatagramPacket packet 
          = new DatagramPacket(buffer, buffer.length, address, PortNb);
        socket.send(packet);
        
        byte[] buff_answer = new byte[512];
        socket.setSoTimeout(3000);
            
		 while(true){
	            try {
	            	DatagramPacket packet1 = new DatagramPacket(buff_answer,buff_answer.length);
	                socket.receive(packet1);
	                BroadcastMultAnsHandler BMAH = new BroadcastMultAnsHandler(socket,packet1);
	                System.out.println("Starting thread ");
	                BMAH.start();
	            }
	            catch (SocketTimeoutException e) {
	            	System.out.println("Fin Timer");
	            	socket.disconnect();
	                socket.close();
	                break;
	            }
	        }
		 System.out.println("Out of the client while");
        
    }
}
