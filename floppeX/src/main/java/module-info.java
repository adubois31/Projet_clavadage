module FloppaChat.floppeX {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;

    opens FloppaChat.floppeX to javafx.fxml;
    exports FloppaChat.floppeX;
}
