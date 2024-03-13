module com.example.guicompanydb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;

    opens com.example.guicompanydb to javafx.fxml, com.google.gson;
    exports com.example.guicompanydb;
    exports com.example.guicompanydb.models;
    opens com.example.guicompanydb.models to javafx.fxml, com.google.gson;
}