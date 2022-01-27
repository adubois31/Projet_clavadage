package FloppaChat.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Iterator;

import FloppaChat.DataBase.ActiveUserManager;
import FloppaChat.DataBase.DBController;
import FloppaChat.GUI.Global;

public class Process {
	private static boolean HelloAccepted = true;
	private ActiveUserManager aUM;
	
	//Variable used to check if 
	//all the users answering a change of pseudo request are ok with it
	private static boolean ChangePseudoAccepted =true;

	public static boolean getChangePseudoAccepted() {
		return ChangePseudoAccepted;
	}
	public static void setChangePseudoAccepted(boolean Val) {
		ChangePseudoAccepted=Val;
	}

	//Variable used to check if
	//all the users accept our pseudo as new user
	public static boolean getHelloAccepted() {
		return HelloAccepted;
	}
	public static void SetHelloAccepted(boolean Val) {
		HelloAccepted=Val;
	}
	//our constructor creating an instance of ActiveUserManager
	public Process() {
		this.aUM = new ActiveUserManager();
	}
	//method charged of extracting the relevant information of the received broadcast packet
	//then depending on the flag, calls the appropriate method
	public void BroadcastProcess(DatagramPacket packet,DatagramSocket socket) throws IOException {
		System.out.println("Processing Packet...\n");
		String received= new String(packet.getData(), 0, packet.getLength());
		String[] Split_Answer = received.split("\\|");
		String Flag = Split_Answer[0];
		String pseudoSubject1 = Split_Answer[1];
		String pseudoSubject2="";
		if(Split_Answer.length>2)
			pseudoSubject2=Split_Answer[2];
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
			break;
		
		case "ACK":
			processAck(pseudoSubject1);
			break;
		}
	}
	
	//processes a hello packet
	public void processHello(String SenderIP,String Pseudo,DatagramPacket packet,DatagramSocket socket) throws IOException,SocketException {
		//checks if the pseudo is free, if it's the case it adds it to our active user list
		//then send a broadcast packet to say that the pseudo if free for us
		if(aUM.CheckPseudoUnicity(Pseudo)){
			aUM.addActiveUser(SenderIP, Pseudo);
			byte[] out_buffer= Packet.HelloBack("Ok",Global.userPseudo).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
		//if the pseudo is taken sends that this pseudo is not Ok
		} else {
			byte[] out_buffer= Packet.HelloBack("Not_Ok",Global.userPseudo).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
		}
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//processes HelloBack packets
	public void processHelloBack(String Check,String SenderPseudo,String SenderIP) throws IOException {
		//if the user answering our query for a new pseudo is not know we add him
		if(aUM.CheckPseudoUnicity(SenderPseudo)) {
			aUM.addActiveUser(SenderIP, SenderPseudo);	
		}
		if(Check.equals("Ok")) {
			HelloAccepted = HelloAccepted && true;
		}
		//if the answer if not ok HelloAccepted becomes false
		else {
			HelloAccepted = HelloAccepted && false;
		}

	}
	

	public void processChangePseudo(String OldPseudo,String ClientIP,String NewPseudo,DatagramPacket packet,DatagramSocket socket) {
		//checks if the new pseudo is free to send a packet confirming that it's not taken
		if (aUM.CheckPseudoUnicity(NewPseudo)){
			byte[] out_buffer= Packet.ChangePseudoAns("New_OK",Global.userPseudo).getBytes();
			packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
		}
		//if we have already taken the new pseudo we send that's not ok
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

	//processing the answers of our pseudo changing query
	public void processChangePseudoAns(String Check,String SenderPseudo, String SenderIP) {
		if(aUM.CheckPseudoUnicity(SenderPseudo)) {
			aUM.addActiveUser(SenderIP, SenderPseudo);
		}
		if(Check.equals("New_OK")) {
			ChangePseudoAccepted = ChangePseudoAccepted && true;
		}
		//if the new pseudo is taken then it's false
		else {
			ChangePseudoAccepted = ChangePseudoAccepted && false;
		}
	}

	//process a new pseudo that is confirmed
	public void processConfirmedNewPseudo(String NewPseudo, String SenderIP, DatagramPacket packet,DatagramSocket socket) throws IOException {
		DBController db = new DBController(Global.dbName);
		//updating the pseudo of the sender
		db.changePseudo(NewPseudo,db.getIDfromUser(aUM.getActiveUserPseudo(SenderIP), SenderIP));
		aUM.UpdateActiveUserPseudo(SenderIP, NewPseudo);
		//sending an ack packet to confirm our changes
		byte[] out_buffer= Packet.Ack(NewPseudo).getBytes();
		packet = new DatagramPacket(out_buffer, out_buffer.length, packet.getAddress(), packet.getPort());
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//acknowledgment of the changes of the other users
	public void processAck(String NewPseudo) {
		Global.userPseudo = NewPseudo;
	}
	
	
	public void processDisconnected(String DiscIP,String DiscPseudo) {
		//removing the disconnected user from the active user list
		if(!(aUM.CheckPseudoUnicity(DiscPseudo))){
			aUM.removeActiveUser(DiscIP, DiscPseudo);
		}
		//closing all connections and thread with this user
		Iterator<MessServWorker> Workers = ServConnections.ClientList.iterator();
		while(Workers.hasNext()) {
			MessServWorker worker = Workers.next();
			if (worker.ClientIP().equals(DiscIP)) {
				worker.Disconnecting_Client();
				worker.interrupt();
				ServConnections.removeClientConnection(Workers);
				break;
			}
		}

		ServConnections.PrintClientList();
		Iterator<MessageClient> Clients = MultiClientConnections.ClientConnections.iterator();
		while(Clients.hasNext()) {
			MessageClient MC = Clients.next();
			if (MC.getRemoteIP().equals(DiscIP)) {
				MC.EndChat();
				MultiClientConnections.removeClient(Clients);
				break;
			}
		}
	}
}