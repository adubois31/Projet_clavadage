package FloppaChat.GUI;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class Global {

	@FXML public static String userPseudo;
	
	public static int activeUserIndex;
	
	@FXML public static String activeUserChat;
	
	public static int activeUserID;
	
	public static String dbName = "flopbase";
	
	public static int BroadServNb = 9001;
	
	public static int MessServNb = 6969;
}
