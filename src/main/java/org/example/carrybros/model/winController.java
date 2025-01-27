package org.example.carrybros.model;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class winController {

    @FXML
    private Button quitButton;

    @FXML
    private Button playAgainButton;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label killedLabel;

    @FXML
    private Label regeneratedLabel;

    private int score = 70; // Example score, you can dynamically set this
    private int killed = 7; // Example number, you can dynamically set this
    private int regenerated = 7; // Example number, you can dynamically set this

    // Initialize the labels with the actual score values
    @FXML
    public void initialize() {
        scoreLabel.setText(String.valueOf(score));
        killedLabel.setText(String.valueOf(killed));
        regeneratedLabel.setText(String.valueOf(regenerated));

        // Set action for Play Again Button
        playAgainButton.setOnAction(e -> {
            try {
                playAgain();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Set action for Quit Button
        quitButton.setOnAction(e -> quitGame());
    }

    // Play Again action (reload the game or reset the game state)
    private void playAgain() throws IOException {
        try {
            // Load the FXML for the main menu or the Main.java scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.java")); // Replace with the actual path to your Main.fxml file
            Parent root = fxmlLoader.load();

            // Get the current stage from any component
            Stage stage = (Stage) playAgainButton.getScene().getWindow(); // Replace `playAgainButton` with any UI component in this controller

            // Set the new scene
            stage.setScene(new Scene(root));
            stage.setTitle("Main Menu"); // Optional: Set the title of the window
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load the main scene.");
        }
    }

    // Quit action (exit the application)
    private void quitGame() {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }
}
