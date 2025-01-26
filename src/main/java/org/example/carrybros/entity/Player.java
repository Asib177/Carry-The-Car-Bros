package org.example.carrybros.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import org.example.carrybros.model.GamePanel;
import org.example.carrybros.model.KeyHandler;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    private Image gunImage, counerHouse;
    private final double gunDistance = 50;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        solidArea = new Rectangle(8, 8, 32, 32);
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        startTime = System.currentTimeMillis();
        playerXposition = gp.tileSize * 5;
        playerYposition = gp.tileSize * 5;
        playerSpeed = 3;
        direction = "down";
    }

    public void getPlayerImage() {
        up1 = new Image(getClass().getResourceAsStream("/player/boy_up_1.png"));
        up2 = new Image(getClass().getResourceAsStream("/player/boy_up_2.png"));
        down1 = new Image(getClass().getResourceAsStream("/player/boy_down_1.png"));
        down2 = new Image(getClass().getResourceAsStream("/player/boy_down_2.png"));
        left1 = new Image(getClass().getResourceAsStream("/player/boy_left_1.png"));
        left2 = new Image(getClass().getResourceAsStream("/player/boy_left_2.png"));
        right1 = new Image(getClass().getResourceAsStream("/player/boy_right_1.png"));
        right2 = new Image(getClass().getResourceAsStream("/player/boy_right_2.png"));
        gunImage = new Image(getClass().getResourceAsStream("/images/NewGun.png"));
//        counerHouse = new Image(getClass().getResourceAsStream("/house/Couner Housee.png"));
    }


    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

            if (keyH.upPressed) {
                direction = "up";
                gp.cChecker.checkTile(this);
                if (!collisionOn && !gp.tileM.isCollidingWithCar(this)) {
                    playerYposition -= playerSpeed;
                }
            } else if (keyH.downPressed) {
                direction = "down";
                gp.cChecker.checkTile(this);
                if (!collisionOn && !gp.tileM.isCollidingWithCar(this)) {
                    playerYposition += playerSpeed;
                }
            } else if (keyH.leftPressed) {
                direction = "left";
                gp.cChecker.checkTile(this);
                if (!collisionOn && !gp.tileM.isCollidingWithCar(this)) {
                    playerXposition -= playerSpeed;
                }
            } else if (keyH.rightPressed) {
                direction = "right";
                gp.cChecker.checkTile(this);
                if (!collisionOn && !gp.tileM.isCollidingWithCar(this)) {
                    playerXposition += playerSpeed;
                }
            }

            playerXposition = Math.max(0, Math.min(playerXposition, gp.worldWidth - gp.tileSize));
            playerYposition = Math.max(0, Math.min(playerYposition, gp.worldHeight - gp.tileSize));

            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }
        updateGunPosition(gp.mouseX, gp.mouseY); // Update the gun position and angle
    }

    private void updateGunPosition(double mouseX, double mouseY) {
        // Calculate angle towards the mouse cursor
        gunAngle = Math.atan2(mouseY - screenY - gp.tileSize / 2, mouseX - screenX - gp.tileSize / 2);
        // Calculate gun position based on the player's position and angle
        gunX = screenX + gp.tileSize / 2 + Math.cos(gunAngle) * gunDistance;
        gunY = screenY + gp.tileSize / 2 + Math.sin(gunAngle) * gunDistance;
    }

    public void draw(GraphicsContext gc) {
        Image playerImage = null;

        switch (direction) {
            case "up" -> playerImage = spriteNum == 1 ? up1 : up2;
            case "down" -> playerImage = spriteNum == 1 ? down1 : down2;
            case "left" -> playerImage = spriteNum == 1 ? left1 : left2;
            case "right" -> playerImage = spriteNum == 1 ? right1 : right2;
        }

        screenX = playerXposition - gp.cameraX;
        screenY = playerYposition - gp.cameraY;

        if (gp.cameraX == 0) screenX = playerXposition;
        if (gp.cameraY == 0) screenY = playerYposition;
        if (gp.cameraX == gp.worldWidth - gp.screenWidth) screenX = screenX + gp.cameraX - (gp.worldWidth - gp.screenWidth);
        if (gp.cameraY == gp.worldHeight - gp.screenHeight) screenY = screenY + gp.cameraY - (gp.worldHeight - gp.screenHeight);

        if (playerImage != null) {
            gc.drawImage(playerImage, screenX, screenY, gp.tileSize, gp.tileSize);
        }

        drawGun(gc);
    }

    private void drawGun(GraphicsContext gc) {
        // Check if the mouse is to the left of the player
        boolean flipGun = gunX < playerXposition;

        // Draw the gun at the correct position with proper rotation
        gc.save(); // Save the current state of the canvas
        gc.translate(gunX, gunY); // Translate the canvas to the gun's center

        gc.rotate(Math.toDegrees(gunAngle)); // Rotate the canvas by the gun's angle

        // Flip the gun horizontally when the cursor is to the left of the player
        if (flipGun) {
            gc.scale(1, -1); // Flip the gun horizontally
        }

        gc.drawImage(gunImage, -gp.tileSize / 4, -gp.tileSize / 4, gp.tileSize / 2, gp.tileSize / 2);
        gc.restore(); // Restore the canvas to its original state
    }
}

