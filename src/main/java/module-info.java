module com.example.facegenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.facegenerator to javafx.fxml;
    exports com.example.facegenerator;
}