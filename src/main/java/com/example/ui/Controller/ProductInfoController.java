package com.example.ui.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ProductInfoController {
    private String productId;
    @FXML
    Button button;
    public void setProductId(String productId) {
        button.setText(productId);
    }

}
