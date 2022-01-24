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
		System.out.println("Interrupting client thread "+Thread.currentThread().isInterrupted());
		closeEverything();
		super.interrupt();
		Thread.currentThread().interrupt();
		System.out.println("Interrupting client thread "+Thread.currentThread().getId());
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
		try {
			String MessFromClient = BuffRead.readLine();
			if (MessFromClient != null) {
				DBC.addMessage(DBC.getIDfromUser(ClientPseudo(), ClientIP()), Global.MPC.nowDate(), MessFromClient, false);
				if (Global.activeUserChat.equals(ClientPseudo()))
					Global.MPC.addMessageFrom(MessFromClient, Global.MPC.nowDate());
				}
		}catch(IOException e){
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
