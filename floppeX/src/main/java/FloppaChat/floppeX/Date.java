package FloppaChat.floppeX;

public class Date {
	private String DateID;
	private int Year;
	private int Month;
	private int Day;
	private int Hour;
	private int Minute;
	
	public Date(String DateID,int year,int month,int day,int hour,int minute) {
		this.DateID = DateID;
		this.Year = year;
		this.Month = month;
		this.Day = day;
		this.Hour = hour;
		this.Minute = minute;
	}
	
	public String getTime() {
		return this.Hour+":"+this.Minute;
	}
	
	public String getID() {
		return this.DateID;
	}
}
