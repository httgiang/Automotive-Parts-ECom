package com.example.ui.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class PaymentController extends HelpMethods{
    @FXML
    private void onPayButtonClicked(ActionEvent event){
        switchToScene(event, "Order.fxml");
    }
}
