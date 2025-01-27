package org.example.carrybros.model;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.example.carrybros.HelloApplication;


import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the login.fxml file
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 740, 740);

        primaryStage.setTitle("Carry The Car - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
