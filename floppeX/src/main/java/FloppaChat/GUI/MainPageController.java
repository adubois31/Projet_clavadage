package FloppaChat.GUI;

import java.io.IOException;
import java.time.LocalDate;

import FloppaChat.floppeX.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
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
	
	@FXML private VBox messagelist;
	
	@FXML private Button backbutton;
	@FXML private Button flopit;

	@FXML private TextField contentMessage;
	
	@FXML
	protected void initialize() throws IOException {
		System.out.println("hei hei");
		if(pseudotext!=null)
			pseudotext.setText(UserPseudo.userPseudo);
		if(activeusers!=null) {
			activeusers.getItems().add("Hugo");
			activeusers.getItems().add("Viktor");
			activeusers.getItems().add("Mathis");
		}
		if (pseudoForeign!=null) 	
			pseudoForeign.setText(UserPseudo.activeUserChat);
		if (messagelist!=null) {
			addMessageTo("Salut toi");
			addMessageFrom("Je ne veux pas parler avec toi deso");
		}
		
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
	            UserPseudo.activeUserChat = name;
	            FXMLLoader loader = new FXMLLoader();   
	            VBox chatThing = loader.load(App.class.getResource("ChatPage.fxml").openStream());
	            borderPane.setCenter(chatThing);   
            } 
    }	
	
	private void addMessage(String cont,String path) throws IOException {
		FXMLLoader loaderLabel = new FXMLLoader(); 
        AnchorPane label = loaderLabel.load(App.class.getResource(path).openStream());
        VBox labelMessage = (VBox) label.getChildren().get(0);
        Text contenu_t = (Text) labelMessage.getChildren().get(0);
        Label date_t = (Label) labelMessage.getChildren().get(1);
        contenu_t.setText(cont);
		date_t.setText(LocalDate.now().toString());
        messagelist.getChildren().add(label);
	}
	
	private void addMessageFrom(String cont) throws IOException {
		addMessage(cont,"receiveLabel.fxml");
	}
	
	private void addMessageTo(String cont) throws IOException {
		addMessage(cont,"sentLabel.fxml");
	}
	
	@FXML
	private void sendMessageEnter(KeyEvent keyEvent) throws IOException {
		if(keyEvent.getCode()== KeyCode.ENTER) {
		addMessageTo(contentMessage.getText());
		contentMessage.setText("");
		}
	}
	
	@FXML
	private void sendMessageButton() throws IOException {
		addMessageTo(contentMessage.getText());
		contentMessage.setText("");
	}
	
	
	@FXML
	private void backToMainPage() throws IOException {
		//System.out.println("Back to Main");
		App.setRoot("MainPage");
	}
}
