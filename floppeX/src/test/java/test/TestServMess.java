package test;
import FloppaChat.Network.*;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class TestServMess {
	static ServMess SMess;
	@BeforeAll
	static void init() throws IOException {
		//MS = new MessageServer(ServSock);
		ServMess SMess = new ServMess(9696);
		SMess.run();
		System.out.println("Ending init");

	}
	
	@Test
	void testMessClientToServer() {
		while (true) {
			
		}
		
	}
	
	@Test
	void testMessServerToClient() {
		SMess.SendMessToClient("Hello there", "10.1.5.233");
		SMess.SendMessToClient("General Kenoni","10.1.5.233");
		assert(true);
	}
	
	
	

}
