package pl.kafara.AES;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private final Aes aes = new Aes();
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

    @FXML
    private TextArea keyField;

    @FXML
    private Button generateKeyButton;

    @FXML
    private Button loadKeyButton;

    @FXML
    private Button saveKeyButton;

    @FXML
    private RadioButton rb128, rb192, rb256;

    private byte[] decryptedFile;

    private byte[] encryptedFile;

    private byte[] key;

    private String byteToString(byte[] arr) {
        StringBuilder result = new StringBuilder();
        for (byte b : arr) {
            result.append(String.format("%x", Byte.toUnsignedInt(b)));
        }
        return result.toString();
    }

    private void loadFile(int type, TextArea fileField) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
        File f = fileChooser.showOpenDialog(null);
        if (f != null) {
            String fileName = f.getAbsolutePath();
            try (InputStream inputStream = new FileInputStream(fileName)) {
                byte[] file = inputStream.readAllBytes();
                if (type == 0) {
                    decryptedFile = file;
                } else if (type == 1) {
                    encryptedFile = file;
                } else if (type == 2) {
                    key = file;
                }
                fileField.setText(byteToString(file));
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
                    } else if (type == 1) {
                        outputStream.write(encryptedFile);
                    } else if (type == 2) {
                        outputStream.write(key);
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
        saveDecryptedFileButton.setDisable(false);
        if (key != null)
            encryptButton.setDisable(false);
    }

    @FXML
    public void saveDecryptedFile(ActionEvent event) {
        saveFile(0);
    }

    @FXML
    public void loadEncryptedFile(ActionEvent event) {
        loadFile(1, encryptedFileField);
        saveEncryptedFileButton.setDisable(false);
        if (key != null)
            decryptButton.setDisable(false);
    }

    @FXML
    public void saveEncryptedFile(ActionEvent event) {
        saveFile(1);
    }

    @FXML
    public void encrypt() throws Exception {
        if (decryptedFile.length > 0) {
            List<Byte> res = aes.encode(decryptedFile, key);
            encryptedFile = new byte[res.size()];
            int i = 0;
            for (byte b : res) {
                encryptedFile[i] = b;
                i++;
            }
            encryptedFileField.setText(byteToString(encryptedFile));
            saveEncryptedFileButton.setDisable(false);
            decryptButton.setDisable(false);
        }
    }

    @FXML
    public void decrypt() throws Exception {
        if (encryptedFile.length > 0) {
            List<Byte> res = aes.decode(encryptedFile, key);
            decryptedFile = new byte[res.size()];
            int i = 0;
            for (byte b : res) {
                decryptedFile[i] = b;
                i++;
            }
            decryptedFileField.setText(byteToString(decryptedFile));
            encryptButton.setDisable(false);
            saveDecryptedFileButton.setDisable(false);
        }
    }

    @FXML
    public void loadKey() {
        loadFile(2, keyField);
        saveKeyButton.setDisable(false);
        encryptButton.setDisable(false);
        decryptButton.setDisable(false);
        if (key.length != 16 && key.length != 24 && key.length != 32 || key == null) {
            saveKeyButton.setDisable(true);
            encryptButton.setDisable(true);
            decryptButton.setDisable(true);
            key = null;
            keyField.setText("");
        }

        if (encryptedFile == null || encryptedFile.length == 0) {
            encryptButton.setDisable(true);
        }

        if (decryptedFile == null || decryptedFile.length == 0) {
            decryptButton.setDisable(true);
        }
    }

    @FXML
    public void generateKey() {
        Random random = new Random();
        if (rb128.isSelected()) {
            key = new byte[16];
            random.nextBytes(key);
        } else if (rb192.isSelected()) {
            key = new byte[24];
            random.nextBytes(key);
        } else {
            key = new byte[32];
            random.nextBytes(key);
        }
        keyField.setText(byteToString(key));
        saveKeyButton.setDisable(false);
        encryptButton.setDisable(false);
        decryptButton.setDisable(false);
    }

    @FXML
    public void saveKey() {
        saveFile(2);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decryptedFileField.setWrapText(true);
        decryptedFileField.setEditable(false);
        encryptedFileField.setWrapText(true);
        encryptedFileField.setEditable(false);
        keyField.setEditable(false);
        saveKeyButton.setDisable(true);
        saveDecryptedFileButton.setDisable(true);
        saveEncryptedFileButton.setDisable(true);
        encryptButton.setDisable(true);
        decryptButton.setDisable(true);
    }
}