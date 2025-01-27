package org.example.carrybros.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.carrybros.HelloApplication;

public class joinApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("joinGame.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 740, 740);
        stage.setTitle("Join Page");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
