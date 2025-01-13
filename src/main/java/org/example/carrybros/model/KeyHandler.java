package org.example.carrybros.model;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandler {


    public void keyPressed(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code == KeyCode.W) upPressed = true;
        if (code == KeyCode.S) downPressed = true;
        if (code == KeyCode.A) leftPressed = true;
        if (code == KeyCode.D) rightPressed = true;
    }

    public void keyReleased(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code == KeyCode.W) upPressed = false;
        if (code == KeyCode.S) downPressed = false;
        if (code == KeyCode.A) leftPressed = false;
        if (code == KeyCode.D) rightPressed = false;
    }
}