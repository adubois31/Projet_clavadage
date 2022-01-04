package test;

import FloppaChat.DataBase.ActiveUserManager;
import FloppaChat.Network.*;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestBroadcastServer {

	static ActiveUserManager aUM= new ActiveUserManager();
	
	@Test
	void TestServer() throws UnknownHostException, IOException {
		aUM.InitActiveUser("Floppa");
		aUM.addActiveUser("192.169.1.1", "Pedro");
		aUM.UpdateActiveUserPseudo("192.169.1.1", "Michel");
		aUM.PrintActiveUsers();
		BroadcastServer Serv = new BroadcastServer();
        Serv.run();
	}
	
}
