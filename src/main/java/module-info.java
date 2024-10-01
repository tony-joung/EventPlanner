module com.example.eventmanagementdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.eventmanagementdemo to javafx.fxml;
    exports com.example.eventmanagementdemo;
    exports com.example.eventmanagementdemo.controller;
    opens com.example.eventmanagementdemo.controller to javafx.fxml;
    exports com.example.eventmanagementdemo.model;
    opens com.example.eventmanagementdemo.model to javafx.fxml;
}