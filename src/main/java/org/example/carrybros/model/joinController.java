package org.example.carrybros.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.carrybros.model.Logout;

public class joinController {

    @FXML
    private TextField ipAddressField;

    @FXML
    private Button joinButton;

    @FXML
    private Button quitButton1;

    @FXML
    public void initialize() {
        joinButton.setOnAction(event -> handleJoinButton());
        quitButton1.setOnAction(event -> handleLogout());
    }

    private void handleJoinButton() {
        String ipAddress = ipAddressField.getText().trim();

        if (ipAddress.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "IP Address cannot be empty.");
            return;
        }


    }

    private boolean isValidIPAddress(String ipAddress) {
        // A simple regex for IP address validation
        String ipPattern = "";
        return ipAddress.matches(ipPattern);
    }



    
    private void handleLogout() {
        Logout.main();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
