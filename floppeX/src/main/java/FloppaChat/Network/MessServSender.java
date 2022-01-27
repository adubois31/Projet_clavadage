package FloppaChat.Network;


public class MessServSender {
	
	//goes through our clients and sends a message to the right one
	public static void SendMessToClient(String TargetIP,String Mess) {
		for (MessServWorker target : ServConnections.ClientList) {
			if(target.ClientIP().equals(TargetIP)) {
				target.SendMessToClient(Mess);
			}
		}
	}
	
	//checks if an other client is a client to our tcp server
	public static boolean isMessServer(String IPTarget) {
		boolean isServer = false;
		for (MessServWorker target : ServConnections.ClientList) {
			if(target.ClientIP().equals(IPTarget)) {
				isServer=true;
				break;
			}
		}
		return isServer;
	}

}
