module FloppaChat.FloppeX {
    requires javafx.controls;
    requires javafx.fxml;

    opens FloppaChat.FloppeX to javafx.fxml;
    exports FloppaChat.FloppeX;
}
