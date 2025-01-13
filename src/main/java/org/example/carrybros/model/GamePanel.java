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
    public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // World Map Settings
    public final int maxWorldCol = 23;
    public final int maxWorldRow = 20;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // Game Components
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);

    private AnimationTimer gameLoop;

    public GamePanel() {
        super(768, 576); // Set the canvas size to screen width and height
        this.setFocusTraversable(true);
        this.setOnKeyPressed(keyH::keyPressed);
        this.setOnKeyReleased(keyH::keyReleased);

        startGameThread();
    }

    public void startGameThread() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw();
            }
        };
        gameLoop.start();
    }

    public void update() {
        player.update();
    }

    public void draw() {
        GraphicsContext gc = getGraphicsContext2D();

        // Clear the screen
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, screenWidth, screenHeight);

        // Draw tiles and player
        tileM.draw(gc);
        player.draw(gc);
    }
}
