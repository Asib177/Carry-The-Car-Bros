package org.example.carrybros.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.carrybros.HelloApplication;

import java.io.IOException;

public class ControlsController {

    @FXML
    private Label upStatus;
    @FXML
    private Label downStatus;
    @FXML
    private Label leftStatus;
    @FXML
    private Label rightStatus;
    @FXML
    private Label fireStatus;

    @FXML
    private Button optionsButton;

    @FXML
    public void initialize() {
        // Simulating dynamic updates
        setUpControls();

        optionsButton.setOnAction(ActionEvent->{
            try {
                options();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setUpControls() {
        // Example: Simulating dynamic key press updates
        upStatus.setText(": W");
        downStatus.setText(": S");
        leftStatus.setText(": A");
        rightStatus.setText(": D");
        fireStatus.setText(": F");
    }
    private void options() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("options.fxml"));
        Parent root = fxmlLoader.load();


        Stage stage = (Stage) optionsButton.getScene().getWindow();


        stage.setScene(new Scene(root, 740, 740));
        stage.setTitle("Options");
        stage.show();
    }
}
