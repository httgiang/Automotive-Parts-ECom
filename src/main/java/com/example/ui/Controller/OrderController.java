package com.example.ui.Controller;

import com.example.ui.Entity.OrderCustomer;
import com.example.ui.SQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderController extends MenuBarMethods implements Initializable {
    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    @FXML
    private TableView<OrderCustomer> table;

    @FXML
    private TableColumn<?, ?> colOID;

    @FXML
    private TableColumn<?, ?> colPicture;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colProduct;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colTime;
    ObservableList<OrderCustomer> orderList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = SQLConnection.connectDb();
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        /*try{
            orderList.clear();
            pst = connection.prepareStatement("Select ORDER_ITEMS.orderId, ORDER_ITEMS.shipped, ORDER_ITEMS.quantity, PRODUCTS.pPrice, PRODUCTS.pName from ORDER_ITEMS, PRODUCTS where PRODUCTS.productId = ORDER_ITEMS.productId");
            rs= pst.executeQuery();
            while(rs.next()){
                float price = rs.getFloat("pPrice");
                orderList.add(new OrderCustomer(rs.getString("pName"),
                        rs.getString("orderId"),
                        rs.getInt("quantity"),

                        ))
            }
        }
        catch (SQLException e) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, e);

        }*/
    }

}
