package FloppaChat.DataBase;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class DBController {
	
	private String dbName;
	
	public DBController(String name) {
		try {
			Class.forName("org.sqlite.JDBC");
			this.dbName = name;
			File dbfile = new File("src/main/resources/sqlite/db/"+this.dbName+".db");
			if(dbfile.createNewFile()) {
				System.out.println("File created: " + dbfile.getName());
			initDatabase();
		} catch(Exception e) {
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
				"(UserID Integer PRIMARY KEY,"
				+ "Pseudo TEXT NOT NULL,"
				+ "IPadress TEXT NOT NULL,"
				+ "UNIQUE(Pseudo,IPadress))";
		
		String MessageTable =
				"(MessageID Integer PRIMARY KEY,"
				+ "UserID Integer NOT NULL,"
				+ "Date TEXT,"
				+ "Content TEXT,"
				+ "Sent Integer,"
				+ "FOREIGN KEY (UserID) REFERENCES Users(UserID))";
		
		this.createTable("Users", UserTable);
		this.createTable("Messages", MessageTable);
	}
	
	public void createUser(String Pseudo,String IPadress) {
		try (Connection con = this.connect()){	
			String createQuery = "INSERT INTO Users(Pseudo,IPadress) VALUES ('"+Pseudo+"','"+IPadress+"');";
			PreparedStatement statement = con.prepareStatement(createQuery);
			statement.executeUpdate();
		} catch(SQLException e) {
			System.err.println("Error at inserting in User table");
			System.err.println(e.getMessage());
		}
	}
	
	public void addMessage(int UserID,String Date,String content,boolean sent) {
		int actualSent = 0;
		if(sent) actualSent=1;
		try (Connection con = this.connect()){
			String query = "INSERT INTO Messages(UserID,Date,Content,Sent) VALUES ("
					+ "'"+UserID+"','"+Date+"','"+content+"','"+actualSent+"');";
			PreparedStatement statement = con.prepareStatement(query);
			statement.executeUpdate();
		} catch(SQLException e) {
			System.err.println("Error at inserting in Messages table");
			System.err.println(e.getMessage());
		}
	}
	
	public ArrayList<Message> fetchMessagesWithUser(int UserID) {
		ArrayList<Message> listMessages = new ArrayList<Message>();
		try (Connection con = this.connect()){
			String query = "SELECT * FROM Messages WHERE UserID=\""+UserID+"\";";
			Statement stmt  = con.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) 
	            listMessages.add(new Message(rs.getString("Date"), UserID, rs.getString("Content"), rs.getBoolean("Sent")));
		} catch(SQLException e) {
			System.err.println("Error at fetching message");
			System.err.println(e.getMessage());
		}
		return listMessages;
	}
	
	public int getIDfromUser(String pseudo,String IP) {
		int result = -1;
		try (Connection con = this.connect()){
			String query = "SELECT UserID FROM Users WHERE Pseudo=\""+pseudo+"\" AND IPadress=\""+IP+"\";";
			Statement stmt  = con.createStatement();
	        ResultSet rs    = stmt.executeQuery(query);
	        while (rs.next()) {
	        	result = rs.getInt("UserID");
	        	break;
	        }
		} catch(SQLException e) {
			System.err.println("Error at getting ID of User");
			System.err.println(e.getMessage());
		}
		return result;
	}
	
	public void changePseudo(String newPseudo,int ID) {
		try (Connection con = this.connect()){
			String query = "UPDATE Users SET Pseudo = '"+newPseudo+"'WHERE UserID="+ID+";";
			Statement stmt  = con.createStatement();
	        stmt.executeUpdate(query);
		} catch(SQLException e) {
			System.err.println("Error at updating pseudo of user");
			System.err.println(e.getMessage());
		}
	}
	
	//This is for testing, do not use it otherwise!
	public void deleteAllTables() {
		try (Connection con = this.connect()){
			String query = "DROP TABLE 'Users';"
					//+ "UPDATE Messages SET UserID = NULL;"
					+ "DROP TABLE 'Messages';";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(query);
		}catch(SQLException e) {
			System.err.println("Error at deleting tables");
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
	
	//Testing purpose
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
}
