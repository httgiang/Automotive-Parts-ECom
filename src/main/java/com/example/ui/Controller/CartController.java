package com.example.ui.Controller;

import com.example.ui.Entity.Cart;
import com.example.ui.Entity.Products;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CartController extends MenuBarMethods implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML
    private TableColumn<Products, Button> addCol;

    @FXML
    private TableColumn<Products, Float> amountCol;
    @FXML
    private TableColumn<Cart, String> pIDCol;

    @FXML
    private TableColumn<Products, String> pNameCol;

    @FXML
    private TableColumn<Cart, Integer> quantityCol;

    @FXML
    private TableColumn<Products, Button> removeCol;
    @FXML
    private TableColumn<?, ?> selectCol;
    @FXML
    public void onPayNowButtonClicked(ActionEvent event){
        switchToScene(event, "PaymentPage.fxml");
    }


    private void loadData(){
        pIDCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        pNameCol.setCellValueFactory(new PropertyValueFactory<>("pName"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        pIDCol.setCellValueFactory(new PropertyValueFactory<>("pId"));
        pIDCol.setCellValueFactory(new PropertyValueFactory<>("pId"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

}

