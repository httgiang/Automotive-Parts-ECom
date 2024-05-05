package com.example.ui;

import com.example.ui.Entity.User;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ProfilePageController extends MenuBarMethods {
    @FXML
    private Button cartButton;

    @FXML
    private Button homeButton;

    @FXML
    private Label labelAddress;

    @FXML
    private Label labelBank;

    @FXML
    private Label labelInfo;

    @FXML
    private Button logoutButton;

    @FXML
    private Button orderButton;

    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton;

    @FXML
    private TextField txt_address;

    @FXML
    private TextField txt_bank;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_info;

    @FXML
    private TextField txt_mobile;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_pincode;

    @FXML
    private Button updateButton;

    @FXML
    private void updateInformation(){
        try {
            Connection con = SQLConnection.connectDb();
            assert con != null;
            String sql = "UPDATE ACCOUNTS SET name = ?, mobile = ?, address = ?, pincode = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, txt_name.getText());
            pst.setInt(2, (Integer.parseInt(txt_mobile.getText())));
            pst.setString(3, txt_address.getText());
            pst.setInt(4, (Integer.parseInt(txt_pincode.getText())));
            pst.execute();
            JOptionPane.showMessageDialog(null, "Update successfully!");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void showUserInformation(){
        try {
            String userEmail = User.getInstance().getEmail();
            String userType = User.getInstance().getType();
            Connection con = SQLConnection.connectDb();
            String sql = "SELECT * FROM ACCOUNTS WHERE email = ?";
            assert con != null;
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, userEmail);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                txt_name.setText(rs.getString("name"));
                txt_email.setText(userEmail);
                txt_mobile.setText(String.valueOf((rs.getInt("mobile"))));
                txt_pincode.setText(String.valueOf((rs.getInt("pincode"))));
                if (userType.equals("Purchaser")) {
                    String queryPurchaser = "SELECT * FROM PURCHASERS WHERE purchaserEmail = ?";
                    pst = con.prepareStatement(queryPurchaser);
                    pst.setString(1, userEmail);
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        labelAddress.setVisible(true);
                        txt_address.setVisible(true);
                        txt_address.setText(rs.getString("address"));
                    }
                } else {
                    String querySeller = "SELECT * FROM SELLERS WHERE sellerEmail = ?";
                    pst = con.prepareStatement(querySeller);
                    pst.setString(1, userEmail);
                    rs = pst.executeQuery();

                    if (rs.next()) {
                        labelInfo.setVisible(true);
                        txt_info.setVisible(true);
                        labelBank.setVisible(true);
                        txt_bank.setVisible(true);
                        txt_info.setText(rs.getString("sellerInfo"));
                        txt_bank.setText(rs.getString("sellerBankAccount"));
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Successfully show info!");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    public void initialize(){
        showUserInformation();
    }
}
