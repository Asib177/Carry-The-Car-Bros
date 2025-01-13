package org.example.carrybros.model;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Carry The Car - Game");
        primaryStage.getIcons().add(new Image(getClass().getResource("/images/Icon.png").toString()));

        // Create the GamePanel
        GamePanel gamePanel = new GamePanel();

        // Wrap the GamePanel in a StackPane (or any Parent subclass)
        StackPane root = new StackPane(gamePanel);

        // Wrap the root in a Scene
        Scene scene = new Scene(root, gamePanel.screenWidth, gamePanel.screenHeight);
        primaryStage.setScene(scene);

        // Prevent resizing
        //primaryStage.setResizable(false);

        // Show the stage
        primaryStage.show();

        // Start the game loop
        gamePanel.startGameThread();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
