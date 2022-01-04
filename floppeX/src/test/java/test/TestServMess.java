package test;
import FloppaChat.Network.*;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;




public class TestServMess {
	static MessageServer MS;
	@BeforeAll
	static void init() throws IOException {
		System.out.println("Creating Server Socket");
		ServerSocket ServSock = new ServerSocket(2023);
		MS = new MessageServer(ServSock);
		System.out.println("Ending init");

	}
	
	@Test
	void testMessClientToServer() {
		MS.RecvMessFromClient();
		assert(true);
		
	}
	
	@Test
	void testMessServerToClient() {
		MS.SendMessToClient("Hello there");
		MS.SendMessToClient("General Kenoni");
		assert(true);
	}
	
	
	

}
