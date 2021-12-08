package controller.admincontroller;

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
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/processdatescreen.fxml"));
        Stage stage = (Stage) processDateButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void removeFlight(ActionEvent event) throws IOException {
        // load remove flight screen
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/removeflightscreen.fxml"));
        Stage stage = (Stage) processDateButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void scheduleFlight(ActionEvent event) throws IOException {
        // load schedule flight screen
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/scheduleflightscreen.fxml"));
        Stage stage = (Stage) processDateButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void viewAirlines(ActionEvent event) throws IOException {
        // load view flight screen
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/viewairlinescreen.fxml"));
        Stage stage = (Stage) processDateButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void viewAirports(ActionEvent event) throws IOException {
        // load view airports screen
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/viewairportscreen.fxml"));
        Stage stage = (Stage) processDateButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void viewCustomers(ActionEvent event) throws IOException {
        // load view customers screen
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/viewcustomersscreen.fxml"));
        Stage stage = (Stage) processDateButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void viewOwners(ActionEvent event) throws IOException {
        // load view owners screen
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/viewownersscreen.fxml"));
        Stage stage = (Stage) processDateButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
