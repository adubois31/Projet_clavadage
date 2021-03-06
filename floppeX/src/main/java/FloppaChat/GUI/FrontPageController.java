package FloppaChat.GUI;

import java.io.IOException;

import FloppaChat.Network.BroadcastServer;
import FloppaChat.Network.MessageMainServer;
import FloppaChat.Network.NetInterface;
import FloppaChat.floppeX.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FrontPageController {
	private App App = new App();
	
	private void processAlert(String message,AlertType type) throws IOException {
		Alert alert = new Alert(type, message,ButtonType.OK);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.OK) {
			App.setRoot("FloppaFrontPage");
		}
	}
	
	@FXML private TextField pseudo;
	
	@FXML
	private void keyPressed(KeyEvent keyEvent) throws IOException {
		String typedPseudo = pseudo.getText().strip();
		if(keyEvent.getCode()== KeyCode.ENTER) {
			if (typedPseudo.equals("Floppa")) {
				processAlert("You can't choose the pseudo of god",AlertType.WARNING);
			} else if(typedPseudo.equals("")) {
				processAlert("No pseudo written",AlertType.ERROR);
			} else {
				if (Global.BroadServRunning) {
					if (NetInterface.ChangePseudo(Global.userPseudo,typedPseudo )) {
						Global.userPseudo=typedPseudo;
						App.setRoot("MainPage");
						App.nextStage();
					}
					else 
					{
						processAlert("Pseudo already taken",AlertType.ERROR);
					}
				}
				else 
				{
					if(NetInterface.ChoosePseudo(typedPseudo)) {
						Global.userPseudo = typedPseudo;
						BroadcastServer BS = new BroadcastServer(Global.BroadServNb);
						MessageMainServer MMS = new MessageMainServer(Global.MessServNb);
						MainPageController.startServers(BS,MMS);
						Global.BroadServRunning=true;
						App.setRoot("MainPage");
						App.nextStage();
					} 
					else {
						processAlert("Pseudo already taken",AlertType.ERROR);
					}
				}
			}
		}
	}
		
	public void errorPseudo(String pseudo) throws IOException{
		Alert alert = new Alert(AlertType.ERROR, "Pseudo already taken : "+pseudo,ButtonType.OK);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.OK) {
			App.setRoot("FloppaFrontPage");
		}
	}

}

