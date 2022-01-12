package FloppaChat.Network;
import java.io.IOException;

public class MessageMainServer {
	private int ServPort;
	private ServMess Server;
	
	public MessageMainServer(int PortNB) {
		this.ServPort=PortNB;
	}
	
	public void startServ() {
		Server = new ServMess(ServPort);
		System.out.println("Main Server Started");
		Server.start();
		
	}
	
	public void stopServ() {
		if(Server.isAlive()) {
			Server.interrupt();
		}
	}
	
	
	

	

}
