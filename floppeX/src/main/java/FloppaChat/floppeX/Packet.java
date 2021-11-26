package FloppaChat.floppeX;

public class Packet {
	
	public static String Hello(String Local_Pseudo) {
		return("Hello|"+Local_Pseudo);
	}
	
	public static String HelloBack(String Old_Pseudo, String NewPseudo) {
		return ("Hello_Back|"+Old_Pseudo+"|"+NewPseudo);
	}

	public static String Bye(String LocalPseudo) {
		return ("Bingus|"+LocalPseudo);
	}
}