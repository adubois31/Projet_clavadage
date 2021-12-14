package FloppaChat.DataBase;

public class User {
	private int ID;
	private String Pseudo;
	private String IPadress;
	
	public User(String Pseudo,String IP) {
		this.Pseudo=Pseudo;
		this.IPadress=IP;
	}
	
	public User(int ID,String Pseudo,String IP) {
		this.ID=ID;
		this.Pseudo=Pseudo;
		this.IPadress=IP;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public String getPseudo() {
		return this.Pseudo;
	}
	
	public String getIP() {
		return this.IPadress;
	}
}
