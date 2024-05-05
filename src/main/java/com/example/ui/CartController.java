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
        try {
            Parent root = FXMLLoader.load(getClass().getResource("PaymentPage.fxml"));
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
