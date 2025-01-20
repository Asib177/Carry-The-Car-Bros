package org.example.carrybros.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import org.example.carrybros.model.GamePanel;
import org.example.carrybros.model.KeyHandler;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public int screenX;
    public int screenY;

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

        speed = 3;
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
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

            if (keyH.upPressed) {
                direction = "up";
                gp.cChecker.checkTile(this);
                if (!collisionOn && !gp.tileM.isCollidingWithCar(this)) {
                    worldY -= speed;
                }
            } else if (keyH.downPressed) {
                direction = "down";
                gp.cChecker.checkTile(this);
                if (!collisionOn && !gp.tileM.isCollidingWithCar(this)) {
                    worldY += speed;
                }
            } else if (keyH.leftPressed) {
                direction = "left";
                gp.cChecker.checkTile(this);
                if (!collisionOn && !gp.tileM.isCollidingWithCar(this)) {
                    worldX -= speed;
                }
            } else if (keyH.rightPressed) {
                direction = "right";
                gp.cChecker.checkTile(this);
                if (!collisionOn && !gp.tileM.isCollidingWithCar(this)) {
                    worldX += speed;
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
    }
}