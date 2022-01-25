package FloppaChat.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import FloppaChat.DataBase.ActiveUserManager;
import FloppaChat.GUI.Global;

public class Process {
	private static boolean HelloAccepted = true;
	private ActiveUserManager aUM;

	private static boolean ChangePseudoAccepted =true;

	public static boolean getChangePseudoAccepted() {
		return ChangePseudoAccepted;
	}
	public static void setChangePseudoAccepted(boolean Val) {
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
		String Flag = Split_Answer[0];
		String pseudoSubject1 = Split_Answer[1];
		String pseudoSubject2 = Split_Answer[2];
		String IPSubject = packet.getAddress().toString().substring(1);
		System.out.println("Flag : "+Flag+" \n");
		
		switch(Flag) {
		
		case "Hello":
			System.out.println("Processing Hello...\n");
			processHello(IPSubject,pseudoSubject1,packet,socket);
			break;
		
		case "Hello_Back":
			System.out.println("Processing Hello_Back...\n");
			processHelloBack(pseudoSubject1,pseudoSubject2,IPSubject);
			break;
		
		case "Bingus":
			if(!Global.userPseudo.equals(pseudoSubject1)) {
				System.out.println("Processing Bingus...\n");
				processDisconnected(IPSubject,pseudoSubject1);
			}
			break;
		
		case "ChangePseudo":
			processChangePseudo(pseudoSubject1,IPSubject,pseudoSubject2,packet,socket);
			break;
		
		case "ChangePseudoAns":
			processChangePseudoAns(pseudoSubject1,pseudoSubject2,IPSubject);
			break;
		
		case "ConfirmedNewPseudo":
			processConfirmedNewPseudo(pseudoSubject1,IPSubject,packet,socket);
		
		case "ACK":
			processAck(pseudoSubject1);
			break;
		}
	}

	public void processHello(String SenderIP,String Pseudo,DatagramPacket packet,DatagramSocket socket) throws IOException,SocketException {
		if(aUM.CheckPseudoUnicity(Pseudo)){
			aUM.addActiveUser(SenderIP, Pseudo);
			byte[] out_buffer= Packet.HelloBack("Ok",Global.userPseudo).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
		} else {
			byte[] out_buffer= Packet.HelloBack("Not_Ok",Global.userPseudo).getBytes();
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
	
	public void processDisconnected(String DiscIP,String DiscPseudo) {
		System.out.println("Processing Disconnection of "+DiscPseudo+" IP : "+DiscIP);
		if(!(aUM.CheckPseudoUnicity(DiscPseudo))){
			aUM.removeActiveUser(DiscIP, DiscPseudo);
			System.out.println(DiscPseudo+" removed from active users");
			aUM.PrintActiveUsers();
		}
		for(MessServWorker Worker : ServConnections.ClientList) {
			if (Worker.ClientIP().equals(DiscIP)) {
				Worker.interrupt();
				ServConnections.ClientList.remove(Worker);
				break;
			}	
		}
		ServConnections.PrintClientList();
		for (MessageClient Client : MultiClientConnections.ClientConnections) {
			if(Client.getRemoteIP().equals(DiscIP)) {
				Client.EndChat();
				MultiClientConnections.removeClient(Client);
				break;
			}
		}
		MultiClientConnections.PrintingClientConnections();
		System.out.println("Ending disconnection of "+DiscPseudo);
	}

	public void processChangePseudo(String OldPseudo,String ClientIP,String NewPseudo,DatagramPacket packet,DatagramSocket socket) {
		if (aUM.CheckPseudoUnicity(NewPseudo)){
			byte[] out_buffer= Packet.ChangePseudoAns("New_OK",Global.userPseudo).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
		}
		else {
			byte[] out_buffer= Packet.ChangePseudoAns("New_Not_OK",Global.userPseudo).getBytes();
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
		byte[] out_buffer= Packet.Ack(NewPseudo).getBytes();
		packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void processAck(String NewPseudo) {
		Global.userPseudo = NewPseudo;
		aUM.PrintActiveUsers();
	}
}