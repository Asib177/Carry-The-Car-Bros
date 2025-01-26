package org.example.carrybros.model;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
    public void initialize() {
        // Simulating dynamic updates
        setUpControls();
    }

    private void setUpControls() {
        // Example: Simulating dynamic key press updates
        upStatus.setText(": W");
        downStatus.setText(": S");
        leftStatus.setText(": A");
        rightStatus.setText(": D");
        fireStatus.setText(": F");
    }
}
