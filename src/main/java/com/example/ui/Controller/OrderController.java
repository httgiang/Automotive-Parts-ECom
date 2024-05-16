package com.example.ui.Controller;

import com.example.ui.Entity.OrderCustomer;
import com.example.ui.Entity.User;
import com.example.ui.SQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
//if shippedn= 0: ÚNSHIPPED, = 1, SHIPPED

public class OrderController extends HelpMethods implements Initializable{
    private Connection connection = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    @FXML
    private TableView<OrderCustomer> table;
    private CallableStatement call = null;

    @FXML
    private TableColumn<OrderCustomer, Integer> colOID;
    @FXML
    private TableColumn<OrderCustomer, Integer> colPID;

    @FXML
    private TableColumn<OrderCustomer, String> colPName;

    @FXML
    private TableColumn<OrderCustomer, Integer> colQuant;

    @FXML
    private TableColumn<OrderCustomer, ?> colAmount;

    @FXML
    private TableColumn<OrderCustomer, ?> statusCol;
    String email = User.getInstance().getEmail();
    ObservableList<OrderCustomer> orderList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = SQLConnection.connectDb();
        loadDataFromDatabase();
        table.setItems(orderList);
        setCellTable();
    }

    private void setCellTable() {
        colOID.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        colPID.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        colPName.setCellValueFactory(new PropertyValueFactory<>("Product"));
        colQuant.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));//tên trong patient class
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadDataFromDatabase() {
        try {
            orderList.clear();
            call = connection.prepareCall("{call fillOrderPurTable(?)}");
            call.setString(1, email);
            call.execute();
            rs = call.getResultSet();
            while (rs.next()) {
                orderList.add(new OrderCustomer(rs.getInt("productID"),
                        rs.getString("pName"),
                        rs.getInt("orderID"),
                        rs.getInt("quantity"),
                        rs.getFloat("pPrice"),
                        rs.getInt("shipped")));
            }
        }
        catch (SQLException e) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

}
