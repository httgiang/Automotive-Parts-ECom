package com.example.ui.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PaymentController extends MenuBarMethods {
    @FXML
    private javafx.scene.control.TextField txt_CVV;

    @FXML
    private javafx.scene.control.TextField txt_ExDate;

    @FXML
    private javafx.scene.control.TextField txt_cardFName;

    @FXML
    private javafx.scene.control.TextField txt_cardLName;
    private Stage stage;
    private Scene scene;
    @FXML
    public void onPayButtonClicked(ActionEvent event){
        switchToScene(event, "Order.fxml");
    }
}
