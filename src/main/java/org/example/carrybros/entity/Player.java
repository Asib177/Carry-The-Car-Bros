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


            collisionOn = false;
            gp.cChecker.checkTile(this);

            if (!collisionOn) {
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                spriteCounter = 0;
            }
        }
    }

    public void draw(GraphicsContext gc) {
        // Draw the player
        Image playerImage = null;

        }

        if (playerImage != null) {
            gc.drawImage(playerImage, screenX, screenY, gp.tileSize, gp.tileSize);
        }
    }
}