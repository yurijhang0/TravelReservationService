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
            viewCustomersButton, viewOwnersButton, backButton;

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
        Stage stage = (Stage) removeFlightButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void scheduleFlight(ActionEvent event) throws IOException {
        // load schedule flight screen
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/scheduleflightscreen.fxml"));
        Stage stage = (Stage) scheduleFlightButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void viewAirlines(ActionEvent event) throws IOException {
        // load view flight screen
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/viewairlinescreen.fxml"));
<<<<<<< HEAD
        Stage stage = (Stage) viewAirlinesButton.getScene().getWindow();
=======
        Stage stage = (Stage) processDateButton.getScene().getWindow();
>>>>>>> 8eb782251ea40f1cfbf2353134005ddda8271825
        stage.setScene(new Scene(root));
    }

    @FXML
    void viewAirports(ActionEvent event) throws IOException {
        // load view airports screen
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/viewairportscreen.fxml"));
        Stage stage = (Stage)viewAirportsButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void viewCustomers(ActionEvent event) throws IOException {
        // load view customers screen
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/viewcustomerscreen.fxml"));
        Stage stage = (Stage) viewCustomersButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void viewOwners(ActionEvent event) throws IOException {
        // load view owners screen
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/viewownerscreen.fxml"));
        Stage stage = (Stage) viewOwnersButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        // load admin home screen
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/loginscreen.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
