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
        primaryStage.getIcons().add(new Image(getClass().getResource("/images/gun.png").toString()));

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
