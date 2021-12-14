package FloppaChat.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import FloppaChat.DataBase.ActiveUser_Manager;

public class Process {
	private static boolean HelloAccepted = true;
	
	public static boolean getHelloAccepted() {
		return HelloAccepted;
	}
	public static void SetHelloAccepted(boolean Val) {
		HelloAccepted=Val;
	}
	ActiveUser_Manager AU_M = new ActiveUser_Manager();
	
	public void BroadcastProcess(DatagramPacket packet,DatagramSocket socket) throws IOException {
		System.out.println("Processing Packet...\n");
		String received= new String(packet.getData(), 0, packet.getLength());
		String[] Split_Answer = received.split("\\|");
		System.out.println("Flag : "+Split_Answer[0]+" \n");
		if ((Split_Answer[0]).equals("Hello")) {
			System.out.println("Processing Hello...\n");
			processHello((packet.getAddress()).toString().substring(1),Split_Answer[1],packet,socket);
		}
		if (Split_Answer[0].equals("Hello_Back")) {
			System.out.println("Processing Hello_Back...\n");
			processHelloBack(Split_Answer[1],Split_Answer[2],packet.getAddress().toString().substring(1));
		}
		if ((Split_Answer[0].equals("Bingus"))) {
			processDisconnected(packet.getAddress().toString(),Split_Answer[1]);
		}
		if((Split_Answer[0].equals("ChangePseudo"))) {
			processChangePseudo(Split_Answer[1],packet.getAddress().toString(),Split_Answer[2] , packet);
		}
		if((Split_Answer[0].equals("ChangePseudoAns"))) {
			processChangePseudoAns(Split_Answer[1],Split_Answer[2],packet.getAddress().toString());
		}
	}
	
	
	public void processHello(String IP,String Pseudo,DatagramPacket packet,DatagramSocket socket) {
		System.out.println(Pseudo+AU_M.CheckPseudoUnicity(Pseudo));
		ActiveUser_Manager activeUser_Manager = new ActiveUser_Manager();
		AU_M.PrintActiveUsers();
		if(AU_M.CheckPseudoUnicity(Pseudo)){
			AU_M.addActiveUser(IP, Pseudo);
			System.out.println("Pseudo OK\n");
			byte[] out_buffer= Packet.HelloBack("Ok",AU_M.getActiveUser_Pseudo("127.0.0.1")).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
			System.out.println("Sending Hello_Back\n");
		}
		else {
			System.out.println("Pseudo taken\n");
			byte[] out_buffer= Packet.HelloBack("Not_Ok",AU_M.getActiveUser_Pseudo("127.0.0.1")).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
			System.out.println("Pseudo already taken\n");
		}
		AU_M.PrintActiveUsers();
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public void processHelloBack(String Check,String SenderPseudo,String SenderIP) throws IOException {
		if(AU_M.CheckPseudoUnicity(SenderPseudo)) {
			AU_M.addActiveUser(SenderIP, SenderPseudo);	
		}
		AU_M.PrintActiveUsers();
		if(Check.equals("Ok")) {
			//displaying active user and chat page 
			
			HelloAccepted = HelloAccepted && true;

		}
		else {
			HelloAccepted = HelloAccepted && false;
		}
		
	}
	public boolean processDisconnected(String DiscIP,String DiscPseudo) {
		
		if(!(AU_M.CheckPseudoUnicity(DiscPseudo))){
			AU_M.removeActiveUser(DiscIP, DiscPseudo);
		}
		return true;
		
	}
	
	public void processChangePseudo(String OldPseudo,String IP,String NewPseudo,DatagramPacket packet) {
		if (AU_M.CheckPseudoUnicity(OldPseudo)&&AU_M.CheckPseudoUnicity(NewPseudo)){
			//Database_Manager.addActiveUser(IP,NewPseudo);
			byte[] out_buffer= Packet.ChangePseudoAns("New_OK",AU_M.getActiveUser_Pseudo("127.0.0.1")).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
		}
		if(!(AU_M.CheckPseudoUnicity(OldPseudo))&&AU_M.CheckPseudoUnicity(NewPseudo)) {
			//Database_Manager.UpdateActive_User_Pseudo(IP, NewPseudo);
			byte[] out_buffer= Packet.ChangePseudoAns("New_OK",AU_M.getActiveUser_Pseudo("127.0.0.1")).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
		}
		else {
			byte[] out_buffer= Packet.ChangePseudoAns("New_Not_OK",AU_M.getActiveUser_Pseudo("127.0.0.1")).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
			System.out.println("Pseudo Déjà Pris");
		}
	}
	
	public void processChangePseudoAns(String Check,String SenderPseudo, String SenderIP) {
		if(AU_M.CheckPseudoUnicity(SenderPseudo)) {
			AU_M.addActiveUser(SenderIP, SenderPseudo);
		}
		if(Check=="New_OK") {
			System.out.println("Nouveau Pseudo OK");
		}
		System.out.println("Nouveau Pseudo  PAS OK");
	}
	
	

}
