package FloppaChat.floppeX;

import java.io.IOException;
import javafx.fxml.FXML;

public class ChatPageController {
	
	@FXML
	private void backButtonClicked() throws IOException {
		App.setRoot("MainPage");
	}

}
