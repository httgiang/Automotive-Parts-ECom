package com.example.ui;


import com.example.ui.Entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpController{
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    private Alert alert;
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField address;

    @FXML
    private Label addressLabel;

    @FXML
    private TextField bankAcc;

    @FXML
    private TextField confirmPassword;

    @FXML
    private Button forgotPassword;

    @FXML
    private Label labelBankAcc;

    @FXML
    private Button li_logInButton;

    @FXML
    private TextField li_password;

    @FXML
    private Button li_signUpButton;

    @FXML
    private TextField li_userEmail;

    @FXML
    private AnchorPane logInPane;

    @FXML
    private TextField mobile;

    @FXML
    private TextField pincode;

    @FXML
    private TextField shopInfo;

    @FXML
    private Label shopInfoLabel;

    @FXML
    private AnchorPane signUpPane;

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
    private ComboBox<String> typeBox;

    @FXML
    void logIn(ActionEvent event) {
        if(li_userEmail.getText().isEmpty() || li_password.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please enter your email and password!");
            alert.showAndWait();
        } else {
            con = SQLConnection.connectDb();
            String select = "SELECT email, password FROM ACCOUNTS WHERE email = ? AND password = ?";

            try{
                pst = con.prepareStatement(select);
                pst.setString(1, li_userEmail.getText());
                pst.setString(2, li_password.getText());
                rs = pst.executeQuery();
                if(rs.next()){
                    User.getInstance().setEmail(li_userEmail.getText());
                    User.getInstance().setType(getAccountType(li_userEmail.getText()));
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
    void signUp(ActionEvent event) {
        if (userName.getText().isEmpty() || su_userEmail.getText().isEmpty() || mobile.getText().isEmpty() || pincode.getText().isEmpty() || su_password.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the blank fields!");
            alert.showAndWait();
        } else {
            con = SQLConnection.connectDb();
            String insertAccounts = "INSERT INTO ACCOUNTS(name, email, mobile, pincode, password) VALUES(?,?,?,?,?)";
            try {
                pst = con.prepareStatement(insertAccounts);
                pst.setString(1, userName.getText());
                pst.setString(2, su_userEmail.getText());
                pst.setInt(3, Integer.parseInt(mobile.getText()));
                pst.setInt(4, Integer.parseInt(pincode.getText()));
                pst.setString(5, su_password.getText());
                pst.execute();
                if (getSelectedType().equals("Purchaser")) {
                    String insertPurchasers = "INSERT INTO PURCHASERS(purchaserEmail, address) VALUES (?, ?)";
                    try {
                        pst = con.prepareStatement(insertPurchasers);
                        pst.setString(2, su_userEmail.getText());
                        pst.setString(2, address.getText());
                        pst.execute();
                        JOptionPane.showMessageDialog(null, "Information saved");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                } else {
                    String insertSellers = "INSERT INTO SELLERS(sellerEmail, sellerInfo, sellerBankAccount) VALUES(?,?,?)";
                    try {
                        pst = con.prepareStatement(insertSellers);
                        pst.setString(1, su_userEmail.getText());
                        pst.setString(2, shopInfo.getText());
                        pst.setString(3, bankAcc.getText());
                        pst.execute();
                        JOptionPane.showMessageDialog(null, "Information saved");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getAccountType(String email) throws SQLException {
        con = SQLConnection.connectDb();
        String sql = "SELECT * FROM PURCHASERS WHERE purchaserEmail = ?";
        pst = con.prepareStatement(sql);
        pst.setString(1, email);
        rs = pst.executeQuery();
        if(rs.next()){
            return "Purchaser";
        }
        else {
            return "Seller";
        }
    }
    @FXML
    void switchForm(ActionEvent event) {
        if (event.getSource() == li_signUpButton) {
            logInPane.setVisible(false);
            signUpPane.setVisible(true);

        } else if (event.getSource() == su_logInButton){
            signUpPane.setVisible(false);
            logInPane.setVisible(true);
        }
    }

    public String getSelectedType(){
        return typeBox.getSelectionModel().getSelectedItem();
    }
    @FXML
    public void selectType(){
        String s = getSelectedType();
        User.getInstance().setType(s);
        if(s.equals("Purchaser")){
            shopInfoLabel.setVisible(false);
            shopInfo.setVisible(false);
            labelBankAcc.setVisible(false);
            bankAcc.setVisible(false);
            addressLabel.setVisible(true);
            address.setVisible(true);
        } else if(s.equals("Seller")){
            addressLabel.setVisible(false);
            address.setVisible(false);
            shopInfoLabel.setVisible(true);
            shopInfo.setVisible(true);
            labelBankAcc.setVisible(true);
            bankAcc.setVisible(true);
        }
    }
    @FXML
    public void initialize(){
        ObservableList<String> list = FXCollections.observableArrayList("Purchaser", "Seller");
        typeBox.setItems(list);
    }

}
