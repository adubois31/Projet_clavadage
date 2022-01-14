package FloppaChat.GUI;

import FloppaChat.DataBase.ActiveUserManager;

public class testList extends Thread{
	ActiveUserManager aUM;
	public testList() {
		aUM = new ActiveUserManager();
	}
	
	@Override
	public void run() {
		System.out.println("Thread running for list");
		Integer i=0;
		while(true) {
			i++;
			try {
				Thread.sleep(5000);
				aUM.addActiveUser(i.toString(), i.toString());
				System.out.println("Added element to list");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
