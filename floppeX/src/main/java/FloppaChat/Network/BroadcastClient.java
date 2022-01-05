package FloppaChat.Network;

import java.io.*;
import java.net.*;
;

public class BroadcastClient {
	private static DatagramSocket socket = null;



    public static void broadcast(String broadcastMessage, InetAddress address) throws IOException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);

        byte[] buffer = broadcastMessage.getBytes();

        DatagramPacket packet 
          = new DatagramPacket(buffer, buffer.length, address, 6969);
        socket.send(packet);
        
        byte[] buff_answer = new byte[512];
        DatagramPacket packet1 = new DatagramPacket(buff_answer,buff_answer.length);
        socket.setSoTimeout(1500);
            
		 while(true){
	            try {
	                socket.receive(packet1);
	                Process process = new Process();
	        		process.BroadcastProcess(packet1, socket);
	        		socket.close();
	        		break;
	            }
	            catch (SocketTimeoutException e) {
	                // timeout exception.
	                socket.close();
	                break;
	            }
	        }
        
    }
}
