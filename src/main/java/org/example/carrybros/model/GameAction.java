package org.example.carrybros.model;

import java.io.Serializable;

public class GameAction implements Serializable {
    private String actionType;
    private int x, y;
    private boolean isShooting;

    public GameAction(String actionType, int x, int y, boolean isShooting) {
        this.actionType = actionType;
        this.x = x;
        this.y = y;
        this.isShooting = isShooting;
    }

    public String getActionType() {
        return actionType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isShooting() {
        return isShooting;
    }
}
