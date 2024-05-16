package com.example.ui.Controller;

import com.example.ui.Entity.Products;
import com.example.ui.Entity.User;
import com.example.ui.SQLConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewProductController extends HelpMethods {
    private Alert alert;
    PreparedStatement pst = null;
    private int productID;


    @FXML
    private TextField txt_pID;

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
    private ImageView productImg;
    @FXML
    private AnchorPane rootPur;
    @FXML
    private AnchorPane rootSel;
    String userType = User.getInstance().getType();

    public void setProductID(int productID) {
        this.productID = productID;
        showProductInformation(productID);
    }

    public void showProductInformation(int productID) {
        if(userType.equals("Purchaser")){
            setUneditable(rootPur);
            setEditable(txt_selectQuantity);
        } else {
            setUneditable(rootSel);
        }
        try {
            Connection con = SQLConnection.connectDb();
            System.out.println(productID);
            String sql = "SELECT * FROM PRODUCTS WHERE productID = ?";
            assert con != null;
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, productID);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                txt_pID.setText(String.valueOf((rs.getInt("productID"))));
                txt_pName.setText(rs.getString("pName"));
                txt_pType.setText(rs.getString("pType"));
                txt_pPrice.setText(String.valueOf((rs.getInt("pPrice"))));
                txt_pStockQuantity.setText(String.valueOf((rs.getInt("pStockQuantity"))));
                txt_pInfo.setText(rs.getString("pInfo"));
                showProductImg(productImg, rs);
            }
            JOptionPane.showMessageDialog(null, "Successfully show info!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    private void addToCart() {
        if (txt_selectQuantity.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in the 'Product Quantity'!");
            alert.showAndWait();
        } else {
            String purchaserEmail = User.getInstance().getEmail();
            Connection con = SQLConnection.connectDb();
            String insertQuantity = "INSERT INTO CART(purchaserEmail, productID, productQuantity) VALUES(?,?,?)";
            try {
                pst = con.prepareStatement(insertQuantity);
                pst.setString(1, purchaserEmail);
                pst.setInt(2, productID);
                pst.setInt(3, Integer.parseInt(txt_selectQuantity.getText()));
                pst.execute();
                JOptionPane.showMessageDialog(null, "Successfully add to cart!");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    @FXML
    private void updateProduct() {
        if(userType.equals("Purchaser")){
            setEditable(rootPur);
        } else {
            setEditable(rootSel);
        }
        setUneditable(txt_pID);
        try {
            Connection con = SQLConnection.connectDb();
            assert con != null;
            String sql = "UPDATE PRODUCTS SET pName = ?, pType = ?, pPrice = ?, pStockQuantity = ?, pInfo = ? " +
                    "WHERE productID = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, txt_pName.getText());
            pst.setString(2, txt_pType.getText());
            pst.setDouble(3, (Double.parseDouble(txt_pPrice.getText())));
            pst.setInt(4, (Integer.parseInt(txt_pStockQuantity.getText())));
            pst.setString(5, txt_pInfo.getText());
            pst.setInt(6, Integer.parseInt(txt_pID.getText()));

            pst.execute();

            JOptionPane.showMessageDialog(null, "Update successfully!");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }


}

