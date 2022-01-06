package FloppaChat.Network;


import java.io.IOException;
import FloppaChat.Network.BroadcastServerWorker;
import java.net.*;

import FloppaChat.DataBase.ActiveUserManager;


public class BroadcastServer extends Thread {
    private DatagramSocket socket;
    private int PortNb = 9001;
    private boolean running;
    private byte[] buf = new byte[256];

    public BroadcastServer() throws SocketException {
        socket = new DatagramSocket(PortNb);
    }
    
    public void StopBroadcastServer() {
    	running = false;
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
    			BroadcastServerWorker worker = new BroadcastServerWorker(packet,socket);
        		worker.start();
    			} 
    		catch (IOException e) {
    			e.printStackTrace();
    			}
    		}
    	socket.close();
    	}
    }
    	

