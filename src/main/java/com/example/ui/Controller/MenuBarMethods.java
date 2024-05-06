package com.example.ui.Controller;

import com.example.ui.Entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MenuBarMethods {
    private static Stage stage;
    private static Scene scene;

    public void switchToScene(ActionEvent event, String fileName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/ui/" + fileName));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void switchToHome(ActionEvent event) {
        System.out.println(User.getInstance().getType());
        if (User.getInstance().getType().equals("Purchaser")) {
            switchToScene(event, "MainPage.fxml");
        } else {
            switchToScene(event, "MainPageSeller.fxml");
        }
    }

    @FXML
    public void switchToProfile(ActionEvent event) {
        if (User.getInstance().getType().equals("Purchaser")) {
            switchToScene(event, "ProfilePage.fxml");
        } else {
            switchToScene(event, "ProfilePageSeller.fxml");
        }
    }

    @FXML
    public void switchToCart(ActionEvent event) {
        switchToScene(event, "Cart.fxml");
    }

    @FXML
    public void switchToOrder(ActionEvent event) {
        if (User.getInstance().getType().equals("Purchaser")) {
            switchToScene(event, "Order.fxml");
        } else {
            switchToScene(event, "OrderSeller.fxml");
        }
    }

    @FXML
    public void switchToStock(ActionEvent event) {
        switchToScene(event, "Stock.fxml");
    }

    @FXML
    public void switchToShipped(ActionEvent event) {
        switchToScene(event, "Shipped.fxml");
    }

    @FXML
    public void switchToLogout(ActionEvent event) {
        switchToScene(event, "LogOut.fxml");
    }
}
