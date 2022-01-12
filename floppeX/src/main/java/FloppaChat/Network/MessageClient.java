package FloppaChat.Network;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

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

    	MainPageController MPC = new MainPageController();
    	DBController DBC = new DBController(Global.dbName);
        new Thread(new Runnable(){
            @Override
            public void run(){
                while (Sock.isConnected()){
                	System.out.println("Thread started");
                    try {
                        String MessFromServer = BuffRead.readLine();
                        MPC.addMessageFrom(MessFromServer, MPC.nowDate());
                        DBC.addMessage(DBC.getIDfromUser(Global.userPseudo, Sock.getInetAddress().toString().substring(1)), MPC.nowDate() , MessFromServer, false);
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