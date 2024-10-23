package com.example.eventmanagementdemo.utils;

import com.example.eventmanagementdemo.EventManagementApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class Utils {

    public static void navigateTo(Control control, String page){
        Stage stage = (Stage) control.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(EventManagementApplication.class.getResource(page));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), EventManagementApplication.WIDTH, EventManagementApplication.HEIGHT);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        stage.setScene(scene);
    }

    public static void navigateWithInitialData(Control control, String page, String css, Consumer<FXMLLoader> loader){
        Stage stage = (Stage) control.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(EventManagementApplication.class.getResource(page));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), EventManagementApplication.WIDTH, EventManagementApplication.HEIGHT);
            if(Objects.nonNull(css)){
                scene.getStylesheets().add(EventManagementApplication.class.getResource(css).toExternalForm());
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        loader.accept(fxmlLoader);
        stage.setScene(scene);
    }

    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showSuccess(String title, String message, Runnable function) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(null) == ButtonType.OK && Objects.nonNull(function)) {
            function.run();
        }
    }
}
