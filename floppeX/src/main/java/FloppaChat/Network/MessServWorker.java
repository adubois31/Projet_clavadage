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
import FloppaChat.GUI.MainPageController;

public class MessServWorker extends Thread {
	
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
		while(this.clientSock.isConnected()) {
			try {
				
				RecvMessFromClient();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		closeEverything();
	}
	
	@Override
	public void interrupt() {
		super.interrupt();
		closeEverything();
	}
	
	public String ClientIP() {
		return clientSock.getInetAddress().toString().substring(1);
		
	}
	
	private String ClientPseudo() {
		ActiveUserManager aUM = new ActiveUserManager(); 
		return aUM.getActiveUserPseudo(ClientIP());
	}

	private void RecvMessFromClient() throws IOException {
		MainPageController MPC = new MainPageController();
		DBController DBC = new DBController(Global.dbName);
		String MessFromClient = BuffRead.readLine();
		if (MessFromClient != null) {
			//System.out.println("Message reçu du client : "+clientSock);
			MPC.addMessageFrom(MessFromClient, MPC.nowDate());
			DBC.addMessage(DBC.getIDfromUser(ClientPseudo(), ClientIP()), MPC.nowDate(), MessFromClient, false);
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
            if (BuffRead != null){
                BuffRead.close();
            }
            if(this.BuffWrite!= null){
                BuffWrite.close();
            }
            if (clientSock != null){
                clientSock.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
