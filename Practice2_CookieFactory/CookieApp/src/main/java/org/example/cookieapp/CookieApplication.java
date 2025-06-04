package org.example.cookieapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CookieApplication extends Application {
    //Doc This method is the entry point for the JavaFX application, setting up the primary stage and loading the initial UI.
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CookieApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Cookie factory!!!!");
        stage.setScene(scene);
        stage.show();
    }

    //Doc This is the main method that launches the JavaFX application.
    public static void main(String[] args) {
        launch();
    }
}