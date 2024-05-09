module com.example.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    requires com.microsoft.sqlserver.jdbc;

    opens com.example.ui to javafx.fxml;
    exports com.example.ui;
    exports com.example.ui.Controller;
    opens com.example.ui.Controller to javafx.fxml, javafx.base;
    opens com.example.ui.Entity to javafx.fxml, javafx.base;
}