package org.example.carrybros.village;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.carrybros.model.GamePanel;
import org.example.carrybros.model.KeyHandler;

public class Village {
    GamePanel gp;
    KeyHandler keyH;

    private Image cornerHouse, horizontalHouse, verticalHouse;
    int cornerHouseX, cornerHouseY, horizontalHouseX, horizontalHouseY, verticalHouseX, verticalHouseY;

    public Village(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getHouseImage();
    }

    public void setDefaultValues() {
        cornerHouseX = gp.tileSize * 8;
        cornerHouseY = gp.tileSize * 8;

        horizontalHouseX = gp.tileSize * 19;
        horizontalHouseY = gp.tileSize * 38;

        verticalHouseX = gp.tileSize * 33;
        verticalHouseY = gp.tileSize * 20;
    }

    public void getHouseImage() {
        cornerHouse = new Image(getClass().getResourceAsStream("/house/Corner House.png"));
        horizontalHouse = new Image(getClass().getResourceAsStream("/house/Horizontal House.png"));
        verticalHouse = new Image(getClass().getResourceAsStream("/house/Vertical House.png"));
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(cornerHouse, cornerHouseX - gp.cameraX, cornerHouseY - gp.cameraY, gp.tileSize * 5, gp.tileSize * 5);
        gc.drawImage(horizontalHouse, horizontalHouseX - gp.cameraX, horizontalHouseY - gp.cameraY, gp.tileSize * 5, gp.tileSize * 5);
        gc.drawImage(verticalHouse, verticalHouseX - gp.cameraX, verticalHouseY - gp.cameraY, gp.tileSize * 5, gp.tileSize * 5);
    }
}

