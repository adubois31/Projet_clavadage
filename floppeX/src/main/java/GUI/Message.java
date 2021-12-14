package GUI;

public class Message {
	private String DateID;
	private User user;
	private Date date;
	private String content;
	private Boolean sent;
	
	public Message(String DateID,User user,Date date,String content,Boolean sent) {
		this.DateID=DateID;
		this.user=user;
		this.date=date;
		this.content=content;
		this.sent=sent;
	}
	
	public String getID() {
		return this.DateID;
	}
}
