package FloppaChat.DataBase;

import FloppaChat.GUI.Global;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ActiveUserManager {
	public static ObservableList<ActiveUserCustom> Act_User_List = FXCollections.observableArrayList(ActiveUserCustom.extractor());
	
	
	/*public void InitActiveUser(String MyPseudo) {
		addActiveUser("127.0.0.1",MyPseudo);
	}*/
	
	
	public void addActiveUser(String IP, String Pseudo) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (CheckPseudoUnicity(Pseudo)) {
					ActiveUserCustom AU=new ActiveUserCustom();
					AU.pseudo.set(Pseudo);
					AU.IP.set(IP);
					Act_User_List.add(AU);
				}	
			}
		});
	}
	
	public void removeActiveUser(String IP, String Pseudo) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				ActiveUserCustom AU=new ActiveUserCustom();
				AU.IP.set(IP);
				AU.pseudo.set(Pseudo);
				Act_User_List.remove(AU);
			}
		});
	}
	
	public String getActiveUserIP(String ActiveUser_Pseudo) {
		return Act_User_List.stream()
		.filter(a -> a.getPseudo().equals(ActiveUser_Pseudo))
		.map(ActiveUserCustom::getIP)
		.findFirst()
		.get();
	}
	
	public String getActiveUserPseudo(String ActiveUser_IP) {
		return Act_User_List.stream()
				.filter(a -> a.getIP().equals(ActiveUser_IP))
				.map(ActiveUserCustom::getPseudo)
				.findFirst()
				.get();	
	}
	
	public void UpdateActiveUserPseudo(String IP, String NewPseudo) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				ActiveUserCustom AU=new ActiveUserCustom();
				AU.IP.set(IP);
				AU.pseudo.set(NewPseudo);
				for (ActiveUserCustom au : Act_User_List) {
					if (au.getIP().equals(IP)) {
						Act_User_List.remove(au);
						Act_User_List.add(AU);
						break;
					}
				}
			}
		});	
	}
	
	public boolean pseudoExists(String pseudo) {
		return Act_User_List.stream().anyMatch(au -> au.getPseudo().equals(pseudo));
	}
	
	public void PrintActiveUsers() {
		for(ActiveUserCustom au : Act_User_List){
			System.out.println("Pseudo : "+au.getPseudo()+" IP : "+au.getIP()+"\n");
	    }
	}
	
	
	
	public boolean CheckPseudoUnicity(String Pseudo) {
		return !Act_User_List.stream().anyMatch(au -> au.getPseudo().equals(Pseudo)) && !Global.userPseudo.equals(Pseudo);
	}

}
