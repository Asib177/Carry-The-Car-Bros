package org.example.carrybros.model;

import javafx.event.ActionEvent;
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

    @FXML
    private Button logoutButton;

    // Initialize method to set button actions
    @FXML
    public void initialize() {
        // Set action for Play Button
        playButton.setOnAction(e -> {
            try {
                startGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Set action for High Score Button
        highScoreButton.setOnAction(e -> {
            try {
                viewHighScores();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        controlsButton.setOnAction(e -> {
            try {
                viewControls();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        logoutButton.setOnAction(ActionEvent->{
            try {
                logout();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void startGame() throws IOException{
        System.out.println("Game Started");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("JoinGame.fxml"));
        Parent root = fxmlLoader.load();


        Stage stage = (Stage) playButton.getScene().getWindow();


        stage.setScene(new Scene(root, 740, 740));
        stage.setTitle("Join Game");
        stage.show();

    }

    private void viewHighScores() throws IOException {
        System.out.println("Viewing High Scores");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("TopScores.fxml"));
        Parent root = fxmlLoader.load();


        Stage stage = (Stage) highScoreButton.getScene().getWindow();


        stage.setScene(new Scene(root, 740, 740));
        stage.setTitle("High Scores");
        stage.show();
    }

    private void viewControls() throws IOException{
        System.out.println("Viewing Controls");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Controls.fxml"));
        Parent root = fxmlLoader.load();


        Stage stage = (Stage) controlsButton.getScene().getWindow();


        stage.setScene(new Scene(root, 740, 740));
        stage.setTitle("Controls");
        stage.show();
    }
    private void logout() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();


        Stage stage = (Stage) logoutButton.getScene().getWindow();


        stage.setScene(new Scene(root, 740, 740));
        stage.setTitle("Options");
        stage.show();
    }
}
