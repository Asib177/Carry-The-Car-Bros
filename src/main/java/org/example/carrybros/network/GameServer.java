package org.example.carrybros.network;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class GameServer {
    private static final int PORT = 8080;
    private List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    private ExecutorService threadPool = Executors.newCachedThreadPool();

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                threadPool.execute(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastCarState(int carX, int carY, String carDirection) {
        String message = "Car position: (" + carX + ", " + carY + ", " + carDirection + ")";
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("Client disconnected: " + client.getClientSocket());
    }

    public static void main(String[] args) {
        new GameServer().start();
    }
}


class ClientHandler implements Runnable {
    private Socket clientSocket;
    private GameServer server;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket, GameServer server) {
        this.clientSocket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from client: " + inputLine);

                if (inputLine.startsWith("Car position:")) {
                    // Parse the car's state from the message
                    String[] parts = inputLine.split(":");
                    String[] carState = parts[1].trim().split(",");
                    int carX = Integer.parseInt(carState[0].trim().replace("(", ""));
                    int carY = Integer.parseInt(carState[1].trim());
                    String carDirection = carState[2].trim().replace(")", "");

                    // Broadcast the car state to all clients
                    server.broadcastCarState(carX, carY, carDirection);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.removeClient(this);
            close();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void close() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}