package org.example.carrybros.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.example.carrybros.HelloApplication;
import org.example.carrybros.database.DBConnection;

import java.io.IOException;
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
    private Button loginInstead;

    @FXML
    private Button signupButton;

    @FXML
    public void handleLogin(ActionEvent event){
        try {
            login();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void handleSignup() {
        String username = usernameField.getText().trim();
        String fullName = fullnameField.getText().trim();
        String password = passwordField.getText();
        int score = 0;

        if (username.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
            System.out.println("All fields must be filled!");
            return;
        }

        if (insertIntoDatabase(username, fullName, score, password)) {
            System.out.println("Insertion Visited!!!!!!!!!");
                try {
                    Options();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        } else {
            System.out.println("Problem occured!!!!!!");
        }
    }

    private boolean insertIntoDatabase(String username, String fullName, int score, String password) {
        String query = "INSERT INTO player (username, fullName, score, userPassword) VALUES (?, ?, ?, ?)";

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
    private void Options() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("options.fxml"));
        Parent root = fxmlLoader.load();


        Stage stage = (Stage) signupButton.getScene().getWindow();


        stage.setScene(new Scene(root, 740, 740));
        stage.setTitle("Options");
        stage.show();
    }

    private void login() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();


        Stage stage = (Stage) loginInstead.getScene().getWindow();


        stage.setScene(new Scene(root, 740, 740));
        stage.setTitle("Login");
        stage.show();
    }

}
