package FloppaChat.Network;
//different formats of broadcast messages for specific uses
public class Packet {
	
	//Flag and format for asking if our pseudo is free as a new user
	public static String Hello(String Local_Pseudo) {
		return("Hello|"+Local_Pseudo);
	}
	
	//Flag and format for the answer to new user asking if a pseudo is free
	public static String HelloBack(String Check,String SenderPseudo) {
		return ("Hello_Back|"+Check+"|"+SenderPseudo);
	}
	
	//Flag and format for asking others if our new pseudo is ok
	public static String ChangePseudo(String OldPseudo,String NewPseudo) {
		return("ChangePseudo|"+OldPseudo+"|"+NewPseudo);
	}
	//Flag and format to answer the change of pseudo of a user
	public static String ChangePseudoAns(String NewCheck,String SenderPseudo) {
		return("ChangePseudoAns|"+NewCheck+"|"+SenderPseudo);
	}
	
	//Flag and format for confirming we are taking a new pseudo
	public static String ConfirmedNewPseudo(String NewPseudo,String SenderPseudo) {
		return("ConfirmedNewPseudo|"+NewPseudo+SenderPseudo);
	}
	
	//Flag and format for the disconnection message
	public static String Disconnected(String LocalPseudo) {
		return ("Bingus|"+LocalPseudo);
	}
	
	//Flag and format for the answer to a confirmed change of pseudo
	public static String Ack(String NewPseudo) {
		return ("ACK|"+NewPseudo);
	}
}