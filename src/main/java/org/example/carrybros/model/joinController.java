package org.example.carrybros.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.carrybros.HelloApplication;

import java.io.IOException;

public class joinController {

    @FXML
    private TextField ipAddressField;

    @FXML
    private Button joinButton;

    @FXML
    private Button logOut;

    @FXML
    public void initialize() {
        joinButton.setOnAction(event -> {
            try {
                handleJoinButton();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        logOut.setOnAction(event -> {
            try {
                handleLogout();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        joinButton.setOnAction(ActionEvent ->{
            //handleJoinButton();
        });
    }

    private void handleJoinButton() throws IOException{

    }





    private void handleLogout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();


        Stage stage = (Stage) logOut.getScene().getWindow();


        stage.setScene(new Scene(root, 740, 740));
        stage.setTitle("Login");
        stage.show();

    }


}
