module pl.kafara.aaa {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.kafara.AES to javafx.fxml;
    exports pl.kafara.AES;
}