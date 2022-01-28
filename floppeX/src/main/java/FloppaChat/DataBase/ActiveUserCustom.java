package FloppaChat.DataBase;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

public class ActiveUserCustom {
	StringProperty pseudo = new SimpleStringProperty();
	StringProperty IP = new SimpleStringProperty();
	
	public static Callback<ActiveUserCustom, Observable[]> extractor(){
		return new Callback<ActiveUserCustom, Observable[]>(){
			@Override
			public Observable[] call(ActiveUserCustom param) {
				return new Observable[] {param.pseudo,param.IP};
			}
		};
	}
	
	public String getPseudo() {
		return pseudo.get();
	}
	
	public String getIP() {
		return IP.get();
	}
	
	@Override
	public String toString() {
		return pseudo.get();
	}
}
