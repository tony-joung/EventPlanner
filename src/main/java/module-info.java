module com.example.eventmanagementdemo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.eventmanagementdemo to javafx.fxml;
    exports com.example.eventmanagementdemo;
}