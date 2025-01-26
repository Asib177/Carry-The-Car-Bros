package org.example.carrybros.model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;  // Replace with your main app class

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
        playAgainButton.setOnAction(e -> playAgain());

        // Set action for Quit Button
        quitButton.setOnAction(e -> quitGame());
    }

    // Play Again action (reload the game or reset the game state)
    private void playAgain() {
        System.out.println("Playing again...");
        // For example, restart the game logic or navigate to the main menu
    }

    // Quit action (exit the application)
    private void quitGame() {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }
}
