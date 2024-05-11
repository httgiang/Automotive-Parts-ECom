package com.example.ui.Controller;

import com.example.ui.Entity.Cart;
import com.example.ui.Entity.User;
import com.example.ui.SQLConnection;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageController extends HelpMethods implements Initializable {
    String type = User.getInstance().getType();
    String email = User.getInstance().getType();
    private Connection con;
    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        con = SQLConnection.connectDb();
        AnchorPane anchorPane = getAnchorPane(con);
        scrollPane.setContent(anchorPane);
        showProduct(anchorPane);
    }

    private void showProduct(AnchorPane anchorPane) {
        String select = "SELECT * FROM PRODUCTS";
        int width = 300;
        int height = 250;
        int xIndex = 0;
        int yIndex = 0;
        List<AnchorPane> productPaneList = new ArrayList<AnchorPane>();
        try{
            PreparedStatement pst = con.prepareStatement(select);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("productID");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ui/ProductInfo.fxml"));

                AnchorPane pane = loader.load();
                productPaneList.add(pane);

                ProductInfoController controller = loader.getController();
                controller.initProductInfo(productId);
            }
            for (AnchorPane pane : productPaneList) {
                pane.setLayoutX(xIndex * width);
                pane.setLayoutY(yIndex * height);
                pane.setPrefWidth(width);
                pane.setPrefHeight(height);

                anchorPane.getChildren().add(pane);
                xIndex++;
                if (xIndex % 4 == 0) {
                    xIndex = 0;
                    yIndex++;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private AnchorPane getAnchorPane(Connection con) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setLayoutX(0);
        anchorPane.setLayoutY(0);
        anchorPane.setPrefWidth(1200);
        anchorPane.setPrefHeight(getAnchorHeight());
        return anchorPane;
    }

    private int getAnchorHeight(){
        if(type.equals("Purchaser")){
            String select = "SELECT * FROM PRODUCTS";
            int countPur = 0;
            try{
                PreparedStatement pst = con.prepareStatement(select);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    countPur++;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return (int) (Math.ceil((double) countPur / 4)) * 250;
        } else {
            String select = "SELECT * FROM PRODUCTS WHERE sellerEmail = ?";
            int countSel = 0;
            try{
                PreparedStatement pst = con.prepareStatement(select);
                pst.setString(1,email);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    countSel++;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return (int) (Math.ceil((double) countSel / 4)) * 250;
        }

    }
}

