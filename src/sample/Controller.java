package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tools.DataBase;
import tools.Validation;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchor;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label infoLabel;

    @FXML
    void createAccountFunction(ActionEvent event) {
        if(Validation.isEmptyField(loginField,passwordField)) return;
        if(DataBase.isUser(loginField.getText(), passwordField.getText())){
            infoLabel.setText("Такой пользователь уже существует");
            return;
        }
        DataBase.addUser(loginField.getText(), passwordField.getText());
        infoLabel.setText("Пользователь удачно создан.");
        Validation.clearField(loginField,passwordField);
    }

    @FXML
    void deleteAccountFunction(ActionEvent event) {
        if(Validation.isEmptyField(loginField,passwordField)) return;
        if(!DataBase.isUser(loginField.getText(), passwordField.getText())){
            infoLabel.setText("Такого пользователя не существует.");
            return;
        }
        DataBase.deleteUser(infoLabel, loginField.getText());
        Validation.clearField(loginField,passwordField);
    }

    @FXML
    void logInFunction(ActionEvent event) {
        if(Validation.isEmptyField(loginField,passwordField)) return;
        if(!DataBase.isUser(loginField.getText(), passwordField.getText())){
            infoLabel.setText("Такого пользователя не существует.");
            return;
        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/main/scene.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root,712,493));
            stage.setTitle("To Do List");
            stage.setResizable(false);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/reg.jpg")));
            stage.show();
            ((Stage) anchor.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {

    }
}
