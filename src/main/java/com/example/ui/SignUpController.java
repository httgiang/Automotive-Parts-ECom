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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class SignUpController {

    @FXML
    private TextField address;

    @FXML
    private Button forgotPassword;

    @FXML
    private Button li_logInButton;

    @FXML
    private TextField li_password;

    @FXML
    private Button li_signUpButton;

    @FXML
    private TextField li_userEmail;

    @FXML
    private TextField mobile;

    @FXML
    private TextField pincode;

    @FXML
    private Button su_logInButton;

    @FXML
    private TextField su_password;

    @FXML
    private Button su_signUpButton;

    @FXML
    private TextField su_userEmail;

    @FXML
    private TextField userName;
    @FXML
    private AnchorPane sideForm;

    @FXML
    private AnchorPane signUpPane;
    @FXML
    private AnchorPane logInPane;

    private Stage stage;
    private Scene scene;
    @FXML
    private void switchForm(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == li_signUpButton) {
            slider.setNode(sideForm);
            slider.setToX(-600);
            slider.setDuration(Duration.seconds(.7));
            slider.setAutoReverse(true);

//            slider.setOnFinished((ActionEvent e) ->{
//                logInPane.setVisible(false);
//            });
            slider.play();

        } else if (event.getSource() == su_logInButton){
            slider.setNode(sideForm);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.7));
            slider.play();
        }
    }

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    private Alert alert;

    @FXML
    public void logIn(ActionEvent event){
        if(li_userEmail.getText().isEmpty() || li_password.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter your email and password!");
            alert.showAndWait();
        } else {
            con = MySQLConnection.connectDb();
            String select = "SELECT email, password FROM USERS WHERE email = ? AND password = ?";
            try{
                pst = con.prepareStatement(select);
                pst.setString(1, li_userEmail.getText());
                pst.setString(2, li_password.getText());
                rs = pst.executeQuery();
                if(rs.next()){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully login!");
                    alert.showAndWait();
                    switchToHomePage(event);
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect email or password");
                    alert.showAndWait();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void switchToHomePage(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void signUp(ActionEvent event) throws Exception {
        if(userName.getText().isEmpty() || su_userEmail.getText().isEmpty() || mobile.getText().isEmpty() ||
            address.getText().isEmpty() || pincode.getText().isEmpty() || su_password.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the blank fields!");
            alert.showAndWait();
        } else {
            con = MySQLConnection.connectDb();
            String insert = "INSERT INTO USERS(name, email, mobile, address, pincode, password) VALUES(?,?,?,?,?,?)";
            try {
                pst = con.prepareStatement(insert);
                pst.setString(1, userName.getText());
                pst.setString(2, su_userEmail.getText());
                pst.setInt(3, Integer.parseInt(mobile.getText()));
                pst.setString(4, address.getText());
                pst.setInt(5, Integer.parseInt(pincode.getText()));
                pst.setString(6, su_password.getText());
                pst.execute();

                JOptionPane.showMessageDialog(null, "Information saved");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
}


