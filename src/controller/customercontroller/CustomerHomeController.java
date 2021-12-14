package controller.customercontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomerHomeController {

    @FXML
    private Button bookFlightButton, viewReservationButton, cancelFlightButton, viewFlightButton,
            cancelReservationButton,logoutButton, rateOwnersButton, reservePropertyButton, reviewPropertyButton, viewPropertiesButton;


    @FXML
    void bookFlight(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/customerfxml/bookFlight.fxml"));
        Stage stage = (Stage) bookFlightButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void cancelFlights(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/customerfxml/cancelFlight.fxml"));
        Stage stage = (Stage) cancelFlightButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void cancelReservation(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/customerfxml/customerCancelProperty.fxml"));
        Stage stage = (Stage) cancelReservationButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/loginscreen.fxml"));
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void rateOwners(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/customerfxml/customerRateOwner.fxml"));
        Stage stage = (Stage) rateOwnersButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void reserveProperty(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/customerfxml/customerReserveProperty.fxml"));
        Stage stage = (Stage) reservePropertyButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void reviewProperty(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/customerfxml/customerReviewProperty.fxml"));
        Stage stage = (Stage) reviewPropertyButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void viewProperties(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/customerfxml/viewProperties.fxml"));
        Stage stage = (Stage) viewPropertiesButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void viewReservation(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/customerfxml/viewPropertyReservations.fxml"));
        Stage stage = (Stage) viewReservationButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void viewFlight(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/customerfxml/viewflightscreen.fxml"));
        Stage stage = (Stage) viewFlightButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}