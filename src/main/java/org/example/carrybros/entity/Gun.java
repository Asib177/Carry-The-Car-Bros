package org.example.carrybros.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Gun {
    private double x, y;
    private double angle;
    private final double distance;
    private Player player;

    public Gun(Player player, double distance) {
        this.player = player;
        this.distance = distance;
    }

    public void updatePosition(double mouseX, double mouseY) {
        angle = Math.atan2(mouseY - player.worldY, mouseX - player.worldX);
        x = player.worldX + Math.cos(angle) * distance;
        y = player.worldY + Math.sin(angle) * distance;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(x - 5, y - 5, 10, 10);
    }
}
