package com.example.ui.Entity;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Products {
    String pId;
    String pName;
    String sellerEmail;
    String pType;
    String pInfo;
    String pPrice;
    int pStockQuant;
    Button addButton;
    Button removeButton;
    private static Products instance;
    public static Products getInstance() {
        if (instance == null) {
            instance = new Products();
        }
        return instance;
    }

    public String getProductID() {
        return pId;
    }



}
