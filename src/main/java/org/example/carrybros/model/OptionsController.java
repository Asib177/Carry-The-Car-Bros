package org.example.carrybros.model;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;  // Replace with the actual path to your Main class (or relevant class)
import org.example.carrybros.HelloApplication;

import java.io.IOException;

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
        highScoreButton.setOnAction(e -> {
            try {
                viewHighScores();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        controlsButton.setOnAction(e -> viewControls());
    }

    private void startGame() {
        System.out.println("Game Started");

    }

    private void viewHighScores() throws IOException {
        System.out.println("Viewing High Scores");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("options.fxml"));
        Parent root = fxmlLoader.load();


        Stage stage = (Stage) highScoreButton.getScene().getWindow();


        stage.setScene(new Scene(root, 740, 740));
        stage.setTitle("Options");
        stage.show();
    }

    private void viewControls() {
        System.out.println("Viewing Controls");
    }
}
