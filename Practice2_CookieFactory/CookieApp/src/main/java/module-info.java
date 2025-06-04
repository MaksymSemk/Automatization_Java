module org.example.cookieapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.example.cookieservice;

    opens org.example.cookieapp to javafx.fxml;
    exports org.example.cookieapp;
}