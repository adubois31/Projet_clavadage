module FloppaChat.floppeX {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires java.base;
	requires javafx.base;

    opens FloppaChat.floppeX to javafx.fxml;
    opens FloppaChat.GUI to javafx.fxml ;
    opens FloppaChat.DataBase to javafx.fxml ;
    opens FloppaChat.Network to javafx.fxml ;
    exports FloppaChat.floppeX;
}
