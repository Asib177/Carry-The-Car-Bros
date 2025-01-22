package org.example.carrybros.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet {
    private double x, y, dx, dy;
    private final double speed = 10;

    public Bullet(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.dx = Math.cos(angle) * speed;
        this.dy = Math.sin(angle) * speed;
    }

    public void update(double deltaTime) {
        x += dx * deltaTime * 60; // Scale movement with deltaTime
        y += dy * deltaTime * 60;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(x - 2, y - 2, 4, 4); // Draw a small circle for the bullet
    }

    public boolean isOutOfBounds(double width, double height) {
        return x < 0 || x > width || y < 0 || y > height;
    }
}
