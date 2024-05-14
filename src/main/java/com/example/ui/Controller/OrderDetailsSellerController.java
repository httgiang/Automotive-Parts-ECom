package com.example.ui.Controller;

import com.example.ui.Entity.OrderDetails;
import com.example.ui.Entity.OrderSeller;
import com.example.ui.Entity.User;
import com.example.ui.SQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class OrderDetailsSellerController extends HelpMethods {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    private int oID;
    @FXML
    private TableView<OrderDetails> TableOD;

    @FXML
    private TableColumn<OrderDetails, Float> colAmount;


    @FXML
    private TableColumn<OrderDetails, Integer> colPID;

    @FXML
    private TableColumn<OrderDetails, String> colPName;

    @FXML
    private TableColumn<OrderDetails, Integer> colQuant;
    @FXML
    private TextField txt_oID;
    String email = User.getInstance().getEmail();
    ObservableList<OrderDetails> ordList = FXCollections.observableArrayList();

    public void setOID(int oID){
        this.txt_oID.setText("#" + oID);
        loadData(oID);
    }


    private void loadData(int oID) {
        con = SQLConnection.connectDb();
        refreshTable(oID);
        colPID.setCellValueFactory(new PropertyValueFactory<>("pID"));
        colPName.setCellValueFactory(new PropertyValueFactory<>("pName"));
        colQuant.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    @FXML
    private void refreshTable(int oID){
        ordList.clear();
        String query = " SELECT OI.productID, P.pName, OI.quantity, P.pPrice\n" +
                " FROM ORDER_ITEMS OI, PRODUCTS P" +
                " WHERE OI.productID = P.productID" +
                " AND P.sellerEmail = ?" +
                " AND OI.orderID = ?";
        try{
            pst = con.prepareStatement(query);
            System.out.println(oID);
            pst.setString(1, email);
            pst.setInt(2, oID);
            rs = pst.executeQuery();
            while(rs.next()){
                int pID = rs.getInt("productID");
                String pName = rs.getString("pName");
                int quantity = rs.getInt("quantity");
                float pPrice = rs.getFloat("pPrice");
                ordList.add(new OrderDetails(pID, pName, quantity, pPrice));
            }

            TableOD.setItems(ordList);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
