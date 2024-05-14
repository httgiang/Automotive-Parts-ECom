package com.example.ui.Controller;
import com.example.ui.Entity.OrderSeller;
import com.example.ui.Entity.User;
import com.example.ui.SQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class OrderSellerController extends HelpMethods implements Initializable {
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


    @FXML
    private void refreshTable(){
        ordList.clear();
        String query = "SELECT O.orderID, A.name, P.address, O.money" +
                " FROM ORDERS O, PURCHASERS P, ORDER_ITEMS OI, PRODUCTS Pro, ACCOUNTS A " +
                "WHERE P.purchaserEmail = O.purchaserEmail " +
                "AND O.orderID = OI.orderID " +
                "AND OI.productID = Pro.productID " +
                "AND A.email = P.purchaserEmail " +
                "AND OI.shipped = 0 "+
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

    private void loadData() {
        con = SQLConnection.connectDb();
        refreshTable();

        colOID.setCellValueFactory(new PropertyValueFactory<>("oID"));
        colPurName.setCellValueFactory(new PropertyValueFactory<>("purName"));
        colAdr.setCellValueFactory(new PropertyValueFactory<>("purAddress"));
        colMoney.setCellValueFactory(new PropertyValueFactory<>("money"));
        Callback<TableColumn<OrderSeller, String>, TableCell<OrderSeller, String>> cellFactory = new Callback<>() {
            @Override
            public TableCell<OrderSeller, String> call(final TableColumn<OrderSeller, String> param) {
                final TableCell<OrderSeller, String> cell = new TableCell<OrderSeller, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null); //empty rows do not get buttons
                            setText(null);
                        } else {
                            Button viewBtn = new Button("View");
                            Button shipBtn = new Button("Ship");
                            viewBtn.setOnAction((ActionEvent event) -> {
                                OrderSeller ord = getTableView().getItems().get(getIndex());
                                onViewButtonClicked(event, ord.getOID());
                            });
                            shipBtn.setOnAction((ActionEvent event) -> {
                                OrderSeller ord = getTableView().getItems().get(getIndex());
                                onShipButtonClicked(ord.getOID());
                                TableOrderSel.getItems().remove(ord);
                            });
                            HBox manageBtn = new HBox(viewBtn, shipBtn);
                            manageBtn.setStyle("-fx-alignment:center");

                            setGraphic(manageBtn);
                        }
                    }
                };
                return cell;
            }
        };

        colAction.setCellFactory(cellFactory);
    }
    private void onShipButtonClicked(int oID){
        con = SQLConnection.connectDb();
        String query = "UPDATE ORDER_ITEMS SET shipped = 1 WHERE orderID = ?";
        try {
            pst = con.prepareStatement(query);
            pst.setInt(1, oID);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onViewButtonClicked(ActionEvent event, int oID){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ui/OrderDetailsSeller.fxml"));
            Parent root = loader.load();

            OrderDetailsSellerController controller = loader.getController();
            controller.setOID(oID);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
