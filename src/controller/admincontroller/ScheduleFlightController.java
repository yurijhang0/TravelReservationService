package controller.admincontroller;

import controller.DBConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class ScheduleFlightController {

    @FXML
    private Button cancelButton, scheduleButton;

    @FXML
    private TextField airlineTextField, arrivalTimeTextField, capacityTextField, costPerPersonTextField,
            currentDateTextField, departureTimeTextField, flightDateTextField, flightNumberTextField,
            fromAirportTextField, toAirportTextField;

    private String flightNum, airlineName, fromAirport, toAirport, departureTime, arrivalTime, flightDate,
            cost, capacity, currentDate;

    @FXML
    void cancel(ActionEvent event) throws IOException {
        // load admin home screen
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/adminhomescreen.fxml"));
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void scheduleFlight(ActionEvent event) {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        try {
            // prepare call
            CallableStatement statementScheduleFlight =
                    connectDB.prepareCall("{call schedule_flight(?,?,?,?,?,?,?,?,?,?)}");

            flightNum = flightNumberTextField.getText();
            airlineName = airlineTextField.getText();
            fromAirport = fromAirportTextField.getText();
            toAirport = toAirportTextField.getText();
            departureTime = departureTimeTextField.getText();
            arrivalTime = arrivalTimeTextField.getText();
            flightDate = flightDateTextField.getText();
            cost = costPerPersonTextField.getText();
            capacity = capacityTextField.getText();
            currentDate = currentDateTextField.getText();

            statementScheduleFlight.setString(1, flightNum);
            statementScheduleFlight.setString(2, airlineName);
            statementScheduleFlight.setString(3, fromAirport);
            statementScheduleFlight.setString(4, toAirport);
            statementScheduleFlight.setTime(5, Time.valueOf(departureTime));
            statementScheduleFlight.setTime(6, Time.valueOf(arrivalTime));
            statementScheduleFlight.setDate(7, Date.valueOf(flightDate));
            statementScheduleFlight.setInt(8, Integer.parseInt(cost));
            statementScheduleFlight.setInt(9, Integer.parseInt(capacity));
            statementScheduleFlight.setDate(10, Date.valueOf(currentDate));

            // call procedure
            statementScheduleFlight.execute();
            statementScheduleFlight.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
