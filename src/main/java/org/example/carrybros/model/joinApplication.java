package org.example.carrybros.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.carrybros.HelloApplication;

import java.io.IOException;

public class joinApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("JoinGame.fxml"));
        Parent root = loader.load();

        // Set the stage title
        primaryStage.setTitle("Join Game");

        // Create and set the scene
        Scene scene = new Scene(root, 740, 740);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
