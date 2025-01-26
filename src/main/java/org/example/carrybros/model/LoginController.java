package org.example.carrybros.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.carrybros.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Perform validation
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Username and password cannot be empty.");
            return;
        }

        // Check credentials in the database
        if (authenticate(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + username + "!");
            // Navigate to the next screen or main menu if needed
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    private boolean authenticate(String username, String password) {
        String query = "SELECT * FROM player WHERE username = ? AND userPassword = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters
            preparedStatement.setString(2, username);
            preparedStatement.setString(5, password);

            // Execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a record exists
            return resultSet.next();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while connecting to the database.");
        }

        return false;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
