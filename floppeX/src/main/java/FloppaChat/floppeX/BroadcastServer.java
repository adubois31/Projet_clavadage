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
            /*
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            String received= new String(packet.getData(), 0, packet.getLength());
            System.out.println("Client : "+received);
            
            String Localhost = "Floppa";
            byte[] out_buffer = Localhost.getBytes();            
            packet = new DatagramPacket(out_buffer, out_buffer.length, address, port);
             */
            Process.BroadcastProcess(packet,socket);
            /*try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}*/

        }
        socket.close();
    }
    public static void main(String[] args) throws IOException {
    	Database_Manager.InitActiveUser("Floppa");
    	Database_Manager.PrintActiveUsers();
        BroadcastServer Serv = new BroadcastServer();
        Serv.run();
    }
}
