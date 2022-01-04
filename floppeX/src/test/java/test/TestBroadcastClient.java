package test;

import FloppaChat.DataBase.ActiveUserManager;
import FloppaChat.Network.NetInterface;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class TestBroadcastClient {
	@Test
	void TestChoosePseudo() throws UnknownHostException, IOException {
		if(NetInterface.ChoosePseudo("Bri")) {
			assert(true);
		}
		else {
			assert(false);
		}
		
		
		
		
	}

}
