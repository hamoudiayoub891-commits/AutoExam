module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires java.sql;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    opens AutoCorrectionExamen to javafx.graphics, javafx.fxml;
    exports AutoCorrectionExamen;
}