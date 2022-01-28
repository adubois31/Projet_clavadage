package FloppaChat.Network;

public class MessageMainServer {
	private int ServPort;
	private ServConnections Server;
	
	public MessageMainServer(int PortNB) {
		this.ServPort=PortNB;
	}
	
	public void startServ() {
		Server = new ServConnections(ServPort);
		System.out.println("Main Server Started");
		Server.start();
		
	}
	
	public void stopServ() {
		if(Server.isAlive()) {
			System.out.println("Closing Main Server....");
			Server.interrupt();
			System.out.println("Main Server Closed");
		}
	}
	
	
	

	

}
