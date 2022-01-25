package test;
import FloppaChat.Network.*;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestServMess {
	static ServConnections SMess;
	@BeforeAll
	static void init() throws IOException {
		//MS = new MessageServer(ServSock);
		ServConnections SMess = new ServConnections(9696);
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
		MessServSender.SendMessToClient("Hello there", "10.1.5.233");
		MessServSender.SendMessToClient("General Kenoni","10.1.5.233");
		assert(true);
	}
}