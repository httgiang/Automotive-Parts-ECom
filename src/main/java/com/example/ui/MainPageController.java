package com.example.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainPageController {

    private Stage stage;
    private Scene scene;
    @FXML
    private Button cartButton;

    @FXML
    private ImageView homeButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button orderButton;

    @FXML
    private Button profileButton;

    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton;

    @FXML
    public void switchToProfile(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ProfilePage.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

