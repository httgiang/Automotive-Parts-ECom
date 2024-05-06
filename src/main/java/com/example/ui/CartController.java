package com.example.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CartController extends MenuBarMethods{

    private Stage stage;
    private Scene scene;
    @FXML
    public void onPayNowButtonClicked(ActionEvent event){
        switchToScene(event, "PaymentPage.fxml");
    }
}
