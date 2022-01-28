package test;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import FloppaChat.DataBase.DBController;
import FloppaChat.DataBase.Message;
import FloppaChat.GUI.MainPageController;
import FloppaChat.GUI.Global;

public class dbTest {
	
	//This is tests that make sure that the database call works. It does not automatically prove that the calls do the right thing.
	
	static DBController db;
	static String tableName;
	static Random rn;
	static String dbpath;
	static MainPageController mpControl;
	
	@BeforeAll
	static void init() {
		System.out.println("Initializing database");
		db = new DBController("test");
		tableName = "testTable";
		rn = new Random();
		dbpath = "src/main/resources/sqlite/db/test.db";
		mpControl = new MainPageController();
		db.deleteAllTables();
		db.initDatabase();
	}
	
	@Test
	void testCreateTable() {
		db.createTable(tableName, "(id int,name varchar(255))");
		assert(true);
	}

	@Test
	void testInsertIntoTable() {
		int id = rn.nextInt();
		String name = "Viktor";
		db.insertIntoTable(tableName, "("+id+",'"+name+"');");
		assert(true);
	}
	
	@Test
	void testFetchInfo() {
		db.fetchInfoTable(tableName);
		assert(true);
	}
	
	@Test
	void testDeleteFromTable() {
		db.removeInfoFromTable(tableName);
		db.fetchInfoTable(tableName);
		assert(true);
	}
	
	@Test
	void testCreateRealDB() {
		db.initDatabase();
		assert(true);
	}
	
	@Test
	void testFetchUsers() {
		db.createUser("Viktor","69.69.69.69");
		db.createUser("Viktor","68.69.69.69");
		db.createUser("Viktor","69.69.69.69");
		db.createUser("Aubry","96.96.96.96");
		db.fetchUsers();
		assert(true);
	}
	
	@Test
	void testDeleteTables() {
		db.deleteAllTables();
		assert(true);
	}
	
	@Test
	void testFetchMessages() {
		db.createUser("Aubry","96.96.96.96");
		int ID = db.getIDfromUser("Aubry","96.96.96.96");
		db.addMessage(ID,Global.nowDate(),"Hey Salut", true);
		db.addMessage(ID,Global.nowDate(),"En fait non", false);
		db.addMessage(ID,Global.nowDate(),"Ah", true);
		for(Message m : db.fetchMessagesWithUser(ID)) {
			System.out.println(m);
		}
	}
	
	@Test
	void testUpdatePseudo() {
		db.createUser("Viktor","69.69.69.69");
		db.createUser("Aubry","96.96.96.96");
		db.fetchUsers();
		db.changePseudo("Aubrydu69", db.getIDfromUser("Aubry", "96.96.96.96"));
		db.fetchUsers();
	}
	
	@Test
	void createfileTest() throws IOException{
		File dbfile = new File(dbpath);
		if(dbfile.createNewFile()) {
			System.out.println("File created: " + dbfile.getName());
		} else {
			System.out.println("File already exists");
		}
		assert(true);
	}
}