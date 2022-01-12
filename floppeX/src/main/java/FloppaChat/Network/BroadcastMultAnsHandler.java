package FloppaChat.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class BroadcastMultAnsHandler extends Thread{
	private DatagramSocket socket;
	private DatagramPacket packet;
	
	
	public BroadcastMultAnsHandler (DatagramSocket Socket,DatagramPacket Packet) {
		this.socket=Socket;
		this.packet=Packet;
	}
	
	@Override
	public void run() {
		System.out.println("Nouveau thread réponse");
		Process process = new Process();
		try {
			System.out.println(packet.getAddress().toString());
			process.BroadcastProcess(packet, socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
