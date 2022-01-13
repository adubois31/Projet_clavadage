package FloppaChat.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import FloppaChat.DataBase.ActiveUserManager;
import FloppaChat.GUI.MainPageController;

public class Process {
	private static boolean HelloAccepted = true;
	private ActiveUserManager aUM;
	private MainPageController MPC = new MainPageController();
	
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
			processHello(Split_Answer[2],Split_Answer[1],packet,socket);
		}
		if (Split_Answer[0].equals("Hello_Back")) {
			System.out.println("Processing Hello_Back...\n");
			processHelloBack(Split_Answer[1],Split_Answer[2],Split_Answer[3]);
		}
		if ((Split_Answer[0].equals("Bingus"))) {
			processDisconnected(packet.getAddress().toString(),Split_Answer[1]);
		}
		if((Split_Answer[0].equals("ChangePseudo"))) {
			processChangePseudo(Split_Answer[1],packet.getAddress().toString(),Split_Answer[2] , packet,socket);
		}
		if((Split_Answer[0].equals("ChangePseudoAns"))) {
			processChangePseudoAns(Split_Answer[1],Split_Answer[2],packet.getAddress().toString().substring(1));
		}
		if((Split_Answer[0].equals("ConfirmedNewPseudo"))) {
			processConfirmedNewPseudo(Split_Answer[1],packet.getAddress().toString().substring(1),packet,socket);
		}
		if((Split_Answer[0].equals("ACK"))) {
			processAck(Split_Answer[1]);
		}
	}
	
	
	public void processHello(String SenderIP,String Pseudo,DatagramPacket packet,DatagramSocket socket) throws IOException,SocketException {
		if(aUM.CheckPseudoUnicity(Pseudo)){
			aUM.addActiveUser(SenderIP, Pseudo);
			MPC.showActiveUsers1();
			MPC.showActiveUsers2();
			byte[] out_buffer= Packet.HelloBack("Ok",aUM.getActiveUserPseudo("127.0.0.1"),NetInterface.GetIP()).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());

		}
		else {
			byte[] out_buffer= Packet.HelloBack("Not_Ok",aUM.getActiveUserPseudo("127.0.0.1"),NetInterface.GetIP()).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
		}
		aUM.PrintActiveUsers();
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public void processHelloBack(String Check,String SenderPseudo,String SenderIP) throws IOException {
		System.out.println("Sender Pseudo "+SenderPseudo);
		if(aUM.CheckPseudoUnicity(SenderPseudo)) {
			aUM.addActiveUser(SenderIP, SenderPseudo);	
		}
		if(Check.equals("Ok")) {
			
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
	
	public void processChangePseudo(String OldPseudo,String ClientIP,String NewPseudo,DatagramPacket packet,DatagramSocket socket) {
		if (aUM.CheckPseudoUnicity(NewPseudo)){
			byte[] out_buffer= Packet.ChangePseudoAns("New_OK",aUM.getActiveUserPseudo("127.0.0.1")).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
		}
		else {
			byte[] out_buffer= Packet.ChangePseudoAns("New_Not_OK",aUM.getActiveUserPseudo("127.0.0.1")).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
		}
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void processChangePseudoAns(String Check,String SenderPseudo, String SenderIP) {
		if(aUM.CheckPseudoUnicity(SenderPseudo)) {
			aUM.addActiveUser(SenderIP, SenderPseudo);

		}
		if(Check.equals("New_OK")) {
			ChangePseudoAccepted = ChangePseudoAccepted && true;
		}
		else {
			ChangePseudoAccepted = ChangePseudoAccepted && false;
		}
	}
	
	public void processConfirmedNewPseudo(String NewPseudo, String SenderIP, DatagramPacket packet,DatagramSocket socket) throws IOException {
		aUM.UpdateActiveUserPseudo(SenderIP, NewPseudo);
		aUM.PrintActiveUsers();
		MPC.showActiveUsers1();
		MPC.showActiveUsers2();
		byte[] out_buffer= Packet.Ack(NewPseudo).getBytes();
		packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void processAck(String NewPseudo) {
		aUM.UpdateActiveUserPseudo("127.0.0.1", NewPseudo);
		aUM.PrintActiveUsers();
	}
	
	

}
