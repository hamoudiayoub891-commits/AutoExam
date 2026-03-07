module AutoCorrectionExamen {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires java.sql;

    opens AutoCorrectionExamen to javafx.graphics, javafx.fxml;
    exports AutoCorrectionExamen;
}