package pl.kafara.AES;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    Aes aes = new Aes();
    @FXML
    private Button loadDecryptedFileButton;

    @FXML
    private Button saveDecryptedFileButton;

    @FXML
    private Button loadEncryptedFileButton;

    @FXML
    private Button saveEncryptedFileButton;

    @FXML
    private Button encryptButton;

    @FXML
    private Button decryptButton;

    @FXML
    private TextArea decryptedFileField;

    @FXML
    private TextArea encryptedFileField;

    private byte[] decryptedFile;

    private byte[] encryptedFile;

    private byte[] key = "z$C&F)J@NcRfUjXn".getBytes();

    private void loadFile(int type, TextArea fileField) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
        File f = fileChooser.showOpenDialog(null);
        if (f != null) {
            String fileName = f.getAbsolutePath();
            try (InputStream inputStream = new FileInputStream(fileName)) {
                String output = "";
                byte[] file = inputStream.readAllBytes();
                if (type == 0) {
                    decryptedFile = file;
                } else {
                    encryptedFile = file;
                }
                for (byte b : file) {
                    output += String.format("%x", Byte.toUnsignedInt(b));
                }
                fileField.setText(output);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void saveFile(int type) {
        FileChooser fileChooser = new FileChooser();
        File f = fileChooser.showSaveDialog(new Stage());
        if (f != null) {
            String fileName = f.getAbsolutePath();
            try (OutputStream outputStream = new FileOutputStream(fileName)) {
                try {
                    if (type == 0) {
                        outputStream.write(decryptedFile);
                    } else {
                        outputStream.write(encryptedFile);
                    }
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    public void loadDecryptedFile(ActionEvent event) {
        loadFile(0, decryptedFileField);
    }

    @FXML
    public void saveDecryptedFile(ActionEvent event) {
        saveFile(0);
    }

    @FXML
    public void loadEncryptedFile(ActionEvent event) {
        loadFile(1, encryptedFileField);
    }

    @FXML
    public void saveEncryptedFile(ActionEvent event) {
        saveFile(1);
    }

    @FXML
    public void encrypt() throws Exception {
        if (decryptedFile.length > 0) {
            System.out.println("tak");
            List<Byte> res = aes.encode(decryptedFile, key);
            encryptedFile = new byte[res.size()];
            String output = "";
            int i = 0;
            for (byte b : res) {
                output += String.format("%x", Byte.toUnsignedInt(b));
                encryptedFile[i] = b;
                i++;
            }
            encryptedFileField.setText(output);
        }
    }

    @FXML
    public void decrypt() throws Exception {
        if (encryptedFile.length > 0) {
            System.out.println("nie");
            List<Byte> res = aes.decode(encryptedFile, key);
            decryptedFile = new byte[res.size()];
            String output = "";
            int i = 0;
            for (byte b : res) {
                output += String.format("%x", Byte.toUnsignedInt(b));
                decryptedFile[i] = b;
                i++;
            }
            decryptedFileField.setText(output);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decryptedFileField.setWrapText(true);
        decryptedFileField.setEditable(false);
        encryptedFileField.setWrapText(true);
        encryptedFileField.setEditable(false);
    }
}