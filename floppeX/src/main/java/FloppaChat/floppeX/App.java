package FloppaChat.floppeX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import FloppaChat.GUI.Global;
import FloppaChat.GUI.MainPageController;

public class App extends Application {

    private static Scene scene; 
    private static Stage stageMod;

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
    	stageMod=stage;
        scene = new Scene(loadFXML("FloppaFrontPage"), 640, 480);
        stageMod.setScene(scene);
        stageMod.show();
    }
    
    @Override
    public void stop(){
        System.out.println("Closing App...");
        if (Global.BroadServRunning)
        	MainPageController.stopEverything();
    }
    
    public void nextStage() throws IOException {
    	stageMod.close();
    	stageMod = new Stage();
    	scene = new Scene(loadFXML("MainPage"), 850, 682);
    	stageMod.setScene(scene);
    	stageMod.show();
    }

    public void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private Parent loadFXML(String fxml){
    	FXMLLoader fxmlLoader;
    	try {
            fxmlLoader = new FXMLLoader(getClass().getResource("/resources/FloppaChat/floppeX/"+fxml + ".fxml"));
            return fxmlLoader.load();
    	} catch (IllegalStateException | IOException e) {
            fxmlLoader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
            try {
				return fxmlLoader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
    	} 
        return null;
    }

    public static void main(String[] args) {
    	System.out.println("Launching app...");
        launch();
    }

}