package org.example.carrybros.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.carrybros.HelloApplication;

public class LoginApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Parent root = loader.load();

        // Set the stage title
        primaryStage.setTitle("Login - Carry The Car");

        // Create and set the scene
        Scene scene = new Scene(root, 740, 740);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
