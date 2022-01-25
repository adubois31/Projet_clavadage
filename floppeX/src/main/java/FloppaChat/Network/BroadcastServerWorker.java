package FloppaChat.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class BroadcastServerWorker extends Thread{
	
	private DatagramPacket packet;
	private DatagramSocket socket;
	
	public BroadcastServerWorker(DatagramPacket packet,DatagramSocket socket) {
		this.packet=packet;
		this.socket=socket;
	}
	
	@Override
	public void run() {
		System.out.println("Worker started");
		BroadcastClientHandler(this.packet,this.socket);
	}
	
	private void BroadcastClientHandler(DatagramPacket packet,DatagramSocket socket) {
    	Process process = new Process();
		try {
			process.BroadcastProcess(packet, socket);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }
}
