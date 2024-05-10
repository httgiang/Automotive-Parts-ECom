package com.example.ui.Controller;

import com.example.ui.Entity.Products;
import com.example.ui.Entity.User;
import com.example.ui.SQLConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewProductController extends MenuBarMethods {
    private Alert alert;
    PreparedStatement pst = null;
    @FXML
    private TextField txt_productID;

    @FXML
    private TextField txt_pName;

    @FXML
    private TextField txt_pType;

    @FXML
    private TextField txt_pPrice;

    @FXML
    private TextField txt_pStockQuantity;

    @FXML
    private TextArea txt_pInfo;

    @FXML
    private TextField txt_selectQuantity;

    @FXML
    private Button button_addToCart;

    @FXML
    private ImageView productImg;

    private void showProductInformation() {
        try {
            String productID = Products.getInstance().getProductID();

            Connection con = SQLConnection.connectDb();

            String sql = "SELECT * FROM PRODUCTS WHERE productID = ?";
            assert con != null;
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, productID);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                txt_productID.setText(productID);
                txt_pName.setText(rs.getString("pName"));
                txt_pType.setText(rs.getString("pType"));
                txt_pPrice.setText(String.valueOf((rs.getInt("pPrice"))));
                txt_pStockQuantity.setText(String.valueOf((rs.getInt("pStockQuantity"))));
                txt_pInfo.setText(rs.getString("pInfo"));
//                productImg.setImage();
            }
            JOptionPane.showMessageDialog(null, "Successfully show info!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void addToCart() {
        if (txt_selectQuantity.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in the Product Quantity!");
            alert.showAndWait();
        } else {
            String purchaserEmail = User.getInstance().getEmail();
            Connection con = SQLConnection.connectDb();
            String insertQuantity = "INSERT INTO CART(purchaserEmail, productID, productQuantity) VALUES(?,?,?)";
            try {
                pst = con.prepareStatement(insertQuantity);
                pst.setString(1, purchaserEmail);
                pst.setString(2, txt_productID.getText());
                pst.setInt(3, Integer.parseInt(txt_selectQuantity.getText()));
                pst.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // if button is clicked -> call method to update info in DB
    private void activateAddToCartButton(ActionEvent event) {
        if(event.getSource() == button_addToCart) {
            addToCart();
        }
    }
    public void initialize() {
        showProductInformation();
    }
}

