package test;

import FloppaChat.DataBase.ActiveUserManager;
import FloppaChat.Network.*;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestBroadcastServer {

	static ActiveUserManager aUM= new ActiveUserManager();
	@Test
	void TestServer() throws UnknownHostException, IOException {
		aUM.addActiveUser("192.169.1.1", "Pedro");
		aUM.PrintActiveUsers();
		BroadcastServer Serv = new BroadcastServer(6968);
        Serv.start();
        while(true) {
        	
        }        
	}
	

	
}
