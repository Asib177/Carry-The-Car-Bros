package org.example.carrybros.entity;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Entity {

    // World position for the player
    public int worldX, worldY;

    // Timer and game state variables
    public long startTime;

    // Player movement details
    public int speed;
    public String direction; // Player's movement direction

    // Player sprites
    public Image up1, up2, down1, down2, left1, left2, right1, right2;

    // For sprite animation
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // Collision bounding box
    public Rectangle solidArea;
    public boolean collisionOn = false;
}
