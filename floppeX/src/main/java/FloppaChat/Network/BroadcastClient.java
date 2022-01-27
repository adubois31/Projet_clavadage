package FloppaChat.Network;

import java.io.*;
import java.net.*;

;

public class BroadcastClient {
	private static DatagramSocket socket = null;
	private static int PortNb;
	private int timeout = 1000;

	public BroadcastClient(int PortNB) {
		PortNb=PortNB;
	}

    public void broadcast(String broadcastMessage, InetAddress address) throws IOException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);

        byte[] buffer = broadcastMessage.getBytes();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, PortNb);
        //sends the  broadcastMessage to all users
        socket.send(packet);
        
        byte[] buff_answer = new byte[512];
        //setting a timeout to give time to all users to answer the broadcast message
        socket.setSoTimeout(timeout);
            
		 while(true){
	            try {
	            	DatagramPacket packet1 = new DatagramPacket(buff_answer,buff_answer.length);
	                socket.receive(packet1);
	                //creating a thread for the user who answered
	                Thread BroadcastMultAnsThread = new Thread(new Runnable() {
	                	@Override
	                	public void run() {
	                		Process process = new Process();
	                		try {
	                			//processes this packet
	                			process.BroadcastProcess(packet1, socket);
	                		} catch (IOException e) {
	                			e.printStackTrace();
	                		}
	                	}
	                });
	                BroadcastMultAnsThread.start();
	            }
	            catch (SocketTimeoutException e) {
	            	System.out.println("Fin Timer");
	            	socket.disconnect();
	                socket.close();
	                break;
	            }
	        }
        
    }
}
