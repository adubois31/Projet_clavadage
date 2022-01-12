package test;

import FloppaChat.DataBase.ActiveUserManager;
import FloppaChat.Network.NetInterface;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class TestBroadcastClient {
	static ActiveUserManager aUM= new ActiveUserManager();
	@Test
	void TestChoosePseudo() throws UnknownHostException, IOException {
		if(NetInterface.ChoosePseudo("Billy")) {
			assert(true);
		}
		else {
			assert(false);
		}
	}
	
	@Test
	void TestChangePseudo() throws UnknownHostException, IOException {
		aUM.InitActiveUser("Bri");
		if (NetInterface.ChangePseudo("Bri", "Billy")) {
			assert(true);
		}
		else {
			assert(false);
		}
		
	}

}
