package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import FloppaChat.DataBase.ActiveUserManager;

public class ActiveUserManagerTest {
	static ActiveUserManager AUM;

	@BeforeAll
	static void init() {
		AUM = new ActiveUserManager();
	}
	
	@Test
	static void testAddUser() {
		AUM.addActiveUser("69.69", "Paul");
		assert(ActiveUserManager.Act_User_List.size()==1);
	}
	
	@Test
	static void testDeleteUser() {
		testAddUser();
		AUM.removeActiveUser("69.69", "Paul");
		assert(ActiveUserManager.Act_User_List.size()==0);
	}
	
	@Test
	static void testUpdateUser() {
		testAddUser();
		AUM.UpdateActiveUserPseudo("69.69", "Hugo");
		assert(ActiveUserManager.Act_User_List.stream().anyMatch(Au -> Au.getPseudo().equals("Hugo")));
		assert(!ActiveUserManager.Act_User_List.stream().anyMatch(Au -> Au.getPseudo().equals("Paul")));
	}
	
	@AfterAll
	static void end() {
		AUM.Act_User_List = null;
	}
}
