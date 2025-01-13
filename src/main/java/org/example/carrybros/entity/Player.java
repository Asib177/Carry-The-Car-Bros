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

        // Set Player in the middle of the screen
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        startTime = System.currentTimeMillis();

        worldX = gp.tileSize * 1;
        worldY = gp.tileSize * 9;

        speed = 2; // Player speed
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

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

            if (keyH.upPressed) direction = "up";
            else if (keyH.downPressed) direction = "down";
            else if (keyH.leftPressed) direction = "left";
            else if (keyH.rightPressed) direction = "right";

            // Reset collision flag and check collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Move if no collision
            if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            // Sprite animation
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = spriteNum == 1 ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }

    public void draw(GraphicsContext gc) {
        // Draw the player
        Image playerImage = null;

        switch (direction) {
            case "up" -> playerImage = (spriteNum == 1) ? up1 : up2;
            case "down" -> playerImage = (spriteNum == 1) ? down1 : down2;
            case "left" -> playerImage = (spriteNum == 1) ? left1 : left2;
            case "right" -> playerImage = (spriteNum == 1) ? right1 : right2;
        }

        if (playerImage != null) {
            gc.drawImage(playerImage, screenX, screenY, gp.tileSize, gp.tileSize);
        }
    }
}
