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
		Socket socket = new Socket("10.1.5.155",2023);
		MC = new MessageClient(socket);
 		System.out.println("Ending init");

	}
	@Test
	void testMessClientToServer() {
		System.out.println("Message envoyé : Coucou Hibou");
    	MC.SendMessToServer("Coucou Hibou");
    	MC.SendMessToServer("Re Coucou Hibou");
		assert(true);
		
	}
	
	@Test
	void testMessServerToClient() {
		MC.RecvMessFromServer();
	}
	

}
