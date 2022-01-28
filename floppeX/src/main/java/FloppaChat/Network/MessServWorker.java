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
	private boolean Disconnected = false;
	
	public void Disconnecting_Client() {
		Disconnected=true;
	}
	
	//our constructor initializing our buffers and getting the socket
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
		super.interrupt();
	}
	
	//getting the ip of the client connected
	public String ClientIP() {
		return clientSock.getInetAddress().toString().substring(1);
	}
	
	//getting the pseudo of the client connected
	private String ClientPseudo() {
		ActiveUserManager aUM = new ActiveUserManager(); 
		return aUM.getActiveUserPseudo(ClientIP());
	}

	private void RecvMessFromClient() throws IOException {
		DBController DBC = new DBController(Global.dbName);
		if(!Disconnected)
			DBC.createUser(ClientPseudo(), ClientIP());
		try {
			String MessFromClient = BuffRead.readLine();
			if (MessFromClient != null) {
				int dbID = DBC.getIDfromUser(ClientPseudo(), ClientIP());
				DBC.addMessage(dbID, Global.nowDate(), MessFromClient, false);
				//if we are not actively chatting with this client we store it in our db and not display it
				if (Global.activeUserChat.equals(ClientPseudo()))
					Global.MPC.addMessageFrom(MessFromClient, Global.nowDate());
				}
		}catch(IOException | NullPointerException e){
			System.out.println(e.getMessage());
			System.out.println("Error RcvMessFromClient");
			super.interrupt();
		}
	}
	//sends message to the client connected to the socket
	public void SendMessToClient(String messageToClient){
		try {
			BuffWrite.write(messageToClient);
			BuffWrite.newLine();
			BuffWrite.flush();
		} catch (IOException e) {
			e.printStackTrace();
			closeEverything();
			super.interrupt();
		}
	}

	//closing all the buffers and the socket
	private void closeEverything(){
		try {
			if (clientSock != null){
				clientSock.close();
			}
			if (BuffRead != null){
				BuffRead.close();
			}
			if(this.BuffWrite!= null){
				BuffWrite.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
