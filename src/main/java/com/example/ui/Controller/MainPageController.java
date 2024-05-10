package com.example.ui.Controller;

import com.example.ui.SQLConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageController extends MenuBarMethods implements Initializable {

    private Connection con;
    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        con = SQLConnection.connectDb();
        AnchorPane anchorPane = createAnchorPane(con);
        scrollPane.setContent(anchorPane);
        showProduct(anchorPane);
    }

    private void showProduct(AnchorPane anchorPane) {
        String select = "SELECT * FROM PRODUCTS";
        int width = 300;
        int height = 200;
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

                ProductInfoController controller  = loader.getController();
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

    private AnchorPane createAnchorPane(Connection con) {
        String select = "SELECT * FROM PRODUCTS";
        int count = 0;
        try{
            PreparedStatement pst = con.prepareStatement(select);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                count++;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setLayoutX(0);
        anchorPane.setLayoutY(0);
        anchorPane.setPrefWidth(1200);
        anchorPane.setPrefHeight((int) (Math.ceil((double) count / 4)) * 200);

        return anchorPane;
    }
}

