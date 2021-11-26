module FloppaChat.floppeX {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;

    opens FloppaChat.floppeX to javafx.fxml;
    exports FloppaChat.floppeX;
}
