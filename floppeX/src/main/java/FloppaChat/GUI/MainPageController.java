package FloppaChat.GUI;

import java.io.IOException;
import FloppaChat.DataBase.ActiveUserCustom;
import FloppaChat.DataBase.ActiveUserManager;
import FloppaChat.DataBase.DBController;
import FloppaChat.DataBase.Message;
import FloppaChat.Network.BroadcastServer;
import FloppaChat.Network.MessServSender;
import FloppaChat.Network.MessageMainServer;
import FloppaChat.floppeX.App;
import FloppaChat.Network.MultiClientConnections;
import FloppaChat.Network.NetInterface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainPageController{

	private static BroadcastServer broadserv;
	private static MessageMainServer MainServ;
	private DBController dbcontrol = new DBController(Global.dbName);
	private ActiveUserManager aUM = new ActiveUserManager();
	private App App = new App();

	@FXML private ListView<ActiveUserCustom> activeusers;
	@FXML private ListView<ActiveUserCustom> activeUserList;
	@FXML private Text pseudotext;
	@FXML private VBox centerPage;
	@FXML private BorderPane borderPane;
	@FXML private Text pseudoForeign;
	@FXML private VBox messagelist;
	@FXML private Button backbutton;
	@FXML private Button flopit;
	@FXML private Button changePseudoButton;
	@FXML private TextField contentMessage;

	public static void startServers(BroadcastServer broadserv, MessageMainServer MMS) {
		MainPageController.broadserv = broadserv;
		broadserv.start();
		Global.BroadServRunning=true;
		MainPageController.MainServ=MMS;
		MMS.startServ();
	}

	public static void stopEverything() {
		MainServ.stopServ();
		MultiClientConnections.ClosingClients();
		if (Global.BroadServRunning) {
			NetInterface.Disconnect();
			broadserv.interrupt();
		}

	}

	private void processAlert(String message,AlertType type) throws IOException {
		Alert alert = new Alert(type, message,ButtonType.OK);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.OK) {

		}
	}

	@FXML
	public void initialize() {
		if(pseudotext!=null)
			pseudotext.setText(Global.userPseudo);

		if(activeusers!=null) 
			activeusers.setItems(ActiveUserManager.Act_User_List);

		if(activeUserList!=null) 
			activeUserList.setItems(ActiveUserManager.Act_User_List);

		if (pseudoForeign!=null) 	
			pseudoForeign.setText(Global.activeUserChat);

		if (messagelist!=null) {
			try {
				this.fillMessageHistorics();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Global.MPC = this;
		}	
	}

	private String getPseudoFromIndex(int index){
		return activeusers.getItems().get(index).toString();
	}

	private String getPseudoFromIndex2(int index){
		return activeUserList.getItems().get(index).toString();
	}

	@FXML
	private void activeUserClicked() throws IOException{
		if (activeusers.getSelectionModel().getSelectedIndices().size() > 0){
			Global.activeUserIndex = (int)activeusers.getSelectionModel().getSelectedIndices().get(0);
			String name = getPseudoFromIndex(Global.activeUserIndex);
			System.out.println(name);
			if (!aUM.pseudoExists(name)) {
				processAlert("User no longer active",AlertType.ERROR);
			} else {
				Global.activeUserChat = name;
				String activeUserIP = aUM.getActiveUserIP(name);
				dbcontrol.createUser(name,activeUserIP);
				Global.activeUserID = dbcontrol.getIDfromUser(name, activeUserIP);
				FXMLLoader loader = new FXMLLoader();   
				VBox chatThing = loader.load(App.class.getResource("ChatPage.fxml").openStream());
				borderPane.setCenter(chatThing); 
			}
		} 
	}	

	@FXML
	private void activeUserClicked2() throws IOException{
		if (activeUserList.getSelectionModel().getSelectedIndices().size() > 0){
			Global.activeUserIndex = (int)activeUserList.getSelectionModel().getSelectedIndices().get(0);
			String name = getPseudoFromIndex2(Global.activeUserIndex);
			System.out.println(name);
			if (!aUM.pseudoExists(name)) {
				processAlert("User no longer active",AlertType.ERROR);
			} else {
				Global.activeUserChat = name;
				String activeUserIP = aUM.getActiveUserIP(name);
				dbcontrol.createUser(name,activeUserIP);
				Global.activeUserID = dbcontrol.getIDfromUser(name, activeUserIP);
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
		contenu_t.setText(this.processMessage(cont));
		date_t.setText(date);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				messagelist.getChildren().add(label);
			}
		});
	}

	public String processMessage(String cont) {
		String a = "";
		if (cont != null) {
			a+=cont.charAt(0);
			for (int i=1;i<cont.length();i++) {
				if (i%45==0)
					a+="\n";
				a+=cont.charAt(i);
			}
		}
		return a;
	}

	public void addMessageFrom(String cont,String date) throws IOException {
		addMessage(cont,"receiveLabel.fxml",date);
	}

	public void addMessageTo(String cont,String date) throws IOException {
		addMessage(cont,"sentLabel.fxml",date);
	}


	private void fillMessageHistorics() throws IOException{
		for(Message m : this.dbcontrol.fetchMessagesWithUser(Global.activeUserID)) {
			if(m.isSent())
				addMessageTo(m.getContent(),m.getDate());
			else
				addMessageFrom(m.getContent(),m.getDate());
		}
	}

	private void sendMessage(String TargetIP, String Message) {
		if(MessServSender.isMessServer(TargetIP)) {
			MessServSender.SendMessToClient(TargetIP, Message);
		}
		else {
			MultiClientConnections.SendMessAsClient(TargetIP,Message);
		}
	}

	@FXML
	private void sendMessageEnter(KeyEvent keyEvent) throws IOException {
		if(keyEvent.getCode()== KeyCode.ENTER) {
			if(contentMessage.getText().equals(""))
				processAlert("No content",AlertType.ERROR);
			else {
				sendMessage(aUM.getActiveUserIP(Global.activeUserChat),contentMessage.getText());
				dbcontrol.addMessage(Global.activeUserID,Global.nowDate(),contentMessage.getText(),true);
				addMessageTo(contentMessage.getText(),Global.nowDate());
				contentMessage.setText("");
			}
		}
	}

	@FXML
	private void sendMessageButton() throws IOException {
		if(contentMessage.getText().equals(""))
			processAlert("No content",AlertType.ERROR);
		else {
			sendMessage(aUM.getActiveUserIP(Global.activeUserChat),contentMessage.getText());
			dbcontrol.addMessage(Global.activeUserID,Global.nowDate(),contentMessage.getText(),true);
			addMessageTo(contentMessage.getText(),Global.nowDate());
			contentMessage.setText("");
		}
	}

	@FXML
	private void showFrontPage() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to change pseudo?",ButtonType.YES,ButtonType.NO);
		alert.showAndWait();
		if(alert.getResult() == ButtonType.YES)
			App.setRoot("FloppaFrontPage");
	}

	/**
	 * @throws IOException
	 */
	@FXML
	private void backToMainPage() throws IOException {
		App.setRoot("MainPage");
	}
}