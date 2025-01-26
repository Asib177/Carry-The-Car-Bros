package org.example.carrybros.model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;  // Replace with the actual path to your Main class (or relevant class)

public class OptionsController {

    @FXML
    private Button playButton;

    @FXML
    private Button highScoreButton;

    @FXML
    private Button controlsButton;

    // Initialize method to set button actions
    @FXML
    public void initialize() {
        // Set action for Play Button
        playButton.setOnAction(e -> startGame());

        // Set action for High Score Button
        highScoreButton.setOnAction(e -> viewHighScores());

        // Set action for Controls Button
        controlsButton.setOnAction(e -> viewControls());
    }

    // Start the game (add game logic here or navigate to a new screen)
    private void startGame() {
        System.out.println("Game Started");
        // For example, you could change scenes or navigate to the game screen
    }

    // Navigate to high score page
    private void viewHighScores() {
        System.out.println("Viewing High Scores");
        // You can switch to a high score page here
    }

    // Navigate to controls page
    private void viewControls() {
        System.out.println("Viewing Controls");
        // You can switch to a controls page here
    }
}
