package DataBase;
//import java.io.IOException;
import java.util.ArrayList;


class ActiveUser{
	String IP;
	String Pseudo;
	ActiveUser(String Ip,String Pseudo){
		this.IP =Ip;
		this.Pseudo=Pseudo;
		
	}
}


public class ActiveUser_Manager {
	static ArrayList<ActiveUser> Act_User_List = new ArrayList<ActiveUser>();
	
	public static void InitActiveUser(String MyPseudo) {
		addActiveUser("127.0.0.1",MyPseudo);
	}
	
	
	public static void addActiveUser(String IP, String Pseudo) {
		if (CheckPseudoUnicity(Pseudo)) {
			ActiveUser AU=new ActiveUser(IP,Pseudo);
			Act_User_List.add(AU);
		}
	}
	
	public static void removeActiveUser(String IP, String Pseudo) {
		ActiveUser AU=new ActiveUser(IP,Pseudo);
		Act_User_List.remove(AU);
	}
	
	public static String getActiveUser_IP(String ActiveUser_Pseudo) {
		String IP = null;
		for (int i = 0; i < Act_User_List.size(); i++) {
			if (Act_User_List.get(i).Pseudo.equals(ActiveUser_Pseudo)) {
				IP=Act_User_List.get(i).IP;
				break;
				}
			}
		return IP;	
	}
	
	public static String getActiveUser_Pseudo(String ActiveUser_IP) {
		String Pseudo = null;
		for (int i = 0; i < Act_User_List.size(); i++) {
			if (Act_User_List.get(i).IP.equals(ActiveUser_IP)) {
				Pseudo=Act_User_List.get(i).Pseudo;
				break;
				}
			}
		return Pseudo;	
	}
	
	public static void UpdateActive_User_Pseudo(String IP, String NewPseudo) {
		ActiveUser AU=new ActiveUser(IP,NewPseudo);
		for (int i = 0; i < Act_User_List.size(); i++) {
			if (Act_User_List.get(i).IP.equals(IP)) {
				Act_User_List.set(i, AU);
				break;
				}
			}
		
		}
	
	public static void PrintActiveUsers() {
		for(int i=0; i< Act_User_List.size(); i++){
			ActiveUser AU=Act_User_List.get(i);
			System.out.println("Pseudo : "+AU.Pseudo+" IP : "+AU.IP+"\n");

	    }
	}
	
	
	
	public static boolean CheckPseudoUnicity(String Pseudo) {
		boolean check = true;
		for (int i = 0; i < Act_User_List.size(); i++) {
			if(Act_User_List.get(i).Pseudo.equals(Pseudo)) {
				check=false;
			}
		}		
		return check;
	}
	/*public static void main(String[] args) throws IOException {
		InitActiveUser("admin");
		PrintActiveUsers();
		System.out.println("Adding Viktor...\n");
		addActiveUser("192.168.1.1", "Viktor");
		}*/
		
}
