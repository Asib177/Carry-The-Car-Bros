package org.example.carrybros.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;
import org.example.carrybros.entity.Bullet;
import org.example.carrybros.entity.Player;
import org.example.carrybros.tile.TileManager;

import java.util.ArrayList;
import java.util.List;

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

    private List<Bullet> bullets = new ArrayList<>();
    public double mouseX;
    public double mouseY;

    public GamePanel() {
        super();
        this.setWidth(screenWidth);
        this.setHeight(screenHeight);
        this.setFocusTraversable(true);
        this.setOnKeyPressed(keyH::handle);
        this.setOnKeyReleased(keyH::handle);
        setupMouseHandlers();
        startGameThread();
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

    public void setupMouseHandlers() {
        // Update gun rotation with mouse movement
        this.setOnMouseMoved((MouseEvent event) -> {
            mouseX = event.getX();
            mouseY = event.getY();
        });

        // Shoot bullets when mouse is clicked
        this.setOnMouseClicked((MouseEvent event) -> {
            bullets.add(new Bullet(player.gunX, player.gunY, player.gunAngle));
        });
    }

    public void update(double deltaTime) {
        tileM.updateCar();
        player.update();
        bullets.forEach(bullet -> bullet.update(deltaTime)); // Update bullets
        bullets.removeIf(bullet -> bullet.isOutOfBounds(screenWidth, screenHeight)); // Remove out-of-bounds bullets
        updateCamera();
    }

    public void updateCamera() {
        centeredCameraX = (int) (player.playerXposition - screenWidth / 2 + player.solidArea.getWidth() / 2);
        centeredCameraY = (int) (player.playerYposition - screenHeight / 2 + player.solidArea.getHeight() / 2);

        cameraX = Math.max(0, Math.min(centeredCameraX, worldWidth - screenWidth));
        cameraY = Math.max(0, Math.min(centeredCameraY, worldHeight - screenHeight));
    }

    public void draw() {
        GraphicsContext gc = getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, screenWidth, screenHeight);

        tileM.draw(gc);
        player.draw(gc);
        bullets.forEach(bullet -> bullet.draw(gc)); // Draw all bullets
    }

    public void resizeCanvas(double newWidth, double newHeight) {
        screenWidth = (int) newWidth;
        screenHeight = (int) newHeight;

        this.setWidth(newWidth);
        this.setHeight(newHeight);
    }
}
