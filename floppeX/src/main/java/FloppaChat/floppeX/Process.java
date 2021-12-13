package FloppaChat.floppeX;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Process {
	
	public static void BroadcastProcess(DatagramPacket packet,DatagramSocket socket) {
		System.out.println("Processing Packet...\n");
		String received= new String(packet.getData(), 0, packet.getLength());
		String[] Split_Answer = received.split("\\|");
		System.out.println("Flag : "+Split_Answer[0]+" \n");
		if ((Split_Answer[0]).equals("Hello")) {
			System.out.println("Processing Hello...\n");
			processHello(packet.getAddress().toString(),Split_Answer[1],packet,socket);
		}
		if (Split_Answer[0].equals("Hello_Back")) {
			System.out.println("Processing Hello_Back...\n");
			processHelloBack(Split_Answer[1],Split_Answer[2],packet.getAddress().toString());
		}
		if ((Split_Answer[0]=="Bingus")) {
			processDisconnected(packet.getAddress().toString(),Split_Answer[1]);
		}
		if((Split_Answer[0]=="ChangePseudo")) {
			processChangePseudo(Split_Answer[1],packet.getAddress().toString(),Split_Answer[2] , packet);
		}
		if((Split_Answer[0]=="ChangePseudoAns")) {
			processChangePseudoAns(Split_Answer[1],Split_Answer[2],packet.getAddress().toString());
		}
	}
	
	
	public static void processHello(String IP,String Pseudo,DatagramPacket packet,DatagramSocket socket) {
		Database_Manager.PrintActiveUsers();
		System.out.println("Pseudo reçu : "+Pseudo+"IP Source: "+IP+"\n");
		if(Database_Manager.CheckPseudoUnicity(Pseudo)){
			Database_Manager.addActiveUser(IP, Pseudo);
			System.out.println("Pseudo OK\n");
			byte[] out_buffer= Packet.HelloBack("Ok",Database_Manager.getActiveUser_Pseudo("127.0.0.1")).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
			System.out.println("Sending Hello_Back\n");
		}
		else {
			System.out.println("Pseudo taken\n");
			byte[] out_buffer= Packet.HelloBack("Not_Ok",Database_Manager.getActiveUser_Pseudo("127.0.0.1")).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
			System.out.println("Pseudo already taken\ns");
		}
		Database_Manager.PrintActiveUsers();
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public static void processHelloBack(String Check,String SenderPseudo,String SenderIP) {
		if(Database_Manager.CheckPseudoUnicity(SenderPseudo)) {
			Database_Manager.addActiveUser(SenderIP, SenderPseudo);	
		}
		Database_Manager.PrintActiveUsers();
		if(Check.equals("Ok")) {
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
