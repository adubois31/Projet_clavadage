module FloppaChat.floppeX {
    requires javafx.controls;
    requires javafx.fxml;

    opens FloppaChat.floppeX to javafx.fxml;
    exports FloppaChat.floppeX;
}
