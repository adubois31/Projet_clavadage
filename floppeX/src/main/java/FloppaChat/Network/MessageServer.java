package FloppaChat.Network;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import FloppaChat.GUI.MainPageController;
import FloppaChat.GUI.UserPseudo;
import FloppaChat.DataBase.DBController;

public class MessageServer{

    private ServerSocket ServSock;
    private Socket Sock;
    private BufferedReader BuffRead;
    private BufferedWriter BuffWrite;

    public MessageServer(ServerSocket ServSock){
        try {
            this.ServSock=ServSock;
            this.Sock=ServSock.accept();
            this.BuffWrite = new BufferedWriter(new OutputStreamWriter(Sock.getOutputStream()));
            this.BuffRead = new BufferedReader(new InputStreamReader(Sock.getInputStream()));
            } catch (IOException e){
            System.out.println("Erreur création serveur.");
            e.printStackTrace();
            closeEverything();
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

    public void RecvMessFromClient(){
    	MainPageController MPC = new MainPageController();
    	DBController DBC = new DBController("");
        new Thread(new Runnable(){
            @Override
            public void run(){
                while (Sock.isConnected()){
                    try {
                        String MessFromClient = BuffRead.readLine();
                        if (MessFromClient != null) {
                        	MPC.addMessageFrom(MessFromClient, MPC.nowDate());
                        	
                        }
                        
                    } catch (IOException e) {
                        System.out.println("Erreur réception du message du client");
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