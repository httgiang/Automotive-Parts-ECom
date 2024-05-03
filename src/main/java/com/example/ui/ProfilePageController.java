package com.example.ui;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    private Button homeButton;

    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton;

    @FXML
    private TextField txt_address;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_mobile;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_pincode;


    SignUpController logIn = new SignUpController();
    private String userEmail = logIn.getUserEmail();

    private void showUserInformation(String userEmail){
        try {
            Connection con = SQLConnection.connectDb();
            String sql = "SELECT * FROM USERS WHERE email = ?";
            assert con != null;
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, userEmail);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                txt_name.setText(rs.getString("name"));
                txt_email.setText(userEmail);
                txt_mobile.setText(rs.getString("mobile"));
                txt_address.setText(rs.getString("address"));
                txt_pincode.setText(rs.getString("pincode"));
                JOptionPane.showMessageDialog(null, "Successfully show info");
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    public void initialize(){
        showUserInformation(userEmail);
    }
}
