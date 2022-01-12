package test;
import FloppaChat.Network.*;

import java.io.IOException;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.Socket;
public class TestClientMess {
	static MessageClient MC;
	@BeforeAll
	static void init() throws IOException {
	
		System.out.println("Creating Client Socket");

		Socket socket = new Socket("10.1.5.233",9696);
		MC = new MessageClient(socket);
 		System.out.println("Ending init");

	}
	@Test
	void testMessClientToServer() {
		System.out.println("Message envoyé : Coucou Hibou");
    	MC.SendMessToServer("Coucou Hibou");
    	long start = System.currentTimeMillis();
		long end = start + 7*1000;
		while (System.currentTimeMillis() < end) {
		    
		}
    	MC.SendMessToServer("2ème mess");
		assert(true);
		
	}
	
	@Test
	void testMessServerToClient() {
		MC.SendMessToServer("Holla");
		MC.RecvMessFromServer();
		while (true) {
			
		}
		

	}
	

}
