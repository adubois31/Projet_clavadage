package FloppaChat.GUI;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

public class ActiveUserCustom {
	StringProperty pseudo = new SimpleStringProperty();
	IntegerProperty id = new SimpleIntegerProperty();
	
	public static Callback<ActiveUserCustom, Observable[]> extractor(){
		return new Callback<ActiveUserCustom, Observable[]>(){
			@Override
			public Observable[] call(ActiveUserCustom param) {
				return new Observable[] {param.id,param.pseudo};
			}
		};
	}
	
	@Override
	public String toString() {
		return pseudo.get();
	}
}
