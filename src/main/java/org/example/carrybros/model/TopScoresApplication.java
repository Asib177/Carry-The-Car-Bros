package org.example.carrybros.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.carrybros.HelloApplication;

public class TopScoresApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("TopScores.fxml"));
        Parent root = loader.load();

        // Set the title for the stage
        primaryStage.setTitle("Top Scores - Carry The Car");

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
