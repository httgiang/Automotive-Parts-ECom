package com.example.ui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class SignUpController {

    @FXML
    private TextField address;

    @FXML
    private TextField mobile;

    @FXML
    private TextField password;

    @FXML
    private TextField pincode;

    @FXML
    private TextField userEmail;

    @FXML
    private TextField userName;

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @FXML
    public void signUp(ActionEvent event) throws Exception{
        con = MySQLConnection.connectDb();
        String query = "INSERT INTO USERS(name, email, mobile, address, pincode, password) VALUES(?,?,?,?,?,?)";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1, userName.getText());
            pst.setString(2, userEmail.getText());
            pst.setInt(3, Integer.parseInt(mobile.getText()));
            pst.setString(4, address.getText());
            pst.setInt(5, Integer.parseInt(pincode.getText()));
            pst.setString(6, password.getText());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Information saved");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

}
