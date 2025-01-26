package org.example.carrybros.network;

import org.example.carrybros.model.GamePanel;

import java.io.*;
import java.net.Socket;

public class GameClient {
    private GamePanel gp;
    private PrintWriter out;

    public GameClient(GamePanel gp) {
        this.gp = gp;
    }

    public void start(String serverAddress, int port) {
        try {
            // Connect to the server
            Socket socket = new Socket(serverAddress, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Start a thread to listen for messages from the server
            new Thread(() -> listenForMessages(in)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenForMessages(BufferedReader in) {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received from server: " + message);

                // Handle the message (e.g., update game state)
                if (message.startsWith("Car position:")) {
                    // Parse the message and update the car's state
                    String[] parts = message.split(":");
                    String[] carState = parts[1].trim().split(",");
                    int carX = Integer.parseInt(carState[0].trim().replace("(", ""));
                    int carY = Integer.parseInt(carState[1].trim());
                    String carDirection = carState[2].trim().replace(")", "");

                    // Update the car's state in the game
                    gp.updateCarState(carX, carY, carDirection);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        } else {
            System.err.println("Error: Output stream is not initialized.");
        }
    }
}