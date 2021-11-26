package FloppaChat.floppeX;

public class Process {
	
	public static boolean processHello(String IP,String Pseudo) {
		if(Database_Manager.CheckPseudoUnicity(Pseudo)){
			Database_Manager.addActiveUser(IP, Pseudo);
			System.out.println("Pseudo OK");
			return true;
		}
		else {
			System.out.println("Pseudo already taken");
			return false;
		}
		
	}
	public static void processHelloBack(String Answer) {
		String[] Split_Answer = Answer.split("\\|");
		if (Database_Manager.CheckPseudoUnicity(Split_Answer[2])){
			Database_Manager.addActiveUser(Split_Answer[1],Split_Answer[2]);
		}
		if(Split_Answer[0]=="OK") {
			System.out.println("Pseudo Valide");
		}
		else {
			System.out.println("Pseudo Déjà Pris");
		}
	}

}
