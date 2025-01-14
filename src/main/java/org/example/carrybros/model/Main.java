package org.example.carrybros.model;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Carry The Car - Game");
        primaryStage.getIcons().add(new Image(getClass().getResource("/images/Icon.png").toString()));

        // Create the GamePanel
        GamePanel gamePanel = new GamePanel();

        // Use Group to avoid layout adjustments
        Group root = new Group(gamePanel);

        // Wrap the root in a Scene
        Scene scene = new Scene(root, gamePanel.screenWidth, gamePanel.screenHeight);
        primaryStage.setScene(scene);

        // Prevent resizing
        //primaryStage.setResizable(false);

        // Listen for window resizing
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            gamePanel.resizeCanvas(newVal.doubleValue(), scene.getHeight());
        });

        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            gamePanel.resizeCanvas(scene.getWidth(), newVal.doubleValue());
        });

        // Show the stage
        primaryStage.show();

        // Start the game loop
        //gamePanel.startGameThread();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
