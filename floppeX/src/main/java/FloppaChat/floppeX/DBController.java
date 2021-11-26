package FloppaChat.floppeX;
import java.sql.*;

public class DBController {
	
	private Connection connection;
	
	public DBController() {
		try {
		Class.forName("org.sqlite.JDBC");
		this.connection=null;
		} catch(ClassNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void openConnectionDB(){
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/sqlite/db/test.db");
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void createTable(String name,String values){
		try {
			this.openConnectionDB();
			String query = "CREATE TABLE "+name+" "+values+";";
			Statement statement = this.connection.createStatement();
			statement.executeQuery(query);
			this.connection.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void insertIntoTable(String name, String values) {
		try {
			this.openConnectionDB();
			String query = "INSERT INTO "+name+" VALUES "+values+";";
			PreparedStatement statement = this.connection.prepareStatement(query);
			statement.executeUpdate(query);
			this.connection.close();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
	}
}
