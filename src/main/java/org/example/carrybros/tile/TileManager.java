package org.example.carrybros.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.carrybros.model.GamePanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TileManager {

    public GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public int carX, carY, carSpeed, carRadius, pathIndex;
    public String carDirection;
    public boolean carMoving;
    public int[][] path;
    public Image carUp, carDown, carLeft, carRight;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        int countTile = 50; // Number of different tiles
        tile = new Tile[countTile];

        mapTileNum = new int[gp.getMaxWorldCol()][gp.getMaxWorldRow()];

        loadMap("/map/myMap01.txt");
        getTileImage();
        initializeCar();
        loadCarImages();
        generateCarPath();
    }

    private void loadCarImages() {
        try {
            carUp = new Image(getClass().getResourceAsStream("/car/car_up.png"));
            carDown = new Image(getClass().getResourceAsStream("/car/car_down.png"));
            carLeft = new Image(getClass().getResourceAsStream("/car/car_left.png"));
            carRight = new Image(getClass().getResourceAsStream("/car/car_right.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateCarPath() {
        ArrayList<int[]> roadPoints = new ArrayList<>();

        for (int row = 0; row < gp.getMaxWorldRow(); row++) {
            for (int col = 0; col < gp.getMaxWorldCol(); col++) {
                if (mapTileNum[col][row] == 3) { // Assuming tile 3 is road
                    int worldX = col * gp.getTileSize();
                    int worldY = row * gp.getTileSize();
                    roadPoints.add(new int[]{worldX, worldY});
                }
            }
        }

        path = roadPoints.toArray(new int[0][]);

        if (path.length > 0) {
            carX = path[0][0] + gp.getTileSize() / 2;
            carY = path[0][1] + gp.getTileSize() / 2;
            carMoving = true;
        }
    }

    private void initializeCar() {
        carSpeed = 1;
        carRadius = 100;
        pathIndex = 0;
        carDirection = "DOWN";
        carMoving = false;
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = new Image(getClass().getResourceAsStream("/tile/bg.png"));

            tile[1] = new Tile();
            tile[1].image = new Image(getClass().getResourceAsStream("/tile/paint.png"));

            tile[2] = new Tile();
            tile[2].image = new Image(getClass().getResourceAsStream("/tile/border.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = new Image(getClass().getResourceAsStream("/road/road.png"));

            tile[4] = new Tile();
            tile[4].image =new Image(getClass().getResourceAsStream("/road/up.png"));

            tile[5] = new Tile();
            tile[5].image = new Image(getClass().getResourceAsStream("/road/down.png"));

            tile[6] = new Tile();
            tile[6].image = new Image(getClass().getResourceAsStream("/road/left.png"));

            tile[7] = new Tile();
            tile[7].image = new Image(getClass().getResourceAsStream("/road/right.png"));

            tile[8] = new Tile();
            tile[8].image = new Image(getClass().getResourceAsStream("/tile/final_left.png"));
            tile[8].collision = true;

            tile[9] = new Tile();
            tile[9].image = new Image(getClass().getResourceAsStream("/tile/final_right.png"));
            tile[9].collision = true;

            tile[10] = new Tile();
            tile[10].image = new Image(getClass().getResourceAsStream("/road/corner_down.png"));

            tile[11] = new Tile();
            tile[11].image = new Image(getClass().getResourceAsStream("/road/corner_up.png"));

            tile[12] = new Tile();
            tile[12].image = new Image(getClass().getResourceAsStream("/road/secondCorner_down.png"));

            tile[13] = new Tile();
            tile[13].image = new Image(getClass().getResourceAsStream("/road/secondCorner_up.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try (InputStream is = getClass().getResourceAsStream(filePath)) {
            if (is == null) {
                throw new RuntimeException("Map file not found at: " + filePath);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                for (int row = 0; row < gp.getMaxWorldRow(); row++) {
                    String line = br.readLine();
                    String[] numbers = line.split(" ");
                    for (int col = 0; col < gp.getMaxWorldCol(); col++) {
                        mapTileNum[col][row] = Integer.parseInt(numbers[col]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading the map file.", e);
        }
    }

    public boolean isPlayerNearCar() {
        int dx = gp.player.worldX - carX;
        int dy = gp.player.worldY - carY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance <= carRadius;
    }

    public void updateCar() {
        if (isPlayerNearCar()) {
            carMoving = true;
        } else {
            carMoving = false;
        }

        if (carMoving && path.length > 0) {
            int targetX = path[pathIndex][0] + gp.getTileSize() / 2;
            int targetY = path[pathIndex][1] + gp.getTileSize() / 2;

            if (carX < targetX) {
                carX += carSpeed;
                carDirection = "RIGHT";
            } else if (carX > targetX) {
                carX -= carSpeed;
                carDirection = "LEFT";
            }

            if (carY < targetY) {
                carY += carSpeed;
                carDirection = "DOWN";
            } else if (carY > targetY) {
                carY -= carSpeed;
                carDirection = "UP";
            }

            if (Math.abs(carX - targetX) < carSpeed && Math.abs(carY - targetY) < carSpeed) {
                carX = targetX;
                carY = targetY;
                pathIndex++;
                if (pathIndex >= path.length) {
                    pathIndex = 0;
                }
            }
        }
    }

    public void draw(GraphicsContext gc) {
        for (int worldRow = 0; worldRow < gp.getMaxWorldRow(); worldRow++) {
            for (int worldCol = 0; worldCol < gp.getMaxWorldCol(); worldCol++) {
                int tileNum = mapTileNum[worldCol][worldRow];
                int worldX = worldCol * gp.getTileSize();
                int worldY = worldRow * gp.getTileSize();
                gc.drawImage(tile[tileNum].image, worldX, worldY, gp.getTileSize(), gp.getTileSize());
            }
        }

        // Draw the car
        if (carDirection.equals("UP")) {
            gc.drawImage(carUp, carX - 32, carY - 32);
        } else if (carDirection.equals("DOWN")) {
            gc.drawImage(carDown, carX - 32, carY - 32);
        } else if (carDirection.equals("LEFT")) {
            gc.drawImage(carLeft, carX - 32, carY - 32);
        } else if (carDirection.equals("RIGHT")) {
            gc.drawImage(carRight, carX - 32, carY - 32);
        }
    }
}