package GUI;

import java.io.IOException;

import FloppaChat.floppeX.App;
import javafx.fxml.FXML;

public class ChatPageController {
	
	@FXML
	private void backButtonClicked() throws IOException {
		App.setRoot("MainPage");
	}

}
