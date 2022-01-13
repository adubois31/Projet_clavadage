package FloppaChat.DataBase;
import java.util.ArrayList;
import java.util.List;

public class ActiveUserManager {
	public static ArrayList<ActiveUser> Act_User_List = new ArrayList<ActiveUser>();
	
	
	public void InitActiveUser(String MyPseudo) {
		addActiveUser("127.0.0.1",MyPseudo);
	}
	
	
	public void addActiveUser(String IP, String Pseudo) {
		if (CheckPseudoUnicity(Pseudo)) {
			ActiveUser AU=new ActiveUser(IP,Pseudo);
			Act_User_List.add(AU);
		}
	}
	
	public void removeActiveUser(String IP, String Pseudo) {
		ActiveUser AU=new ActiveUser(IP,Pseudo);
		Act_User_List.remove(AU);
	}
	
	public String getActiveUserIP(String ActiveUser_Pseudo) {
		return Act_User_List.stream()
		.filter(a -> a.getPseudo().equals(ActiveUser_Pseudo))
		.map(ActiveUser::getIP)
		.findFirst()
		.get();
	}
	
	public String getActiveUserPseudo(String ActiveUser_IP) {
		return Act_User_List.stream()
				.filter(a -> a.getIP().equals(ActiveUser_IP))
				.map(ActiveUser::getPseudo)
				.findFirst()
				.get();	
	}
	
	public void UpdateActiveUserPseudo(String IP, String NewPseudo) {
		ActiveUser AU=new ActiveUser(IP,NewPseudo);
		for (ActiveUser au : Act_User_List) {
			if (au.getIP().equals(IP)) {
				Act_User_List.remove(au);
				Act_User_List.add(AU);
				break;
			}
		}	
	}
	
	public boolean pseudoExists(String pseudo) {
		return Act_User_List.stream().anyMatch(au -> au.getPseudo().equals(pseudo));
	}
	
	public void PrintActiveUsers() {
		for(ActiveUser au : Act_User_List){
			System.out.println("Pseudo : "+au.getPseudo()+" IP : "+au.getIP()+"\n");
	    }
	}
	
	
	
	public boolean CheckPseudoUnicity(String Pseudo) {
		return !Act_User_List.stream().anyMatch(au -> au.getPseudo().equals(Pseudo));
	}

}
