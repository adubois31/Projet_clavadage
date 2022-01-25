package FloppaChat.Network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import FloppaChat.DataBase.ActiveUserManager;
import FloppaChat.DataBase.DBController;
import FloppaChat.GUI.Global;

public class MessServWorker extends Thread {
	private boolean isRunning = true;
	private Socket clientSock;
	private BufferedReader BuffRead;
	private BufferedWriter BuffWrite;

	public MessServWorker(Socket clientSock) throws IOException {
		this.clientSock = clientSock;
		this.BuffRead=new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
		this.BuffWrite = new BufferedWriter(new OutputStreamWriter(clientSock.getOutputStream()));
	}

	@Override
	public void run() {
		while(clientSock.isConnected()&&isRunning) {
			try {
				RecvMessFromClient();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
		closeEverything();
	}

	@Override
	public void interrupt() {
		isRunning=false;
		closeEverything();
		System.out.println("Interrupting client thread "+super.getId());
		super.interrupt();
		System.out.println("Client thread status : "+super.getState());
		System.out.println("Client thread status : "+super.isInterrupted());
	}

	public String ClientIP() {
		return clientSock.getInetAddress().toString().substring(1);
	}

	private String ClientPseudo() {
		ActiveUserManager aUM = new ActiveUserManager(); 
		return aUM.getActiveUserPseudo(ClientIP());
	}

	private void RecvMessFromClient() throws IOException {
		DBController DBC = new DBController(Global.dbName);
		DBC.createUser(ClientPseudo(), ClientIP());
		try {
			String MessFromClient = BuffRead.readLine();
			if (MessFromClient != null) {
				int dbID = DBC.getIDfromUser(ClientPseudo(), ClientIP());
				DBC.addMessage(dbID, Global.MPC.nowDate(), MessFromClient, false);
				if (Global.activeUserChat.equals(ClientPseudo()))
					Global.MPC.addMessageFrom(MessFromClient, Global.MPC.nowDate());
				}
		}catch(IOException | NullPointerException e){
			System.out.println(e.getMessage());
			System.out.println("Error RcvMessFromClient");
			super.interrupt();
		}
	}

	public void SendMessToClient(String messageToClient){
		try {
			BuffWrite.write(messageToClient);
			BuffWrite.newLine();
			BuffWrite.flush();
		} catch (IOException e) {
			System.out.println("Erreur envoi du message au client");
			e.printStackTrace();
			closeEverything();
		}
	}

	private void closeEverything(){
		try {
			if (clientSock != null){
				clientSock.close();
			}
			if (BuffRead != null){
				BuffRead.close();
				System.out.println(BuffRead);
			}
			if(this.BuffWrite!= null){
				BuffWrite.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}