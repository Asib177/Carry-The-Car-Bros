package org.example.carrybros.model;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.carrybros.network.GameClient;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Carry The Car - Game");
        primaryStage.getIcons().add(new Image(getClass().getResource("/images/Icon.png").toString()));

        // Initialize the GamePanel for the first player
        GamePanel gamePanel1 = new GamePanel(1); // Pass player ID (1 for first player)
        Group root1 = new Group(gamePanel1);

        // Create the scene for the first player
        Scene scene1 = new Scene(root1, gamePanel1.screenWidth, gamePanel1.screenHeight);
        primaryStage.setScene(scene1);

        // Add resize listeners for the first player
        scene1.widthProperty().addListener((obs, oldVal, newVal) -> {
            gamePanel1.resizeCanvas(newVal.doubleValue(), scene1.getHeight());
        });
        scene1.heightProperty().addListener((obs, oldVal, newVal) -> {
            gamePanel1.resizeCanvas(scene1.getWidth(), newVal.doubleValue());
        });

        // Show the stage for the first player
        primaryStage.show();

        // Start the game thread for the first player
        gamePanel1.startGameThread();

        // Initialize and start the GameClient for the first player
        GameClient gameClient1 = new GameClient(gamePanel1);
        gameClient1.start("localhost", 8080);

        // Create a new stage for the second player
        Stage secondStage = new Stage();
        secondStage.setTitle("Carry The Car - Game (Player 2)");
        secondStage.getIcons().add(new Image(getClass().getResource("/images/Icon.png").toString()));

        // Initialize the GamePanel for the second player
        GamePanel gamePanel2 = new GamePanel(2); // Pass player ID (2 for second player)
        Group root2 = new Group(gamePanel2);

        // Create the scene for the second player
        Scene scene2 = new Scene(root2, gamePanel2.screenWidth, gamePanel2.screenHeight);
        secondStage.setScene(scene2);

        // Add resize listeners for the second player
        scene2.widthProperty().addListener((obs, oldVal, newVal) -> {
            gamePanel2.resizeCanvas(newVal.doubleValue(), scene2.getHeight());
        });
        scene2.heightProperty().addListener((obs, oldVal, newVal) -> {
            gamePanel2.resizeCanvas(scene2.getWidth(), newVal.doubleValue());
        });

        // Show the stage for the second player
        secondStage.show();

        // Start the game thread for the second player
        gamePanel2.startGameThread();

        // Initialize and start the GameClient for the second player
        GameClient gameClient2 = new GameClient(gamePanel2);
        gameClient2.start("localhost", 12345);
    }

    public static void main(String[] args) {
        launch(args);
    }
}