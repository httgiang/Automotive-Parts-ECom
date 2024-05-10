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

public class ViewProductSellerController extends MenuBarMethods {
    private Alert alert;
    PreparedStatement pst = null;

    @FXML
    private TextField txt_pid;

    @FXML
    private TextField txt_pname;

    @FXML
    private TextField txt_ptype;

    @FXML
    private TextField txt_pprice;

    @FXML
    private TextField txt_pstockQuantity;

    @FXML
    private TextArea txt_pdescription;

    @FXML
    private Button button_updateProduct;

    @FXML
    private ImageView pImg;

    private void showProductInformationFroSeller() {
        try {
            String productID = Products.getInstance().getProductID();

            Connection con = SQLConnection.connectDb();

            String sql = "SELECT * FROM PRODUCTS WHERE productID = ?";
            assert con != null;
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, productID);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                txt_pid.setText(productID);
                txt_pname.setText(rs.getString("pName"));
                txt_ptype.setText(rs.getString("pType"));
                txt_pprice.setText(String.valueOf((rs.getInt("pPrice"))));
                txt_pstockQuantity.setText(String.valueOf((rs.getInt("pStockQuantity"))));
                txt_pdescription.setText(rs.getString("pInfo"));
//                productImg.setImage();
            }
            JOptionPane.showMessageDialog(null, "Successfully show info!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void updateProductInformation() {
        try {
            Connection con = SQLConnection.connectDb();
            assert con != null;
            String sql = "UPDATE PRODUCTS SET productID = ?, pName = ?, pType = ?, pPrice = ?, pStockQuantity = ?, pInfor = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, txt_pid.getText());
            pst.setString(2, txt_pname.getText());
            pst.setString(3, txt_ptype.getText());
            pst.setDouble(4, (Double.parseDouble(txt_pprice.getText())));
            pst.setInt(5, (Integer.parseInt(txt_pstockQuantity.getText())));
            pst.setString(6, txt_pdescription.getText());

            pst.execute();

            JOptionPane.showMessageDialog(null, "Update successfully!");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // if button is clicked -> call method to update info in DB
    private void activateUpdateProductButton(ActionEvent event) {
        if (event.getSource() == button_updateProduct) {
            updateProductInformation();
        }
    }
    @FXML
    public void initialize(){
        showProductInformationFroSeller();
    }

}
