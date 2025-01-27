package org.example.carrybros.model;

public class SessionManager {
    private static SessionManager instance;
    private String username;

    // Private constructor to prevent instantiation
    private SessionManager() {
    }

    // Get the singleton instance
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Getter and Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Clear the session
    public void clearSession() {
        username = null;
    }
}
