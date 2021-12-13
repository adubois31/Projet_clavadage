package FloppaChat.floppeX;

import java.net.DatagramPacket;

public class Process {
	
	public static void BroadcastProcess(DatagramPacket packet) {
		String received= new String(packet.getData(), 0, packet.getLength());
		String[] Split_Answer = received.split("\\|");
		if ((Split_Answer[0]=="Hello") && (Split_Answer[0]!= null)) {
			processHello(packet.getAddress().toString(),Split_Answer[1],packet);
		}
		if ((Split_Answer[0]=="Hello_Back") && (Split_Answer[0]!= null)) {
			processHelloBack(Split_Answer[1],Split_Answer[2],packet.getAddress().toString());
		}
		if ((Split_Answer[0]=="Bingus") && (Split_Answer[0]!= null)) {
			processDisconnected(packet.getAddress().toString(),Split_Answer[1]);
		}
		if((Split_Answer[0]=="ChangePseudo") && (Split_Answer[0]!= null)) {
			processChangePseudo(Split_Answer[1],packet.getAddress().toString(),Split_Answer[2] , packet);
		}
		if((Split_Answer[0]=="ChangePseudoAns") && (Split_Answer[0]!= null)) {
			processChangePseudoAns(Split_Answer[1],Split_Answer[2],packet.getAddress().toString());
		}
	}
	
	
	public static void processHello(String IP,String Pseudo,DatagramPacket packet) {
		if(Database_Manager.CheckPseudoUnicity(Pseudo)){
			Database_Manager.addActiveUser(IP, Pseudo);
			System.out.println("Pseudo OK");
			byte[] out_buffer= Packet.HelloBack("Ok",Database_Manager.getActiveUser_Pseudo("127.0.0.1")).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
		}
		else {
			byte[] out_buffer= Packet.HelloBack("Not_Ok",Database_Manager.getActiveUser_Pseudo("127.0.0.1")).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
			System.out.println("Pseudo already taken");
		}

	}
	
	
	public static void processHelloBack(String Check,String SenderPseudo,String SenderIP) {
		if(Database_Manager.CheckPseudoUnicity(SenderPseudo)) {
			Database_Manager.addActiveUser(SenderIP, SenderPseudo);	
		}
		if(Check=="Ok") {
			System.out.println("Pseudo accepté");

		}
		else {
			System.out.println("Pseudo pas accepté");
		}
		
	}
	public static boolean processDisconnected(String DiscIP,String DiscPseudo) {
		
		if(!(Database_Manager.CheckPseudoUnicity(DiscPseudo))){
			Database_Manager.removeActiveUser(DiscIP, DiscPseudo);
		}
		return true;
		
	}
	
	public static void processChangePseudo(String OldPseudo,String IP,String NewPseudo,DatagramPacket packet) {
		if (Database_Manager.CheckPseudoUnicity(OldPseudo)&&Database_Manager.CheckPseudoUnicity(NewPseudo)){
			Database_Manager.addActiveUser(IP,NewPseudo);
			byte[] out_buffer= Packet.ChangePseudoAns("New_OK",Database_Manager.getActiveUser_Pseudo("127.0.0.1")).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
		}
		if(!(Database_Manager.CheckPseudoUnicity(OldPseudo))&&Database_Manager.CheckPseudoUnicity(NewPseudo)) {
			Database_Manager.UpdateActive_User_Pseudo(IP, NewPseudo);
			byte[] out_buffer= Packet.ChangePseudoAns("New_OK",Database_Manager.getActiveUser_Pseudo("127.0.0.1")).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
		}
		else {
			byte[] out_buffer= Packet.ChangePseudoAns("New_Not_OK",Database_Manager.getActiveUser_Pseudo("127.0.0.1")).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
			System.out.println("Pseudo Déjà Pris");
		}
	}
	
	public static void processChangePseudoAns(String Check,String SenderPseudo, String SenderIP) {
		if(Database_Manager.CheckPseudoUnicity(SenderPseudo)) {
			Database_Manager.addActiveUser(SenderIP, SenderPseudo);
		}
		if(Check=="New_OK") {
			System.out.println("Nouveau Pseudo OK");
		}
		System.out.println("Nouveau Pseudo  PAS OK");
	}
	
	

}
