package FloppaChat.GUI;

import javafx.fxml.FXML;

public class Global {

	@FXML public static String userPseudo;
	
	@FXML public static String activeUserChat;
	
	public static int activeUserIndex;
	
	public static int activeUserID;
	
	public static String dbName = "flopbase";
	
	public static int BroadServNb = 9001;
	
	public static String BroadAdress = "10.1.255.255";
	
	public static boolean BroadServRunning=false;
	
	public static int MessServNb = 6969;
	
	public static MainPageController MPC;
}