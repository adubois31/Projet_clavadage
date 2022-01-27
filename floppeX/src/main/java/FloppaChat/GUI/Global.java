package FloppaChat.GUI;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
	
	public static String nowDate() {
		LocalTime myTimeObj = LocalTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");
		LocalDate myDateObj = LocalDate.now();
		DateTimeFormatter myFormatObj2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return myTimeObj.format(myFormatObj)+" "+myDateObj.format(myFormatObj2);
	}
}