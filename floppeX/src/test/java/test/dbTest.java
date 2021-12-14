package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import DataBase.DBController;
import GUI.BlobCreator;

public class dbTest {
	
	static DBController db;
	static String tableName;
	static Random rn;
	
	@BeforeAll
	static void init() {
		db = new DBController("test");
		tableName = "testTable";
		rn = new Random();
	}
	
	@Test
	void testCreateTable() {
		this.db.createTable(tableName, "(id int,name varchar(255))");
		assert(true);
	}

	@Test
	void testInsertIntoTable() {
		int id = rn.nextInt();
		String name = "Viktor";
		this.db.insertIntoTable(tableName, "("+id+",'"+name+"');");
		assert(true);
	}
	
	@Test
	void testFetchInfo() {
		this.db.fetchInfoTable(tableName);
		assert(true);
	}
	
	@Test
	void testDeleteFromTable() {
		this.db.removeInfoFromTable(tableName);
		this.db.fetchInfoTable(tableName);
		assert(true);
	}
	
	@Test
	void testCreateRealDB() {
		this.db.initDatabase();
		assert(true);
	}
	
	@Test
	void testFetchUsers() {
		this.db.initDatabase();
		this.db.deleteUsers();
		this.db.createUser("Viktor","69.69.69.69");
		this.db.createUser("Aubry","96.96.96.96");
		this.db.fetchUsers();
		assert(true);
	}
}