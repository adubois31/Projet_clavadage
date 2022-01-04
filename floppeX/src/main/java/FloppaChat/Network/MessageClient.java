package FloppaChat.Network;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
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
            closeEverything(Sock, BuffRead, BuffWrite);
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
            closeEverything(Sock, BuffRead, BuffWrite);
        }
    }

    public void RecvMessFromServer(){
    	MainPageController MPC = new MainPageController();
        new Thread(new Runnable(){
            @Override
            public void run(){
                while (Sock.isConnected()){
                    try {
                        String MessFromServer = BuffRead.readLine();
                        //MPC.addMessageFrom(MessFromServer, MPC.nowDate());
                        System.out.println("Message du serveur : "+MessFromServer);
                    } catch (IOException e) {
                        System.out.println("Erreur réception du message du serveur");
                        e.printStackTrace();
                        closeEverything(Sock, BuffRead, BuffWrite);
                        break;
                    }
                }
            }
        }).start();;
    }

    public void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter){
        try {
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}