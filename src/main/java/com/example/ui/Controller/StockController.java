package com.example.ui.Controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StockController extends MenuBarMethods {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    private Alert alert;
    private Stage stage;
    private Scene scene;
    @FXML
    private Button addProductButton;


    @FXML
    public void switchToAddNewProduct(javafx.event.ActionEvent event) {
        switchToScene(event, "AddProductPage.fxml");
    }
}



