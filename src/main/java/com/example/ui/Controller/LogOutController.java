package com.example.ui.Controller;

import com.example.ui.Entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LogOutController extends MenuBarMethods {

    @FXML
    private Button cancelButton;

    @FXML
    private Button yesButton;

    @FXML
    void switchToLogin(ActionEvent event) {
        User.getInstance().setEmail(null);
        switchToScene(event, "SignUpLogIn.fxml");
    }
}

