package FloppaChat.GUI;

import java.io.IOException;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import FloppaChat.DataBase.ActiveUser;
import FloppaChat.DataBase.ActiveUserManager;
import FloppaChat.DataBase.DBController;
import FloppaChat.DataBase.Message;
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
	
	private void processAlert(String message,AlertType type) throws IOException {
		Alert alert = new Alert(type, message,ButtonType.OK);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.OK) {
			
		}
	}
	
	public String nowDate() {
		LocalTime myTimeObj = LocalTime.now();
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");
	    LocalDate myDateObj = LocalDate.now();
	    DateTimeFormatter myFormatObj2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    return myTimeObj.format(myFormatObj)+" "+myDateObj.format(myFormatObj2);
	}
	
	private DBController dbcontrol = new DBController("flopBase");
	
	private ActiveUserManager aUM = new ActiveUserManager();
	
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
		if(pseudotext!=null)
			pseudotext.setText(UserPseudo.userPseudo);
		if(activeusers!=null) {
			aUM.addActiveUser("69.69", "Thomas");
			aUM.addActiveUser("69.69", "Hugo");
			aUM.addActiveUser("69.69", "Clement");
			aUM.addActiveUser("69.69", "Chama");
			for(ActiveUser au : ActiveUserManager.Act_User_List) {
				activeusers.getItems().add(au.getPseudo());
			}
		}
		if (pseudoForeign!=null) 	
			pseudoForeign.setText(UserPseudo.activeUserChat);
		if (messagelist!=null) {
			addMessageFrom("Je ne veux pas parler avec toi deso",nowDate());
			this.fillMessageHistorics();
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
	            if (!aUM.pseudoExists(name)) {
	            	processAlert("User no longer active",AlertType.ERROR);
	            } else {
		            UserPseudo.activeUserChat = name;
		            String activeUserIP = aUM.getActiveUserIP(name);
		            dbcontrol.createUser(name,activeUserIP);
		            UserPseudo.activeUserID = dbcontrol.getIDfromUser(name, activeUserIP);
		            FXMLLoader loader = new FXMLLoader();   
		            VBox chatThing = loader.load(App.class.getResource("ChatPage.fxml").openStream());
		            borderPane.setCenter(chatThing); 
	            }
            } 
    }	
	
	private void addMessage(String cont,String path,String date) throws IOException {
		FXMLLoader loaderLabel = new FXMLLoader(); 
        AnchorPane label = loaderLabel.load(App.class.getResource(path).openStream());
        VBox labelMessage = (VBox) label.getChildren().get(0);
        Text contenu_t = (Text) labelMessage.getChildren().get(0);
        Label date_t = (Label) labelMessage.getChildren().get(1);
        contenu_t.setText(cont);
		date_t.setText(date);
        messagelist.getChildren().add(label);
	}
	
	public void addMessageFrom(String cont,String date) throws IOException {
		addMessage(cont,"receiveLabel.fxml",date);
	}
	
	public void addMessageTo(String cont,String date) throws IOException {
		addMessage(cont,"sentLabel.fxml",date);
	}
	
	private void fillMessageHistorics() throws IOException{
		for(Message m : this.dbcontrol.fetchMessagesWithUser(UserPseudo.activeUserID)) {
			System.out.println("Is sent is : "+m.isSent());
			if(m.isSent())
				addMessageTo(m.getContent(),m.getDate());
			else
				addMessageFrom(m.getContent(),m.getDate());
		}
	}
	
	@FXML
	private void sendMessageEnter(KeyEvent keyEvent) throws IOException {
		if(keyEvent.getCode()== KeyCode.ENTER) {
			if(contentMessage.getText().equals(""))
				processAlert("No content",AlertType.ERROR);
			else {
				dbcontrol.addMessage(UserPseudo.activeUserID,nowDate(),contentMessage.getText(),true);
				addMessageTo(contentMessage.getText(),nowDate());
				contentMessage.setText("");
			}
		}
	}
	
	@FXML
	private void sendMessageButton() throws IOException {
		if(contentMessage.getText().equals(""))
			processAlert("No content",AlertType.ERROR);
		else {
			dbcontrol.addMessage(UserPseudo.activeUserID,nowDate(),contentMessage.getText(),true);
			addMessageTo(contentMessage.getText(),nowDate());
			contentMessage.setText("");
		}
	}
	
	
	@FXML
	private void backToMainPage() throws IOException {
		//System.out.println("Back to Main");
		App.setRoot("MainPage");
	}
}
