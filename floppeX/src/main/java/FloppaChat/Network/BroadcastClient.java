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
        System.out.println("Client Running...\n");

        DatagramPacket packet 
          = new DatagramPacket(buffer, buffer.length, address, 6969);
        socket.send(packet);
        System.out.println("Sending Packet...\n");
        byte[] buff_answer = new byte[512];
        DatagramPacket packet1 = new DatagramPacket(buff_answer,buff_answer.length);
        System.out.println(packet1);
        socket.receive(packet1);
        System.out.println("Recieved a Packet...\n");
        Process process = new Process();
		process.BroadcastProcess(packet1, socket);        
        
        socket.close();
        
    }
}
