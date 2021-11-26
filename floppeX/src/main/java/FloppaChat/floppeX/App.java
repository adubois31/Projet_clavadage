package FloppaChat.floppeX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("FloppaFrontPage"), 640, 480);
        stage.setScene(scene);
        stage.show();
        Connection connection = null;
        try
        {
          // create a database connection
          Class.forName("org.sqlite.JDBC");
          connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/sqlite/db/test.db");
          String query1 = "create table test2 (id int,name varchar(255));";
          Statement statement = connection.createStatement();
          statement.executeQuery(query1);
          System.out.println("Query executed");
          /*String query2 = "select * from test2;";
          Statement statement2 = connection.createStatement();
          ResultSet res = statement2.executeQuery(query2);
          while(res.next()) {
        	  System.out.println(res.getInt("id")+res.getString("name"));
          }*/
          connection.close();
        } catch(Exception e) {
        	System.err.println(e.getMessage());
        }
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}