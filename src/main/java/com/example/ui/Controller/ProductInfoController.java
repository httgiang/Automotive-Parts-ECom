package com.example.ui.Controller;

import com.example.ui.Entity.Cart;
import com.example.ui.Entity.User;
import com.example.ui.SQLConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductInfoController extends HelpMethods{
    private String productId;
    @FXML
    private ImageView imgProd;

    @FXML
    private TextField txt_money;

    @FXML
    private TextField txt_pName;
    @FXML
    private AnchorPane productInfoPane;


    public void initProductInfo(String productId) throws FileNotFoundException {
        this.productId = productId;
        loadDataFromProducts();
    }

    private void loadDataFromProducts() {
        try {
            Connection con = SQLConnection.connectDb();
            assert con != null;
            String sql = "SELECT * FROM PRODUCTS WHERE productID = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, productId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                txt_pName.setText(rs.getString("pName"));
                txt_money.setText(rs.getString("pPrice"));
                byte[] imgData = rs.getBytes("pImage");
                if(imgData != null){
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(rs.getBytes("pImage"));
                    Image image = new Image(inputStream);
                    imgProd.setImage(image);
                    imgProd.setPreserveRatio(false);
                } else {
                    imgProd.setImage(null);
                }
            }
            txt_pName.setEditable(false);
            txt_money.setEditable(false);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onViewButtonClicked(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ui/ViewProduct.fxml"));
            Parent root = loader.load();

            ViewProductController controller = loader.getController();
            controller.setProductID(productId);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
