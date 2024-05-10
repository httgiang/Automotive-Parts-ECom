package com.example.ui.Controller;

import com.example.ui.Entity.User;
import com.example.ui.SQLConnection;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class AddProductController extends HelpMethods {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    @FXML
    private TextField txt_pDes;


    @FXML
    private TextField txt_pName;

    @FXML
    private TextField txt_pPrice;

    @FXML
    private TextField txt_pQuan;

    @FXML
    private TextField txt_pType;

    private byte[] imageData;

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public byte[] getImageData() {
        return imageData;
    }

    String sellerEmail = User.getInstance().getEmail();
    @FXML
    private void chooseImg() throws FileNotFoundException {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(null);
        try{
            if (file != null) {
                String filePath = file.getAbsolutePath();
                byte[] imageData = readImageData(filePath);
                setImageData(imageData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] readImageData(String filePath) throws Exception {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            return buffer;
        }
    }

    @FXML
    private void insertData() {
        con = SQLConnection.connectDb();
        String insert = "INSERT INTO PRODUCTS(sellerEmail, pName, pType, pInfo, pPrice, pStockQuantity, pImage) VALUES(?,?,?,?,?,?,?)";
        try {
            pst = con.prepareStatement(insert);
            pst.setString(1, sellerEmail);
            pst.setString(2, txt_pName.getText());
            pst.setString(3, txt_pType.getText());
            pst.setString(4, txt_pDes.getText());
            pst.setFloat(5, Float.parseFloat(txt_pPrice.getText()));
            pst.setInt(6, Integer.parseInt(txt_pQuan.getText()));
            pst.setBytes(7, getImageData());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Add successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }
    }



}
