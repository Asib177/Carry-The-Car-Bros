package org.example.carrybros.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.carrybros.HelloApplication;

public class winApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file for the win screen
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Win.fxml"));
            AnchorPane root = loader.load();

            // Set the scene with the loaded root
            Scene scene = new Scene(root);
            primaryStage.setTitle("Carry The Car - You Win!");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
