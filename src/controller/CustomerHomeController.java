package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerHomeController {

    @FXML
    private Button bookFlightButton;

    @FXML
    void bookFlight(ActionEvent event) throws IOException {
        // load register screen
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/bookflightscreen.fxml"));
        Stage stage = (Stage) bookFlightButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
