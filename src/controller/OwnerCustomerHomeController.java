package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class OwnerCustomerHomeController {

    @FXML
    private Button customerHomeButton, ownerHomeButton;

    @FXML
    void customerHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/customerfxml/customerHome.fxml"));
        Stage stage = (Stage) customerHomeButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void ownerHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/ownerfxml/ownerhomescreen.fxml"));
        Stage stage = (Stage) ownerHomeButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
