package com.example.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
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
    private Button cartButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button orderButton;

    @FXML
    private Button profileButton;

    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton;


    @FXML
    void switchToAddNewProduct(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("addProductPage.fxml"));
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



