package FloppaChat.Network;

public class Packet {
	
	public static String Hello(String Local_Pseudo) {
		return("Hello|"+Local_Pseudo);
	}
	
	public static String HelloBack(String Check,String SenderPseudo) {
		return ("Hello_Back|"+Check+"|"+SenderPseudo);
	}
	
	public static String ChangePseudo(String OldPseudo,String NewPseudo) {
		return("ChangePseudo|"+OldPseudo+"|"+NewPseudo);
	}
	
	public static String ChangePseudoAns(String NewCheck,String SenderPseudo) {
		return("ChangePseudoAns|"+NewCheck+"|"+SenderPseudo);
	}
	
	public static String ConfirmedNewPseudo(String NewPseudo) {
		return("ConfirmedNewPseudo|"+NewPseudo);
	}
	
	public static String Disconnected(String LocalPseudo) {
		return ("Bingus|"+LocalPseudo);
	}
	
	public static String Ack(String NewPseudo) {
		return ("ACK|"+NewPseudo);
	}
}