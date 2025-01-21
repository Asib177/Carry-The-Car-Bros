package org.example.carrybros.model;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.carrybros.net.Client;
import org.example.carrybros.net.Server;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Start server in a separate thread
        new Thread(() -> {
            new Server(); // Start the server
        }).start();

        primaryStage.setTitle("Carry The Car - Game");
        primaryStage.getIcons().add(new Image(getClass().getResource("/images/Icon.png").toString()));



        // Start client in the main thread or another thread
        new Thread(() -> {
            Client client = new Client(); // Create client instance
            client.startClient(); // Start the client
        }).start();

        GamePanel gamePanel = new GamePanel();
        Group root = new Group(gamePanel);

        Scene scene = new Scene(root, gamePanel.screenWidth, gamePanel.screenHeight);
        primaryStage.setScene(scene);

        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            gamePanel.resizeCanvas(newVal.doubleValue(), scene.getHeight());
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            gamePanel.resizeCanvas(scene.getWidth(), newVal.doubleValue());
        });

        primaryStage.show();
        gamePanel.startGameThread();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
