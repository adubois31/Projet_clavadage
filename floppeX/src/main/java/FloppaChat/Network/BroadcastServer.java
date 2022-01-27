package FloppaChat.Network;


import java.io.IOException;
import java.net.*;

import FloppaChat.GUI.Global;


public class BroadcastServer extends Thread {
    private DatagramSocket socket;
    private int PortNb;
    private byte[] buf = new byte[256];

    public BroadcastServer(int PortNB) throws SocketException {
    	this.PortNb=PortNB;
        socket = new DatagramSocket(PortNb);
    }
    
    //stops the thread and close the socket when interrupted
    @Override
    public void interrupt() {
    	Global.BroadServRunning=false;
    	socket.close();
    	Thread.currentThread().interrupt();
    }
    
    
    @Override
    public void run() {
    	System.out.println("Server Thread Started...");
    	while (true) {
    		DatagramPacket packet = new DatagramPacket(buf, buf.length);
    		try {
    			socket.receive(packet);
    			//creates a new thread to handle the received broadcast packet
    			Thread BroadcastServerWorkerThread = new Thread(new Runnable() {
    				@Override
    				public void run() {
    					BroadcastClientHandler(packet,socket);
    				}
    				//send to process the received packet
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
    			BroadcastServerWorkerThread.start();
    		} catch (IOException e) {
    			break;
    		}
    	}
    	socket.close();
    }
}
    	

