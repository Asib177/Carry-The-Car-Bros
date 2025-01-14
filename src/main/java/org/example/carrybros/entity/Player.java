package org.example.carrybros.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import org.example.carrybros.model.GamePanel;
import org.example.carrybros.model.KeyHandler;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    // Player-related variables
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        // Set Player in the middle of screen
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        startTime = System.currentTimeMillis();

        worldX = gp.tileSize * 5; // Start at some location
        worldY = gp.tileSize * 5;

        speed = 5; // Player speed
        direction = "down"; // Initial direction
    }

    public void getPlayerImage() {
        // Load player sprite images
        up1 = new Image(getClass().getResourceAsStream("/player/boy_up_1.png"));
        up2 = new Image(getClass().getResourceAsStream("/player/boy_up_2.png"));
        down1 = new Image(getClass().getResourceAsStream("/player/boy_down_1.png"));
        down2 = new Image(getClass().getResourceAsStream("/player/boy_down_2.png"));
        left1 = new Image(getClass().getResourceAsStream("/player/boy_left_1.png"));
        left2 = new Image(getClass().getResourceAsStream("/player/boy_left_2.png"));
        right1 = new Image(getClass().getResourceAsStream("/player/boy_right_1.png"));
        right2 = new Image(getClass().getResourceAsStream("/player/boy_right_2.png"));
    }

    public void update(double deltaTime) {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

            // Initially assume no collision
            collisionOn = false;

            // Check for collision in the direction the player is attempting to move
            if (keyH.upPressed) {
                direction = "up";
                gp.cChecker.checkTile(this);  // Check for collision upwards
                if (!collisionOn) {
                    worldY -= speed;  // Move up if no collision
                }
            } else if (keyH.downPressed) {
                direction = "down";
                gp.cChecker.checkTile(this);  // Check for collision downwards
                if (!collisionOn) {
                    worldY += speed;  // Move down if no collision
                }
            } else if (keyH.leftPressed) {
                direction = "left";
                gp.cChecker.checkTile(this);  // Check for collision to the left
                if (!collisionOn) {
                    worldX -= speed;  // Move left if no collision
                }
            } else if (keyH.rightPressed) {
                direction = "right";
                gp.cChecker.checkTile(this);  // Check for collision to the right
                if (!collisionOn) {
                    worldX += speed;  // Move right if no collision
                }
            }

            // Prevent the player from moving outside the world boundaries
            worldX = Math.max(0, Math.min(worldX, gp.worldWidth - gp.tileSize));
            worldY = Math.max(0, Math.min(worldY, gp.worldHeight - gp.tileSize));

            // Sprite animation logic
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }


    public void draw(GraphicsContext gc) {
        Image playerImage = null;

        // Choose sprite image based on direction
        switch (direction) {
            case "up" -> playerImage = spriteNum == 1 ? up1 : up2;
            case "down" -> playerImage = spriteNum == 1 ? down1 : down2;
            case "left" -> playerImage = spriteNum == 1 ? left1 : left2;
            case "right" -> playerImage = spriteNum == 1 ? right1 : right2;
        }

        // Draw the player relative to the camera
        if (playerImage != null) {
            gc.drawImage(playerImage, worldX - gp.cameraX, worldY - gp.cameraY, gp.tileSize, gp.tileSize);
        }
    }
}