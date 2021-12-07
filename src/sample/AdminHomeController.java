package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminHomeController {

    @FXML
    private Button processDateButton, removeFlightButton, scheduleFlightButton, viewAirlinesButton, viewAirportsButton,
            viewCustomersButton, viewOwnersButton;

    @FXML
    void processDate(ActionEvent event) throws IOException {
        // load process date screen
        Parent root = FXMLLoader.load(getClass().getResource("processdatescreen.fxml"));
        Stage stage = (Stage) processDateButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void removeFlight(ActionEvent event) {

    }

    @FXML
    void scheduleFlight(ActionEvent event) {

    }

    @FXML
    void viewAirlines(ActionEvent event) {

    }

    @FXML
    void viewAirports(ActionEvent event) {

    }

    @FXML
    void viewCustomers(ActionEvent event) {

    }

    @FXML
    void viewOwners(ActionEvent event) {

    }

}
