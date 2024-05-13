package com.example.ui.Controller;
import com.example.ui.Entity.Stock;
import com.example.ui.SQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockController extends MenuBarMethods implements Initializable {
    @FXML
    private TableView<Stock> stockTable;
    @FXML
    private TableColumn<Stock, Button> actioncol;


    @FXML
    private TableColumn<Stock, String> typecol;
    @FXML
    private TableColumn<Stock, Integer> quantitycol;

    @FXML
    private TableColumn<Stock, Integer> pidcol;

    @FXML
    private TableColumn<Stock, String > pnamecol;

    @FXML
    private TableColumn<Stock, Float> pricecol;

    Connection connection = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    ObservableList<Stock> stock = FXCollections.observableArrayList();
    @FXML
    private Button addProductButton;


    @FXML
    public void switchToAddNewProduct(javafx.event.ActionEvent event) {
        switchToScene(event, "AddProductPage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = SQLConnection.connectDb();
        loadDataFromDatabase();
        setCellTable();
    }

    private void loadDataFromDatabase() {
        try{
            stock.clear();
            pst = connection.prepareStatement("Select productID, sellerEmail, pName, pType, pPrice, pStockQuantity from PRODUCTS");
            rs= pst.executeQuery();
            while(rs.next()){
                //float price = rs.getFloat("pPrice");
                stock.add(new Stock(rs.getString("productID"),
                        rs.getString("pName"),
                        rs.getInt("pStockQuantity"),
                        rs.getFloat ("pPrice"),
                        rs.getString("pType")
                        ));
            }
        }
        catch (SQLException e) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, e);

        }
    }
    private void setCellTable(){
        typecol.setCellValueFactory(new PropertyValueFactory<>("type"));
        pidcol.setCellValueFactory(new PropertyValueFactory<>("pID"));
        pnamecol.setCellValueFactory(new PropertyValueFactory<>("pName"));
        quantitycol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        pricecol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));//tên trong patient class
        Callback<TableColumn<Stock, String>, TableCell<Stock, String>> cellFactory = new Callback<TableColumn<Stock, String>, TableCell<Stock, String>>() {
            @Override
            public TableCell<Stock, String> call(final TableColumn<Stock, String> param) {
                final TableCell<Stock, String> cell = new TableCell<Stock, String>() {
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
                                Stock c = getTableView().getItems().get(getIndex());
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
    }
    @FXML
    private void removeRecord(Stock c){
        System.out.println(c.getPID());
        String query = "DELETE FROM PRODUCTS WHERE productID = ?";
        connection = SQLConnection.connectDb();
        try {
            pst =  connection.prepareStatement(query);
            pst.setString(1, c.getPID());
            pst.execute();
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        stock.clear();
        String query = " Select productID, sellerEmail, pName, pType, pPrice, pStockQuantity from PRODUCTS";
        try{
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();

            while(rs.next()){
                String pID = rs.getString("productID");
                String pName = rs.getString("pName");
                String pType = rs.getString("pType");
                int pQuantity = rs.getInt("pStockQuantity");
                float pPrice = rs.getFloat("pPrice");
                stock.add(new Stock(pID, pName, pQuantity, pPrice, pType));
                stockTable.setItems(stock);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}



