package FloppaChat.DataBase;

public class ActiveUser {
	private String IP;
	private String Pseudo;
	public ActiveUser(String Ip,String Pseudo){
		this.IP =Ip;
		this.Pseudo=Pseudo;
	}
	
	public String getIP() {
		return this.IP;
	}
	
	public String getPseudo() {
		return this.Pseudo;
	}
}
