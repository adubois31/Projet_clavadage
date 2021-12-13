package FloppaChat.floppeX;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FrontPageController {
	
	Alert alert = new Alert(AlertType.WARNING, "You can't choose that pseudo (God)",ButtonType.OK);
	
	@FXML private TextField pseudo;
	
	@FXML
	private void keyPressed(KeyEvent keyEvent) throws IOException {
		if(keyEvent.getCode()== KeyCode.ENTER) {
			if (pseudo.getText().equals("Floppa")) {
				alert.showAndWait();
				if (alert.getResult() == ButtonType.OK) {
					App.setRoot("FloppaFrontPage");
				}
			} else {
				UserPseudo.userPseudo = pseudo.getText();
				App.setRoot("MainPage");
			}
			//Envoyer broadcast à tout le monde avec pseudo
		} else {
			System.out.println("Wrong key pressed.");
		}
    }

}
