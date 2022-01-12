package FloppaChat.Network;
import java.io.IOException;

public class MessageMainServer {
	private int ServPort = 9696;
	
	public void startServ() throws IOException{
		ServMess Server = new ServMess(ServPort);
		System.out.println("Main Server Started");
		Server.start();
		
	}
	
	
	

	

}
