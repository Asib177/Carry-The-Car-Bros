module org.example.carrybros {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.carrybros to javafx.fxml;
    exports org.example.carrybros;
    exports org.example.carrybros.model;  // Export the model package to JavaFX

    opens org.example.carrybros.model to javafx.fxml;  // Allow reflection for FXML
}

