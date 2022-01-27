package FloppaChat.Network;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import FloppaChat.DataBase.ActiveUserManager;
import FloppaChat.DataBase.DBController;
import FloppaChat.GUI.*;

public class MessageClient{
	private Socket Sock;
    private BufferedReader BuffRead;
    private BufferedWriter BuffWrite;
    private Thread ThisThread = null;
    private boolean isInterrupting = false;
    
    //initializing our buffers as a client connecting to a user
    public MessageClient(Socket socket){
        try {
        	this.Sock=socket;
        	this.BuffWrite = new BufferedWriter(new OutputStreamWriter(Sock.getOutputStream()));
            this.BuffRead = new BufferedReader(new InputStreamReader(Sock.getInputStream()));
            
        } catch (IOException e){
            System.out.println("Erreur création client.");
            e.printStackTrace();
            closeEverything();
        }
    }
    
    //get the ip of the server
    public String getRemoteIP() {
    	return Sock.getInetAddress().toString().substring(1);
    }

    //sends a message to the server
    public void SendMessToServer(String MessToServer){
        try {
            BuffWrite.write(MessToServer);
            BuffWrite.newLine();
            BuffWrite.flush();
        } catch (IOException e) {
            System.out.println("Erreur envoi du message au serveur");
            e.printStackTrace();
            closeEverything();
        }
    }

    public void RecvMessFromServer(){
    	ActiveUserManager aUM = new ActiveUserManager();
    	DBController DBC = new DBController(Global.dbName);
    	//creating a new thread waiting for the server's messages
    	ThisThread = new Thread(new Runnable(){
    		@Override
    		public void run(){
    			while (Sock.isConnected()&& (!Thread.currentThread().isInterrupted())){
    				try {
    					String MessFromServer;
    					if (( MessFromServer= BuffRead.readLine())==null) {
    						break;
    					}
    					if ((MessFromServer !=null)||MessFromServer!="") {
    						DBC.addMessage(DBC.getIDfromUser(aUM.getActiveUserPseudo(getRemoteIP()), getRemoteIP()), Global.MPC.nowDate() , MessFromServer, false);
    						//if we are not actively chatting with this server we just store the message in the db and don't display it
    						if (Global.activeUserChat.equals(aUM.getActiveUserPseudo(getRemoteIP())))
    							Global.MPC.addMessageFrom(MessFromServer, Global.MPC.nowDate());
    					}
    				} catch (IOException e) {
    					System.out.println("Erreur réception du message du serveur");
    					if(!isInterrupting)
    						closeEverything();
    					break;
    				}
    			}
    		}
    	});
    	ThisThread.start();
    }
    
    //method to end the communication with the server
    public void EndChat() {
    	if ((Thread.currentThread()!=null)&&(!ThisThread.isInterrupted())) {
    		isInterrupting =true;
    		Thread.currentThread().interrupt();
    		closeEverything();
    	}	
    }
    
    //closes the socket and all the buffers
    private void closeEverything(){
        try {
        	if (Sock != null){
                Sock.close();
                System.out.println("Socket du client : "+Sock);                
            }
            if (BuffRead != null){
                BuffRead.close();
                System.out.println("BuffRead : "+BuffRead);
            }
            if(BuffWrite!= null){
                BuffWrite.close();
                System.out.println("BuffWrite : "+BuffWrite);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}