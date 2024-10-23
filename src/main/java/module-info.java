module com.example.eventmanagementdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.javalin;


    opens com.example.eventmanagementdemo to javafx.fxml;
    exports com.example.eventmanagementdemo;
    exports com.example.eventmanagementdemo.controller;
    opens com.example.eventmanagementdemo.controller to javafx.fxml;
    exports com.example.eventmanagementdemo.model;
    opens com.example.eventmanagementdemo.model to javafx.fxml;
    exports com.example.eventmanagementdemo.dao.event;
    opens com.example.eventmanagementdemo.dao.event to javafx.fxml;
    exports com.example.eventmanagementdemo.dao.role;
    opens com.example.eventmanagementdemo.dao.role to javafx.fxml;
    exports com.example.eventmanagementdemo.dao.user;
    opens com.example.eventmanagementdemo.dao.user to javafx.fxml;
    exports com.example.eventmanagementdemo.sqlite;
    opens com.example.eventmanagementdemo.sqlite to javafx.fxml;
    exports com.example.eventmanagementdemo.service;
    opens com.example.eventmanagementdemo.service to javafx.fxml;
    exports com.example.eventmanagementdemo.utils;
    opens com.example.eventmanagementdemo.utils to javafx.fxml;
}