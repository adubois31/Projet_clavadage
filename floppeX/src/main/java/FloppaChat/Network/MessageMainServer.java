package FloppaChat.Network;

public class MessageMainServer {
	private int ServPort;
	private ServConnections Server;
	
	public MessageMainServer(int PortNB) {
		this.ServPort=PortNB;
	}
	//starts the tcp server thread
	public void startServ() {
		Server = new ServConnections(ServPort);
		Server.start();
		
	}
	
	//stops the tcp server
	public void stopServ() {
		if(Server.isAlive()) {
			Server.interrupt();
		}
	}
	
	
	

	

}
