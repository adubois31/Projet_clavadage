package FloppaChat.Network;


public class MessServSender {
	
	public static void SendMessToClient(String TargetIP,String Mess) {
		for (MessServWorker target : ServConnections.ClientList) {
			if(target.ClientIP().equals(TargetIP)) {
				System.out.println("Message envoyé");
				target.SendMessToClient(Mess);
			}
		}
	}
	
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
