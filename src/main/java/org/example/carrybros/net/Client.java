package org.example.carrybros.net;

import org.example.carrybros.model.GameAction;

import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 8080;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public void startClient() {
        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            // Start receiving messages from the server
            new Thread(this::receiveMessages).start();

            // You can now start sending actions, for example, when the player shoots
            // Example: GameAction action = new GameAction("shoot", 100, 200, true);
            // sendGameAction(action);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveMessages() {
        try {
            while (true) {
                GameAction action = (GameAction) in.readObject();
                // Handle the received game action, e.g., update player state or shoot
                System.out.println("Received action: " + action.getActionType());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendGameAction(GameAction action) {
        try {
            out.writeObject(action);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
