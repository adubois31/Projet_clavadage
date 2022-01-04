package FloppaChat.DataBase;

public class Message {
	private String Date;
	private int userID;
	private String content;
	private boolean sent;
	
	public Message(String Date,int userID,String content,boolean sent) {
		this.Date=Date;
		this.userID=userID;
		this.content=content;
		this.sent=sent;
	}
	
	public String getDate() {
		return this.Date;
	}
	
	public boolean isSent() {
		return this.sent;
	}
	
	public int getUserID() {
		return this.userID;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public String toString() {
		return "Date = "+Date+" userID = "+userID+" content = "+content+" sent = "+sent;
	}
}
