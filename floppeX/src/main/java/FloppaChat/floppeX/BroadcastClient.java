package FloppaChat.floppeX;

import java.io.*;
import java.net.*;
;

public class BroadcastClient {
	private static DatagramSocket socket = null;

    public static void main(String[] args) throws IOException {
        broadcast(Packet.Hello("Localhost"), InetAddress.getByName("255.255.255.255"));
    }

    public static void broadcast(String broadcastMessage, InetAddress address) throws IOException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);

        byte[] buffer = broadcastMessage.getBytes();
        System.out.println("Client Running...\n");

        DatagramPacket packet 
          = new DatagramPacket(buffer, buffer.length, address, 6969);
        socket.send(packet);
        
        byte[] buff_answer = new byte[256];
        DatagramPacket inPacket = new DatagramPacket(buff_answer,buff_answer.length);
        socket.receive(inPacket);
        Process.BroadcastProcess(inPacket);
        /*String response = new String(inPacket.getData(), 0, inPacket.getLength());
        System.out.println("Answer : "+response);*/
        
        
        socket.close();
        
    }
}
