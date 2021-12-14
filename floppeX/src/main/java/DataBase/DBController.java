package DataBase;
import java.sql.*;

public class DBController {
	
	private String dbName;
	
	public DBController(String path) {
		try {
			Class.forName("org.sqlite.JDBC");
			this.dbName = path;
		} catch(ClassNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:src/main/resources/sqlite/db/"+this.dbName+".db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	
	public void createTable(String name,String values){
		try (Connection con = this.connect()){
			String query = "CREATE TABLE IF NOT EXISTS "+name+" "+values+";";
			Statement statement = con.createStatement();
			statement.execute(query);
		} catch(SQLException e) {
			System.err.println("Error at creating table");
			System.err.println(e.getMessage());
		}
	}
	
	public void insertIntoTable(String name, String values) {
		try (Connection con = this.connect()){
			String query = "INSERT INTO "+name+" VALUES "+values+";";
			PreparedStatement statement = con.prepareStatement(query);
			statement.executeUpdate();
		} catch(SQLException e) {
			System.err.println("Error at inserting in table");
			System.err.println(e.getMessage());
		}
	}
	
	public void initDatabase() {
		String UserTable = 
				"(UserID Integer PRIMARY KEY AUTOINCREMENT,"
				+ "Pseudo varchar(255),"
				+ "IPadress varchar(255))";
		
		String DateTable = 
				"(DateID Integer PRIMARY KEY AUTOINCREMENT,"
				+ "Year Integer,"
				+ "Month Integer,"
				+ "Day Integer,"
				+ "Hour Integer,"
				+ "Minute Integer)";
		
		String MessageTable =
				"(MessageID Integer PRIMARY KEY AUTOINCREMENT,"
				+ "UserID Integer NOT NULL,"
				+ "DateID Integer,"
				+ "Content varchar(255),"
				+ "Sent Integer,"
				+ "FOREIGN KEY (UserID) REFERENCES Users(UserID),"
				+ "FOREIGN KEY (DateID) REFERENCES Date(DateID))";
		this.createTable("Users", UserTable);
		this.createTable("Date", DateTable);
		this.createTable("Messages", MessageTable);
	}
	
	public void createUser(String Pseudo,String IPadress) {
		try (Connection con = this.connect()){
			String query = "INSERT INTO Users(Pseudo,IPadress) VALUES ('"+Pseudo+"','"+IPadress+"');";
			PreparedStatement statement = con.prepareStatement(query);
			statement.executeUpdate();
		} catch(SQLException e) {
			System.err.println("Error at inserting in User table");
			System.err.println(e.getMessage());
		}
	}
	
	public void fetchUsers() {
		try (Connection con = this.connect()){
			String query = "SELECT * FROM Users;";
			Statement stmt  = con.createStatement();
	        ResultSet rs    = stmt.executeQuery(query);
	        while (rs.next()) {
	            System.out.println(rs.getInt("UserID") +  "\t" + 
	                               rs.getString("Pseudo") + "\t"+
	                               rs.getString("IPadress"));
	        }
		} catch(SQLException e) {
			System.err.println("Error at fetching users");
			System.err.println(e.getMessage());
		}
	}
	
	//This is for testing, do not use it otherwise!
	public void deleteUsers() {
		try (Connection con = this.connect()){
			String query = "DELETE FROM Users;";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			System.err.println("Error at removing Users");
			System.err.println(e.getMessage());
		}
	}
	
	
	//This is for the tests
	public void fetchInfoTable(String name) {
		try (Connection con = this.connect()){
			String query = "SELECT * FROM "+name+";";
			Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" + 
                                   rs.getString("name"));
            }
		} catch(SQLException e) {
			System.err.println("Error at fetching table");
			System.err.println(e.getMessage());
		}
	}
	
	//This is for the tests
	public void removeInfoFromTable(String name) {
		try (Connection con = this.connect()){
			String query = "DELETE FROM "+name+" WHERE id<0;";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			System.err.println("Error at removing in table");
			System.err.println(e.getMessage());
		}
	}
}
