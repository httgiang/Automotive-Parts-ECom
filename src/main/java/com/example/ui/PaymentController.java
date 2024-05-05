package com.example.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class PaymentController extends MenuBarMethods{
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
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Order.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
