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
    
    public String getRemoteIP() {
    	return Sock.getRemoteSocketAddress().toString().substring(1);
    }

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
        new Thread(new Runnable(){
            @Override
            public void run(){
                while (Sock.isConnected()){
                	System.out.println("Thread started");
                    try {
                        String MessFromServer = BuffRead.readLine();
                        String ServerIP = Sock.getInetAddress().toString().substring(1);
                        DBC.addMessage(DBC.getIDfromUser(aUM.getActiveUserPseudo(ServerIP), ServerIP), Global.MPC.nowDate() , MessFromServer, false);
                        Global.MPC.addMessageFrom(MessFromServer, Global.MPC.nowDate());
                    } catch (IOException e) {
                        System.out.println("Erreur réception du message du serveur");
                        e.printStackTrace();
                        closeEverything();
                        break;
                    }
                }
            }
        }).start();;
    }
    public void EndChat() {
    	closeEverything();
    }
    
    private void closeEverything(){
        try {
            if (BuffRead != null){
                BuffRead.close();
            }
            if(BuffWrite!= null){
                BuffWrite.close();
            }
            if (Sock != null){
                Sock.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}