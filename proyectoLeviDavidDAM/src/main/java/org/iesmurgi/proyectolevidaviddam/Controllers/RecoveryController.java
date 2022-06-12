package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.iesmurgi.proyectolevidaviddam.Middleware.MailBot;

public class RecoveryController {

    @FXML
    private TextField email;

    @FXML
    private Button sendDataButton;

    @FXML
    void sendData(ActionEvent event) {
        MailBot mailBot = new MailBot();
        mailBot.sendMail(email.getText());
        email.clear();
    }

}
