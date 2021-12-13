package FloppaChat.floppeX;


import java.io.IOException;
import java.net.*;


public class BroadcastServer extends Thread {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];

    public BroadcastServer() throws SocketException {
        socket = new DatagramSocket(6969);
    }

    public void run() {
    	System.out.println("Server Running...\n");
        running = true;

        while (running) {
            DatagramPacket packet 
              = new DatagramPacket(buf, buf.length);
            System.out.println("Created socket\n");
            try {
            	System.out.println("Server listening...\n");
				socket.receive(packet);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
            System.out.println("Packet recieved\n");
            Process process = new Process();
			/*
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            String received= new String(packet.getData(), 0, packet.getLength());
            System.out.println("Client : "+received);
            
            String Localhost = "Floppa";
            byte[] out_buffer = Localhost.getBytes();            
            packet = new DatagramPacket(out_buffer, out_buffer.length, address, port);
             */
            try {
				process.BroadcastProcess(packet, socket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            /*try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}*/

        }
        socket.close();
    }
    public static void main(String[] args) throws IOException {
    	ActiveUser_Manager.InitActiveUser("Viktor");
    	ActiveUser_Manager.addActiveUser("192.168.1.1", "Bingus");
    	ActiveUser_Manager.PrintActiveUsers();
        BroadcastServer Serv = new BroadcastServer();
        Serv.run();
    }
}
