package com.example.ui.Controller;

import com.example.ui.Entity.OrderSeller;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ShippedController extends HelpMethods implements Initializable {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    @FXML
    private TableView<OrderSeller> TableOrderSel;
    @FXML
    private TableColumn<OrderSeller, String> colAction;

    @FXML
    private TableColumn<OrderSeller, String> colAdr;

    @FXML
    private TableColumn<OrderSeller, Float> colMoney;

    @FXML
    private TableColumn<OrderSeller, Integer> colOID;

    @FXML
    private TableColumn<OrderSeller, String> colPurName;

    String email = User.getInstance().getEmail();
    ObservableList<OrderSeller> ordList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    private void loadData() {
        con = SQLConnection.connectDb();
        refreshTable();

        colOID.setCellValueFactory(new PropertyValueFactory<>("oID"));
        colPurName.setCellValueFactory(new PropertyValueFactory<>("purName"));
        colAdr.setCellValueFactory(new PropertyValueFactory<>("purAddress"));
        colMoney.setCellValueFactory(new PropertyValueFactory<>("money"));
    }
    @FXML
    private void refreshTable(){
        ordList.clear();
        String query = "SELECT O.orderID, A.name, P.address, O.money" +
                " FROM ORDERS O, PURCHASERS P, ORDER_ITEMS OI, PRODUCTS Pro, ACCOUNTS A " +
                "WHERE P.purchaserEmail = O.purchaserEmail " +
                "AND O.orderID = OI.orderID " +
                "AND OI.productID = Pro.productID " +
                "AND A.email = P.purchaserEmail " +
                "AND OI.shipped = 1 "+
                "AND Pro.sellerEmail = ? " +
                "GROUP BY O.orderID, A.name, P.address, O.money;";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1, email);
            rs = pst.executeQuery();
            while(rs.next()){
                int oID = rs.getInt("orderID");
                String pName = rs.getString("name");
                String address = rs.getString("address");
                float money = rs.getFloat("money");
                ordList.add(new OrderSeller(oID, pName, address, money));
            }
            TableOrderSel.setItems(ordList);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
