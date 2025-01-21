package org.example.carrybros.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;
import org.example.carrybros.entity.Player;
import org.example.carrybros.net.Client;
import org.example.carrybros.tile.TileManager;

import java.awt.event.MouseEvent;

public class GamePanel extends Canvas {

    public final int tileSize = 48;

    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public int screenWidth = tileSize * maxScreenCol;
    public int screenHeight = tileSize * maxScreenRow;

    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    public int cameraX = 0;
    public int cameraY = 0;
    int centeredCameraX;
    int centeredCameraY;

    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);

    private Client client;

    public GamePanel() {
        super();
        this.setWidth(screenWidth);
        this.setHeight(screenHeight);
        this.setFocusTraversable(true);
        this.setOnKeyPressed(keyH::handle);
        this.setOnKeyReleased(keyH::handle);

        // Mouse movement listener to update gun angle
        this.setOnMouseMoved(this::handleMouseMovement);

        startGameThread();
    }

    private void handleMouseMovement(javafx.scene.input.MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getSceneX(); // Use mouseEvent instead of event
        double mouseY = mouseEvent.getSceneY(); // Use mouseEvent instead of event

        // Pass the mouse coordinates to the player to update the gun angle
        player.updateGunPosition(mouseX, mouseY);
    }

    // Process mouse input and send the game action (if multiplayer)
    private void processMouseInput(int mouseX, int mouseY) {
        // Example: Send shooting action to server (optional)
        GameAction action = new GameAction("shoot", mouseX, mouseY, true);
        if (client != null) {
            client.sendGameAction(action);
        }
    }

    public int getMaxWorldCol() {
        return maxWorldCol;
    }

    public int getMaxWorldRow() {
        return maxWorldRow;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void startGameThread() {
        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;
                update(deltaTime);
                draw();
            }
        };
        gameLoop.start();
    }

    public void update(double deltaTime) {
        tileM.updateCar();
        player.update();
        updateCamera();
    }

    public void updateCamera() {
        centeredCameraX = (int) (player.worldX - screenWidth / 2 + player.solidArea.getWidth() / 2);
        centeredCameraY = (int) (player.worldY - screenHeight / 2 + player.solidArea.getHeight() / 2);

        cameraX = Math.max(0, Math.min(centeredCameraX, worldWidth - screenWidth));
        cameraY = Math.max(0, Math.min(centeredCameraY, worldHeight - screenHeight));
    }

    public void draw() {
        GraphicsContext gc = getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, screenWidth, screenHeight);

        tileM.draw(gc);
        player.draw(gc);
    }

    public void resizeCanvas(double newWidth, double newHeight) {
        screenWidth = (int) newWidth;
        screenHeight = (int) newHeight;

        this.setWidth(newWidth);
        this.setHeight(newHeight);
    }
}
