package FloppaChat.Network;


import java.io.IOException;
import java.net.*;

import FloppaChat.GUI.Global;
import FloppaChat.Network.Process;


public class BroadcastServer extends Thread {
    private DatagramSocket socket;
    private int PortNb;
    private boolean running;
    private byte[] buf = new byte[256];

    public BroadcastServer(int PortNB) throws SocketException {
    	this.PortNb=PortNB;
        socket = new DatagramSocket(PortNb);
    }
    
    @Override
    public void interrupt() {
    	Global.BroadServRunning=false;
    	socket.close();
    	Thread.currentThread().interrupt();
    }
    @Override
    public void run() {
    	System.out.println("Server Thread Started...");
    	running = true;
    	while (running) {
    		DatagramPacket packet = new DatagramPacket(buf, buf.length);
    		try {
    			System.out.println("Waiting for packet...");
    			socket.receive(packet);
    			System.out.println("Packet received");
    			Thread BroadcastServerWorkerThread = new Thread(new Runnable() {
    				@Override
    				public void run() {
    					System.out.println("Worker started");
    					BroadcastClientHandler(packet,socket);
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
    			});
    			BroadcastServerWorkerThread.setName("Broadcast Worker");
    			BroadcastServerWorkerThread.start();
    		} catch (IOException e) {
    			System.out.println("Fermeture socket serv broadcast");
    			break;
    		}
    	}
    	socket.close();
    }
}
    	

