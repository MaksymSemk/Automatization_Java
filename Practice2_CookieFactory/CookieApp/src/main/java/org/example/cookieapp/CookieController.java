package org.example.cookieapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.example.cookieservice.Service;

import static org.example.cookieapp.Utils.setDisableButtons;
import static org.example.cookieapp.Utils.showAlert;

public class CookieController {
    @FXML
    public Button exportDataToJsonButton;
    @FXML
    private Button makeCookiesButton;

    @FXML
    private Button buyCookiesButton;

    @FXML
    private Button buyMaterialsForCookiesButton;

    @FXML
    private Button logInAsWorkerButton;

    @FXML
    private Button logInAsConsumerButton;

    @FXML
    private TextArea factoryInfo;



    private Button[] workerButtons;
    private Button[] consumerButtons;
    private Service factoryService;

    //Doc This method initializes the controller, setting up button groups and disabling them, and instantiating the factory service.
    @FXML
    private void initialize() {
        workerButtons = new Button[]{makeCookiesButton, buyMaterialsForCookiesButton};
        consumerButtons = new Button[]{buyCookiesButton};

        setDisableButtons(workerButtons, true);
        setDisableButtons(consumerButtons, true);

        factoryService = new Service();
    }

    //Doc This method handles the action of making cookies, updating the factory state and displaying appropriate alerts.
    @FXML
    private void makeCookies() {

        if(factoryService.makeCookies()){
            showAlert(Alert.AlertType.INFORMATION, "Cookie Maker", "Cookies Made", "You just made some cookies!");
        }
        else{
            showAlert(Alert.AlertType.ERROR, "Cookie Maker", "Cookies ARE NOT Made", "You have to buy materials before making cookies");
        }
        factoryInfo.setText(factoryService.makeInfoForWorker());

    }

    //Doc This method handles the action of buying cookies, updating the factory state and displaying appropriate alerts.
    @FXML
    private void buyCookies() {
        if(factoryService.buyCookies()){
            showAlert(Alert.AlertType.INFORMATION, "Shop", "Cookies Bought", "You just bought some cookies!");
        }
        else{
            showAlert(Alert.AlertType.ERROR, "Shop", "Cookies ARE NOT Bought", "NO cookies in storage");
        }
        factoryInfo.setText(factoryService.makeInfoForConsumer());
    }

    //Doc This method handles the action of buying materials for cookies, updating the factory state and displaying appropriate alerts.
    @FXML
    private void buyMaterialsForCookies() {
        if (factoryService.buyMaterials()) {
            showAlert(Alert.AlertType.INFORMATION, "Materials", "Supplies Purchased", "You bought materials for cookies!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Materials", "Supplies NOT Purchased", "You already have enough materials.");
        }
        factoryInfo.setText(factoryService.makeInfoForWorker());
    }

    //Doc This method handles the worker login action, enabling worker-specific buttons and updating the factory information.
    @FXML
    private void logInAsWorker() {
        setDisableButtons(workerButtons, false);
        setDisableButtons(consumerButtons, true);

        factoryInfo.setText(factoryService.makeInfoForWorker());

        showAlert(Alert.AlertType.INFORMATION, "Login", "Worker Login", "You logged in as a Worker.");

    }

    //Doc This method handles the consumer login action, enabling consumer-specific buttons and updating the factory information.
    @FXML
    private void logInAsConsumer() {
        setDisableButtons(workerButtons, true);
        setDisableButtons(consumerButtons, false);

        factoryInfo.setText(factoryService.makeInfoForConsumer());

        showAlert(Alert.AlertType.INFORMATION, "Login", "Consumer Login", "You logged in as a Consumer.");
    }

    //Doc This method handles exporting factory data to a JSON file and displays a confirmation alert.
    public void exportDataToJson(ActionEvent actionEvent) {
        factoryService.exportToJson();
        showAlert(Alert.AlertType.INFORMATION, "Json", "Json saved", "Data from factory successfully saved to json");

    }
}