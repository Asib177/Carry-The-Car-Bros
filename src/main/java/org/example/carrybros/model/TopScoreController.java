package org.example.carrybros.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.carrybros.database.DBConnection;

import java.sql.*;

public class TopScoreController {

    @FXML
    private TableView<Score> scoresTable;

    @FXML
    private TableColumn<Score, String> usernameColumn;

    @FXML
    private TableColumn<Score, Integer> scoreColumn;

    private ObservableList<Score> scores;

    @FXML
    public void initialize() {
        // Set up the columns
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        // Fetch the top 5 players from the database
        scores = FXCollections.observableArrayList();
        fetchTopScores();
        scoresTable.setItems(scores);
    }

    // Method to fetch top 5 scores from the database
    private void fetchTopScores() {
        String query = "SELECT username, score FROM player ORDER BY score DESC LIMIT 5";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String username = rs.getString("username");
                int score = rs.getInt("score");
                scores.add(new Score(username, score));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions properly
        }
    }

    // Inner class representing a score entry
    public static class Score {
        private String username;
        private int score;

        public Score(String username, int score) {
            this.username = username;
            this.score = score;
        }

        public String getUsername() {
            return username;
        }

        public int getScore() {
            return score;
        }
    }
}
