package org.example.carrybros.model;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.carrybros.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.carrybros.database.DBConnection;

import java.io.IOException;
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
    private Button signupButton;

    @FXML
    public void initialize(){
        signupButton.setOnAction(event -> {
            try {
                signup();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        loginButton.setOnAction(actionEvent -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();
            if (username.isEmpty() || password.isEmpty()) {
                System.out.println("All fields must be filled!");
                return;
            }
            boolean result = authenticate(username, password);
            if(result){
                try {
                    Options();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                usernameField.setText("");
                passwordField.setText("");
            }
        });
    }

    public void signup() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup.fxml"));
        Parent root = fxmlLoader.load();


        Stage stage = (Stage) signupButton.getScene().getWindow();


        stage.setScene(new Scene(root, 740, 740));
        stage.setTitle("Signup");
        stage.show();
    }

    private boolean authenticate(String username, String password) {
        String query = "SELECT * FROM player WHERE username = ? AND userPassword = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

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

    private void Options() throws IOException, NullPointerException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("options.fxml"));
        Parent root = fxmlLoader.load();


        Stage stage = (Stage) loginButton.getScene().getWindow();


        stage.setScene(new Scene(root, 740, 740));
        stage.setTitle("Options");
        stage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
