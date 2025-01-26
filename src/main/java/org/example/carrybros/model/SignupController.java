package org.example.carrybros.model;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.example.carrybros.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField fullnameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleSignup() {
        String username = usernameField.getText().trim();
        String fullName = fullnameField.getText().trim();
        String password = passwordField.getText();
        int score = 0;

        if (username.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
            showAlert("Error", "All fields must be filled!", AlertType.ERROR);
            return;
        }

        if (insertIntoDatabase(username, fullName, score, password)) {
            showAlert("Success", "Signup successful for user: " + username, AlertType.INFORMATION);
        } else {
            showAlert("Error", "Signup failed! Username might already exist.", AlertType.ERROR);
        }
    }

    private boolean insertIntoDatabase(String username, String fullName, int score, String password) {
        String query = "INSERT INTO player (username, fullname, score, userPassword) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, fullName);
            preparedStatement.setInt(3, score);
            preparedStatement.setString(4, password);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
