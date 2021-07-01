package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Регистрация");
        primaryStage.setScene(new Scene(root, 350, 240));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/reg.jpg")));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
