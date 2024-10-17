package com.example.eventmanagementdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EventManagementApplication extends Application {
    // Constants defining the window title and size
    public static final String TITLE = "Event Management Demo";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventManagementApplication.class.getResource("login-page.fxml"));
        //String stylesheet = EventManagementApplication.class.getResource("main.css").toExternalForm();
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        //scene.getStylesheets().add(stylesheet);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}