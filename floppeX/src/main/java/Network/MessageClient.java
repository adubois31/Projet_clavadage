package Network;

import java.io.*;
import java.net.Socket;

	
public class MessageClient {
		public static void main (String args [])throws Exception{
			Socket sk = new Socket("127.0.0.1",42069);
			BufferedReader str_in=new BufferedReader(new InputStreamReader(sk.getInputStream()));
			PrintStream str_out = new PrintStream(sk.getOutputStream());
			BufferedReader std_in=new BufferedReader(new InputStreamReader(System.in));
			String s;
			
			while (true) {
				System.out.println("Client :");
				s=std_in.readLine();
				str_out.println(s);
				if(s.equalsIgnoreCase("")) {
					System.out.println("Connection ended by client");
					break;
				}
				s=str_in.readLine();
				if(s.equalsIgnoreCase("Cheh")) {
					System.out.println("Connection ended by Server");
					break;
				}
				System.out.println("Server :"+s+"\n");
			}
			sk.close();
			str_in.close();
			str_out.close();
			std_in.close();
			
		}
		
}


