package org.example.carrybros.entity;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Entity {

    public int worldX, worldY;
    public long startTime;
    public int speed;
    public String direction;
    public Image up1, up2, down1, down2, left1, left2, right1, right2, gunImage;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public boolean collisionOn = false;
}
