package org.example.carrybros.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet {
    private double bulletX, bulletY, dx, dy;
    private final double speed = 10;

    public Bullet(double x, double y, double angle) {
        this.bulletX = x;
        this.bulletY = y;
        this.dx = Math.cos(angle) * speed;
        this.dy = Math.sin(angle) * speed;
    }

    public void update(double deltaTime) {
        bulletX += dx * deltaTime * 60; // Scale movement with deltaTime
        bulletY += dy * deltaTime * 60;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        gc.fillOval(bulletX - 2, bulletY - 2, 4, 4); // Draw a small circle for the bullet
    }

    public boolean isOutOfBounds(double width, double height) {
        return bulletX < 0 || bulletX > width || bulletY < 0 || bulletY > height;
    }
}
