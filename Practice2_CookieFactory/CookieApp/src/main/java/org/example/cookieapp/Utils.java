package org.example.cookieapp;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.util.Arrays;

public class Utils {
    //Doc This method displays a JavaFX alert dialog with the specified type, title, header, and content.
    static void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //Doc This method enables or disables an array of JavaFX buttons based on the provided status.
    static void setDisableButtons(Button[] buttons, boolean status){
        Arrays.stream(buttons).forEach( b-> b.setDisable(status));

    }
}