package FloppaChat.floppeX;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;


class ActiveUser{
	String IP;
	String Pseudo;
	ActiveUser(String Ip,String Pseudo){
		this.IP =Ip;
		this.Pseudo=Pseudo;
		
	}
}


public class Database_Manager {
	static ArrayList<ActiveUser> Act_User_List = new ArrayList<ActiveUser>();
	
	public static void InitActiveUser(String MyPseudo) {
		ActiveUser AU=new ActiveUser("127.0.0.1",MyPseudo);
		Act_User_List.add(AU);
	}
	
	
	public static void addActiveUser(String IP, String Pseudo) {
		ActiveUser AU=new ActiveUser(IP,Pseudo);
		Act_User_List.add(AU);
	}
	
	public static void removeActiveUser(String IP, String Pseudo) {
		ActiveUser AU=new ActiveUser(IP,Pseudo);
		Act_User_List.remove(AU);
	}
	
	public static String getActiveUser_IP(String ActiveUser_Pseudo) {
		String IP = null;
		for (int i = 0; i < Act_User_List.size(); i++) {
			if (Act_User_List.get(i).Pseudo==ActiveUser_Pseudo) {
				IP=Act_User_List.get(i).IP;
				break;
				}
			}
		return IP;	
	}
	
	public static String getActiveUser_Pseudo(String ActiveUser_IP) {
		String Pseudo = null;
		for (int i = 0; i < Act_User_List.size(); i++) {
			if (Act_User_List.get(i).IP==ActiveUser_IP) {
				Pseudo=Act_User_List.get(i).Pseudo;
				break;
				}
			}
		return Pseudo;	
	}
	
	public static void UpdateActive_User_Pseudo(String IP, String NewPseudo) {
		ActiveUser AU=new ActiveUser(IP,NewPseudo);
		for (int i = 0; i < Act_User_List.size(); i++) {
			if (Act_User_List.get(i).IP==IP) {
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
			if(Act_User_List.get(i).Pseudo==Pseudo) {
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
		PrintActiveUsers();
		
    }*/
}
