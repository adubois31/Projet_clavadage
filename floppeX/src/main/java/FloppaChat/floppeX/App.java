package FloppaChat.floppeX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    
    private static Stage stageMod;

    @Override
    public void start(Stage stage) throws IOException {
    	stageMod=stage;
        scene = new Scene(loadFXML("FloppaFrontPage"), 640, 480);
        stageMod.setScene(scene);
        stageMod.show();
    }
    
    @Override
    public void stop(){
        System.out.println("Closing App...");
    }
    
    public static void nextStage() throws IOException {
    	stageMod.close();
    	stageMod = new Stage();
    	scene = new Scene(loadFXML("MainPage"), 850, 682);
    	stageMod.setScene(scene);
    	stageMod.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
    	System.out.println("Launching app...");
        launch();
    }

}