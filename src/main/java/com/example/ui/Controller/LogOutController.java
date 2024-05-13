package com.example.ui.Controller;

import com.example.ui.Entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LogOutController extends HelpMethods {

    @FXML
    void switchToLogin(ActionEvent event) {
        User.getInstance().setEmail(null);
        switchToScene(event, "SignUpLogIn.fxml");
    }
}

