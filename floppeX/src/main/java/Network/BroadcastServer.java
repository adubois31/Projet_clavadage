package Network;


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
        running = true;

        while (running) {
            DatagramPacket packet 
              = new DatagramPacket(buf, buf.length);
            try {
				socket.receive(packet);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
            Process process = new Process();
            try {
				process.BroadcastProcess(packet, socket);
			} catch (IOException e) {
				e.printStackTrace();
			}

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
