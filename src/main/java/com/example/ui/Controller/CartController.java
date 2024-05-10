package com.example.ui.Controller;

import com.example.ui.Entity.Cart;
import com.example.ui.Entity.User;
import com.example.ui.SQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CartController extends HelpMethods implements Initializable {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    @FXML
    private TableView<Cart> TableCart;
    @FXML
    private TableColumn<Cart, String> pIDCol;

    @FXML
    private TableColumn<Cart, String> pNameCol;

    @FXML
    private TableColumn<Cart, Integer> quantityCol;
    @FXML
    private TableColumn<Cart, Float> amountCol;

    @FXML
    private TableColumn<Cart, String> removeCol;
    @FXML
    private TableColumn<Cart, Button> selectCol;
    String userEmail = User.getInstance().getEmail();

    ObservableList<Cart> cartList = FXCollections.observableArrayList();

    @FXML
    public void onPayNowButtonClicked(ActionEvent event){
        ObservableList<Cart> productsPaid = FXCollections.observableArrayList();

        switchToScene(event, "PaymentPage.fxml");

        for(Cart cartRow : cartList){
            if(cartRow.getSelect().isSelected()){
                productsPaid.add(cartRow);
            }
        }
        insertValuesIntoOrder(productsPaid);
        insertValuesIntoOrderItems(productsPaid);
        TableCart.getItems().removeAll(productsPaid);
        for(Cart cartRow : productsPaid){
            removeRecord(cartRow);
        }
    }

    private void insertValuesIntoOrder(ObservableList<Cart> prodList) {
        con = SQLConnection.connectDb();
        String insertOrder = "INSERT INTO ORDERS(purchaserEmail) VALUES(?)";
        try {
            pst = con.prepareStatement(insertOrder);
            pst.setString(1, userEmail);
            pst.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            throw new RuntimeException(e);
        }
    }
    private void insertValuesIntoOrderItems(ObservableList<Cart> prodList) {
        con = SQLConnection.connectDb();
        String insertOrderItems = "INSERT INTO ORDER_ITEMS(productId, quantity) VALUES(?,?)";
        try {
            for(Cart prod : prodList) {
                if (prod.getSelect().isSelected()) {
                    pst = con.prepareStatement(insertOrderItems);
                    pst.setString(1, prod.getPID());
                    pst.setInt(2, prod.getQuantity());
                    pst.execute();
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            throw new RuntimeException(e);
        }
    }

        @FXML
    private void refreshTable(){
        cartList.clear();
        String query = " SELECT P.productID, P.pName, C.productQuantity, P.pPrice FROM PRODUCTS P, CART C WHERE P.productID = C.productID AND C.purchaserEmail = ?";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1, userEmail);
            rs = pst.executeQuery();

            while(rs.next()){
                String pID = rs.getString("productID");
                String pName = rs.getString("pName");
                int pQuantity = rs.getInt("productQuantity");
                float pPrice = rs.getFloat("pPrice");
                cartList.add(new Cart(pID, pName, pQuantity, pPrice));
                TableCart.setItems(cartList);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadData() {
        con = SQLConnection.connectDb();
        refreshTable();

        pIDCol.setCellValueFactory(new PropertyValueFactory<>("pID"));
        pNameCol.setCellValueFactory(new PropertyValueFactory<>("pName"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        //Create a cell factory to insert button in every row
        Callback<TableColumn<Cart, String>, TableCell<Cart, String>> cellFactory = new Callback<TableColumn<Cart, String>, TableCell<Cart, String>>() {
            @Override
            public TableCell<Cart, String> call(final TableColumn<Cart, String> param) {
                final TableCell<Cart, String> cell = new TableCell<Cart, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null); //empty rows do not get buttons
                            setText(null);
                        } else {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ui/Button.fxml"));

                            final Button btn;
                            try {
                                btn = loader.load();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            btn.setStyle(
                                    " -fx-cursor: hand ;"
                                            + "-glyph-size:28px;"
                                            + "-fx-alignment: BASELINE_CENTER;" +
                                            "-fx-background-color: transparent"
                            );
                            btn.setOnAction((ActionEvent event) -> {
                                Cart c = getTableView().getItems().get(getIndex());
                                removeRecord(c);
                                setGraphic(btn);
                            });
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        removeCol.setCellFactory(cellFactory);
        selectCol.setCellValueFactory(new PropertyValueFactory<>("select"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    @FXML
    private void removeRecord(Cart c){
        System.out.println(c.getPID());
        String query = "DELETE FROM CART WHERE purchaserEmail = ? AND productID = ?";
        con = SQLConnection.connectDb();
        try {
            pst =  con.prepareStatement(query);
            pst.setString(1, userEmail);
            pst.setString(2, c.getPID());
            pst.execute();
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

