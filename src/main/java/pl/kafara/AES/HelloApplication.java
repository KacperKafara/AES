package pl.kafara.AES;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
//        launch();
        Aes aes = new Aes();
        String msg = "Hello, World!";
        byte[] msgToBytes = msg.getBytes();
        List<Byte> res = aes.encode(msgToBytes, "a000a000a000a000".getBytes());
        byte[] b = new byte[res.size()];
        for (int i = 0; i < res.size(); i++) {
            b[i] = res.get(i);
        }
        System.out.println(Arrays.deepToString(aes.generateKeyState("a000a000a000a000a000a000".getBytes())));
        System.out.println(Arrays.toString("a000a000a000a000a000a000".getBytes()));
//        System.out.println(new String(b));
    }
}