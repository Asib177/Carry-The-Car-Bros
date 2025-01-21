package org.example.carrybros.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
import org.example.carrybros.model.GamePanel;
import org.example.carrybros.model.KeyHandler;
import javafx.scene.input.MouseEvent;


public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

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
        worldX = gp.tileSize * 5;
        worldY = gp.tileSize * 5;
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
    }

    public void handleMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShotTime >= fireRate) {
                // Get the gun position based on player's world coordinates
                double bulletX = worldX + gunOffsetX + gunDistance * Math.cos(gunAngle);
                double bulletY = worldY + gunOffsetY + gunDistance * Math.sin(gunAngle);

                // Add the bullet to the list with the correct world coordinates
                bullets.add(new Bullet(bulletX, bulletY, gunAngle));
                lastShotTime = currentTime;
            }
        }
    }


    // Update the gun position to ensure consistent distance from the player
    public void updateGunPosition(double mouseX, double mouseY) {
        // Calculate the direction to the mouse position from the player's screen position
        double deltaX = mouseX - (screenX + gunOffsetX);  // Difference in X
        double deltaY = mouseY - (screenY + gunOffsetY);  // Difference in Y
        gunAngle = Math.atan2(deltaY, deltaX);  // Calculate the angle of the gun
    }

    public void updateBullets() {
        bullets.removeIf(bullet -> bullet.isOffScreen(gp.screenWidth, gp.screenHeight));
        for (Bullet bullet : bullets) {
            bullet.update(); // Update each bullet’s position
        }
    }

    public void drawBullets(GraphicsContext gc) {
        for (Bullet bullet : bullets) {
            bullet.draw(gc); // Draw each bullet
        }
    }


    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

            if (keyH.upPressed) {
                direction = "up";
                gp.cChecker.checkTile(this);
                if (!collisionOn && !gp.tileM.isCollidingWithCar(this)) {
                    worldY -= playerSpeed;
                }
            } else if (keyH.downPressed) {
                direction = "down";
                gp.cChecker.checkTile(this);
                if (!collisionOn && !gp.tileM.isCollidingWithCar(this)) {
                    worldY += playerSpeed;
                }
            } else if (keyH.leftPressed) {
                direction = "left";
                gp.cChecker.checkTile(this);
                if (!collisionOn && !gp.tileM.isCollidingWithCar(this)) {
                    worldX -= playerSpeed;
                }
            } else if (keyH.rightPressed) {
                direction = "right";
                gp.cChecker.checkTile(this);
                if (!collisionOn && !gp.tileM.isCollidingWithCar(this)) {
                    worldX += playerSpeed;
                }
            }

            worldX = Math.max(0, Math.min(worldX, gp.worldWidth - gp.tileSize));
            worldY = Math.max(0, Math.min(worldY, gp.worldHeight - gp.tileSize));

            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }

    public void draw(GraphicsContext gc) {
        Image playerImage = null;

        switch (direction) {
            case "up" -> playerImage = spriteNum == 1 ? up1 : up2;
            case "down" -> playerImage = spriteNum == 1 ? down1 : down2;
            case "left" -> playerImage = spriteNum == 1 ? left1 : left2;
            case "right" -> playerImage = spriteNum == 1 ? right1 : right2;
        }

        screenX = worldX - gp.cameraX;
        screenY = worldY - gp.cameraY;

        if (gp.cameraX == 0) screenX = worldX;
        if (gp.cameraY == 0) screenY = worldY;
        if (gp.cameraX == gp.worldWidth - gp.screenWidth) screenX = screenX + gp.cameraX - (gp.worldWidth - gp.screenWidth);
        if (gp.cameraY == gp.worldHeight - gp.screenHeight) screenY = screenY + gp.cameraY - (gp.worldHeight - gp.screenHeight);

        if (playerImage != null) {
            gc.drawImage(playerImage, screenX, screenY, gp.tileSize, gp.tileSize);
        }

        drawGun(gc);
    }

    private void drawGun(GraphicsContext gc) {
//         Calculate the gun's fixed position based on the player's position and the fixed distance
        gunX = worldX  + gunOffsetX + gunDistance * Math.cos(gunAngle);
        gunY = worldY  + gunOffsetY + gunDistance * Math.sin(gunAngle);

        // Check if the mouse is to the left of the player
        boolean flipGun = gunX < worldX;

        // Draw the gun at the correct position with proper rotation
        gc.save(); // Save the current state of the canvas
        gc.translate(gunX + 16, gunY + 16); // Translate the canvas to the gun's center
        gc.rotate(Math.toDegrees(gunAngle)); // Rotate the canvas by the gun's angle

        // Flip the gun horizontally when the cursor is to the left of the player
        if (flipGun) {
            gc.scale(1, -1); // Flip the gun horizontally
        }

        gc.drawImage(gunImage, -16, -16); // Draw the gun (centered around the translated point)
        gc.restore(); // Restore the canvas to its original state
    }
}

