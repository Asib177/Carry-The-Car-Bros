package org.example.carrybros.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;
import org.example.carrybros.entity.Player;
import org.example.carrybros.tile.TileManager;

public class GamePanel extends Canvas {

    // Screen settings
    public final int tileSize = 48; // 48x48 tiles

    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    // World Map Settings
//    public final int maxWorldCol = 23;
//    public final int maxWorldRow = 20;
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public int screenWidth = tileSize * maxScreenCol; // 960 pixels
    public int screenHeight = tileSize * maxScreenRow; // 576 pixels

    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    public int cameraX = 0;
    public int cameraY = 0;

    // Game Components
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);

    public GamePanel() {
//        super(768, 576); // Set the canvas size to screen width and height
        // Use default Canvas constructor
        super();

        // Set canvas size explicitly
        this.setWidth(screenWidth);
        this.setHeight(screenHeight);

        // Enable keyboard focus
        this.setFocusTraversable(true);
        this.setOnKeyPressed(keyH::handle);
        this.setOnKeyReleased(keyH::handle);

        startGameThread();
    }

    // Getters for world dimensions and tile size
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

                double deltaTime = (now - lastTime) / 1_000_000_000.0; // Convert nanoseconds to seconds
                lastTime = now;
                update(deltaTime);
                draw();
            }
        };
        gameLoop.start();
    }

    public void update(double deltaTime) {
        tileM.updateCar();
        player.update(deltaTime);
        updateCamera();  // Update the camera position
    }

    public void updateCamera() {
        // Center the camera on the player
        cameraX = player.worldX - player.screenX / 2;
        cameraY = player.worldY - player.screenY / 2;

        // Ensure the camera stays within the world boundaries
        cameraX = Math.max(0, Math.min(cameraX, worldWidth - screenWidth));
        cameraY = Math.max(0, Math.min(cameraY, worldHeight - screenHeight));
    }

    public void draw() {
        GraphicsContext gc = getGraphicsContext2D();

        // Clear the screen
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, screenWidth, screenHeight);

        // Draw tiles, player, etc.
        tileM.draw(gc);
        player.draw(gc);
    }

    // Method to resize the canvas
    public void resizeCanvas(double newWidth, double newHeight) {
        screenWidth = (int) newWidth;
        screenHeight = (int) newHeight;

        this.setWidth(newWidth);
        this.setHeight(newHeight);
    }
}