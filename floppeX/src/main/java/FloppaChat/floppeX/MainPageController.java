package FloppaChat.floppeX;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainPageController {
	@FXML private Text pseudotext;
	
	@FXML private ListView<String> activeusers;
	
	@FXML private VBox centerPage;
	
	@FXML private BorderPane borderPane;
	
	@FXML private Text pseudoForeign;

	@FXML
	protected void initialize() throws IOException {
		
		pseudotext.setText(UserPseudo.userPseudo);
		activeusers.getItems().add("Hugo");
		activeusers.getItems().add("Viktor");
		activeusers.getItems().add("Mathis");
		System.out.println(borderPane);
		System.out.println(pseudoForeign);
	}
	
	private String getPseudoFromIndex(int index){
        return activeusers.getItems().get(index).toString();
    }
	
	@FXML
	private void activeUserClicked() throws IOException{

        if (activeusers.getSelectionModel().getSelectedIndices().size() > 0){
            UserPseudo.activeUserIndex = (int)activeusers.getSelectionModel().getSelectedIndices().get(0);
            String name = getPseudoFromIndex(UserPseudo.activeUserIndex);
            System.out.println(name);
            
            FXMLLoader loader = new FXMLLoader();   
            VBox chatThing = loader.load(getClass().getResource("ChatPage.fxml").openStream());
            borderPane.setCenter(chatThing);   
            //pseudoForeign.setText(name);
            } 
    }	
}
