package org.example.carrybros.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;
import javafx.scene.shape.Rectangle;
import org.example.carrybros.entity.Bullet;
import org.example.carrybros.entity.Player;
import org.example.carrybros.network.GameClient;
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

    public GameClient gameClient;
    public CollisionChecker cChecker;
    int centeredCameraX;
    int centeredCameraY;

    private int otherPlayerX = -1; // Default position (off-screen)
    private int otherPlayerY = -1;

    public TileManager tileM;
    public KeyHandler keyH1; // For player 1
    public KeyHandler keyH2; // For player 2
    public Player player1; // First player
    public Player player2; // Second player

    private List<Bullet> bullets = new ArrayList<>();
    public double mouseX;
    public double mouseY;

    private int playerId; // Player ID (1 or 2)

    public GamePanel(int playerId) {
        super();
        this.playerId = playerId;

        this.setWidth(screenWidth);
        this.setHeight(screenHeight);
        this.setFocusTraversable(true);

        // Initialize the CollisionChecker
        cChecker = new CollisionChecker(this);

        // Initialize the TileManager
        tileM = new TileManager(this);

        // Initialize both KeyHandlers
        keyH1 = new KeyHandler();
        keyH2 = new KeyHandler();

        // Initialize the KeyHandler for the current player
        if (playerId == 1) {
            this.setOnKeyPressed(keyH1::handle);
            this.setOnKeyReleased(keyH1::handle);
        } else if (playerId == 2) {
            this.setOnKeyPressed(keyH2::handle);
            this.setOnKeyReleased(keyH2::handle);
        }

        setupMouseHandlers();

        // Initialize the GameClient
        gameClient = new GameClient(this);
        gameClient.start("localhost", 8080);

        // Initialize the Players with the correct KeyHandler
        if (playerId == 1) {
            player1 = new Player(this, keyH1); // Pass keyH1 for player 1
            player2 = new Player(this, keyH2); // Pass keyH2 for player 2 (even though it's player 1's panel)
        } else if (playerId == 2) {
            player1 = new Player(this, keyH1); // Pass keyH1 for player 1 (even though it's player 2's panel)
            player2 = new Player(this, keyH2); // Pass keyH2 for player 2
        }

        // Position the second player slightly away from the first player
        player2.playerXposition = player1.playerXposition + 500; // Example: 100 pixels to the right
        player2.playerYposition = player1.playerYposition;

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
            bullets.add(new Bullet(player1.gunX, player1.gunY, player1.gunAngle)); // Player 1 shoots
        });
    }

    public void update(double deltaTime) {
        tileM.updateCar();
        player1.update(); // Update the first player
        player2.update(); // Update the second player
        bullets.forEach(bullet -> bullet.update(deltaTime)); // Update bullets
        bullets.removeIf(bullet -> bullet.isOutOfBounds(screenWidth, screenHeight)); // Remove out-of-bounds bullets
        updateCamera();
    }

    // Add this method to update another player's position
    public void updateOtherPlayerPosition(int x, int y) {
        this.otherPlayerX = x;
        this.otherPlayerY = y;
    }

    public void updateCamera() {
        if (playerId == 1) {
            centeredCameraX = (int) (player1.playerXposition - screenWidth / 2 + player1.solidArea.getWidth() / 2);
            centeredCameraY = (int) (player1.playerYposition - screenHeight / 2 + player1.solidArea.getHeight() / 2);
        } else if (playerId == 2) {
            centeredCameraX = (int) (player2.playerXposition - screenWidth / 2 + player2.solidArea.getWidth() / 2);
            centeredCameraY = (int) (player2.playerYposition - screenHeight / 2 + player2.solidArea.getHeight() / 2);
        }

        cameraX = Math.max(0, Math.min(centeredCameraX, worldWidth - screenWidth));
        cameraY = Math.max(0, Math.min(centeredCameraY, worldHeight - screenHeight));
    }

    public void draw() {
        GraphicsContext gc = getGraphicsContext2D();

        // Clear the screen
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, screenWidth, screenHeight);

        // Draw the tiles
        tileM.draw(gc);

        // Draw the first player
        player1.draw(gc);

        // Draw the second player
        player2.draw(gc);

        // Draw the bullets
        bullets.forEach(bullet -> bullet.draw(gc));

        // Draw the other player (if applicable)
        if (otherPlayerX != -1 && otherPlayerY != -1) {
            gc.setFill(Color.RED); // Use a different color for the other player
            gc.fillOval(otherPlayerX - cameraX, otherPlayerY - cameraY, 20, 20); // Example: Draw a circle
        }
    }

    public void updateCarState(int carX, int carY, String carDirection) {
        tileM.carX = carX;
        tileM.carY = carY;
        tileM.carDirection = carDirection;
    }

    public void handleNetworkMessage(String message) {
        // Parse the message and update the game state
        if (message.startsWith("Player position:")) {
            // Update another player's position
            String[] parts = message.split(":");
            String[] coordinates = parts[1].trim().split(",");
            int x = Integer.parseInt(coordinates[0].trim().replace("(", ""));
            int y = Integer.parseInt(coordinates[1].trim().replace(")", ""));

            // Update another player's position in the game
            updateOtherPlayerPosition(x, y);
        } else if (message.startsWith("Car position:")) {
            // Parse the message and update the car's state
            String[] parts = message.split(":");
            String[] carState = parts[1].trim().split(",");
            int carX = Integer.parseInt(carState[0].trim().replace("(", ""));
            int carY = Integer.parseInt(carState[1].trim());
            String carDirection = carState[2].trim().replace(")", "");

            // Update the car's state in the game
            updateCarState(carX, carY, carDirection);
        }
    }

    public void resizeCanvas(double newWidth, double newHeight) {
        screenWidth = (int) newWidth;
        screenHeight = (int) newHeight;

        this.setWidth(newWidth);
        this.setHeight(newHeight);
    }
}