package org.example.carrybros.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.carrybros.model.GamePanel;

public class Bullet {

    private double bulletX, bulletY;
    private double bulletAngle;
    private double bulletSpeed = 10; // Bullet speed
    private double radius = 5; // Bullet size

    GamePanel gp = new GamePanel();

    public Bullet(double x, double y, double angle) {
        this.bulletX = x;
        this.bulletY = y;
        this.bulletAngle = angle;
    }

    public boolean isOffScreen(double worldWidth, double worldHeight) {
        return bulletX < 0 || bulletX > worldWidth || bulletY < 0 || bulletY > worldHeight;
    }

    public void update() {
        bulletX += bulletSpeed * Math.cos(bulletAngle);
        bulletY += bulletSpeed * Math.sin(bulletAngle);
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.YELLOW); // Bullet color
        gc.fillOval(bulletX, bulletY, radius, radius);
    }
}
