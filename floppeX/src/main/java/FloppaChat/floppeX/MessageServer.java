package FloppaChat.floppeX;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

	
public class MessageServer{
		int port;
		ServerSocket M_Serv=null;
		Socket link=null;
		ExecutorService pool =null;
		int client_count=0;
		
		
		MessageServer(int port){
			this.port=port;
			pool = Executors.newFixedThreadPool(1000);
		}
		
		public void startServer() throws IOException {
			M_Serv=new ServerSocket(42069);
			System.out.println("Server Started");
			while (true)
			{
				link=M_Serv.accept();
				client_count++;
				ServerThread runnable = new ServerThread(link,client_count);
				pool.execute(runnable);
			}	
			
		}
		
		private class ServerThread implements Runnable {

			Socket client;
			BufferedReader com_in;
			PrintStream com_out;
			Scanner sc = new Scanner(System.in);
			int id;
			String s;
			
			ServerThread(Socket link, int count) throws IOException {
				this.client = link;
				this.id=count;
				System.out.println("Connection"+id+"established with a client");
				com_in=new BufferedReader(new InputStreamReader(client.getInputStream()));
				com_out=new PrintStream(client.getOutputStream());
			}
			
			@Override
			public void run() {
				int x=1;
				try {
					while (true) 
					{
						s = com_in.readLine();
							System.out.println("Client("+id+"):"+s+"\n");
							System.out.println("Server :");
							s=sc.nextLine();
							if (s.equalsIgnoreCase("Bingus")) 
							{
								com_out.println("Cheh");
								x=0;
								System.out.println("Connection ended by the Floppa");
								break;
							}
							com_out.println(s);
					}
					com_in.close();
					client.close();
					com_out.close();
					if (x==0) {
						System.out.println("Sever cleaning");
						System.exit(0);
					}
				}
				catch (IOException ex) {
					System.out.println("Error"+ex);
				}
				
			}
		}
			
		public void main (String args[]) throws IOException {
			MessageServer serverobj = new MessageServer (42069);
			serverobj.startServer();
		}
		
		
		
}

