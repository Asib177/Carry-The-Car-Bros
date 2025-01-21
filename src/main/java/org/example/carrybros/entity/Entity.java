package org.example.carrybros.entity;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    public int worldX, worldY, screenX, screenY;
    public long startTime;
    public int playerSpeed;
    public String direction;
    public Image up1, up2, down1, down2, left1, left2, right1, right2, gunImage;
    public double gunOffsetX = 20;
    public double gunOffsetY = 10;
    public double gunAngle = 0;
    public double gunDistance = 70;
    public List<Bullet> bullets = new ArrayList<>();
    public long lastShotTime = 0;
    public long fireRate = 50;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public boolean collisionOn = false;
    public double gunX, gunY;
}
