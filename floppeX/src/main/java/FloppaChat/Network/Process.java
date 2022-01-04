package FloppaChat.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import FloppaChat.DataBase.ActiveUserManager;

public class Process {
	private static boolean HelloAccepted = true;
	private ActiveUserManager aUM;
	
	private static boolean ChangePseudoAccepted =true;
	
	public static boolean getChangePseudoAccepted() {
		return ChangePseudoAccepted;
	}
	public static  void setChangePseudoAccepted(boolean Val) {
		ChangePseudoAccepted=Val;
	}
	
	public static boolean getHelloAccepted() {
		return HelloAccepted;
	}
	public static void SetHelloAccepted(boolean Val) {
		HelloAccepted=Val;
	}
	
	public Process() {
		this.aUM = new ActiveUserManager();
	}
	
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
		if((Split_Answer[0].equals("ConfirmedNewPseudo"))) {
			processConfirmedNewPseudo(Split_Answer[1],packet.getAddress().toString());
		}
	}
	
	
	public void processHello(String IP,String Pseudo,DatagramPacket packet,DatagramSocket socket) {

		System.out.println(Pseudo+aUM.CheckPseudoUnicity(Pseudo));
		aUM.PrintActiveUsers();
		if(aUM.CheckPseudoUnicity(Pseudo)){
			aUM.addActiveUser(IP, Pseudo);
			System.out.println("Pseudo OK\n");
			byte[] out_buffer= Packet.HelloBack("Ok",aUM.getActiveUserPseudo("127.0.0.1")).getBytes();

			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
			System.out.println("Sending Hello_Back\n");
		}
		else {
			System.out.println("Pseudo taken\n");

			byte[] out_buffer= Packet.HelloBack("Not_Ok",aUM.getActiveUserPseudo("127.0.0.1")).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
			System.out.println("Pseudo already taken\n");
		}
		aUM.PrintActiveUsers();
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public void processHelloBack(String Check,String SenderPseudo,String SenderIP) throws IOException {

		if(aUM.CheckPseudoUnicity(SenderPseudo)) {
			aUM.addActiveUser(SenderIP, SenderPseudo);	
		}
		aUM.PrintActiveUsers();
		if(Check.equals("Ok")) {
			//displaying active user and chat page 
			
			HelloAccepted = HelloAccepted && true;

		}
		else {
			HelloAccepted = HelloAccepted && false;
		}
		
	}
	public boolean processDisconnected(String DiscIP,String DiscPseudo) {
		

		if(!(aUM.CheckPseudoUnicity(DiscPseudo))){
			aUM.removeActiveUser(DiscIP, DiscPseudo);
		}
		return true;
		
	}
	
	public void processChangePseudo(String OldPseudo,String IP,String NewPseudo,DatagramPacket packet) {

		if (aUM.CheckPseudoUnicity(NewPseudo)){
			byte[] out_buffer= Packet.ChangePseudoAns("New_OK",aUM.getActiveUserPseudo("127.0.0.1")).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
		}
		else {
			byte[] out_buffer= Packet.ChangePseudoAns("New_Not_OK",aUM.getActiveUserPseudo("127.0.0.1")).getBytes();

			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
			System.out.println("Pseudo Déjà Pris");
		}
	}
	
	public void processChangePseudoAns(String Check,String SenderPseudo, String SenderIP) {

		if(aUM.CheckPseudoUnicity(SenderPseudo)) {
			aUM.addActiveUser(SenderIP, SenderPseudo);

		}
		if(Check=="New_OK") {
			ChangePseudoAccepted = ChangePseudoAccepted && true;
		}
		else {
			ChangePseudoAccepted = ChangePseudoAccepted && false;
		}
	}
	
	public void processConfirmedNewPseudo(String NewPseudo, String SenderIP) {
		aUM.UpdateActiveUserPseudo(SenderIP, NewPseudo);
	}
	
	

}
