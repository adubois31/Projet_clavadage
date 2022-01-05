package FloppaChat.Network;


import java.io.IOException;
import FloppaChat.Network.BroadcastServerWorker;
import java.net.*;

import FloppaChat.DataBase.ActiveUserManager;


public class BroadcastServer extends Thread {
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];

    public BroadcastServer() throws SocketException {
        socket = new DatagramSocket(6969);
    }
    
    public void StopBroadcastServer() {
    	running = false;
    }

    public void launch() {
    	Thread t1 = new Thread(new Runnable() {
    	    @Override
    	    public void run() {
    	    	running = true;
        		while (running) {
        			DatagramPacket packet = new DatagramPacket(buf, buf.length);
        			try {
        				socket.receive(packet);
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        			BroadcastServerWorker worker = new BroadcastServerWorker(packet,socket);
        			worker.start();
        		}
        		socket.close();
        	}
    	});  
    	t1.run();
    	}
    	
}

