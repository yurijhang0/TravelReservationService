package controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import table_structures.Flight;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RemoveFlightController implements Initializable {

    @FXML
    private TableView flightTable;

    @FXML
    private TableColumn airlineColumn, dateColumn, numColumn;

    @FXML
    private ChoiceBox<String> airlineDropDown;

    @FXML
    private Button backButton, filterButton, removeButton;

    @FXML
    private TextField currentDateTextField, flightNumTextField, fromDateTextField, toDateTextField;

    private String airline, fromDate, toDate, currentDate, flightNum;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        populateAirlineDropDown();
        setAirline(airlineDropDown.getValue());
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        currentDate = date.toString();
        currentDateTextField.setText(currentDate);
        currentDateTextField.setDisable(true);
    }

    @FXML
    private void populateAirlineDropDown() {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery("select Airline_Name from airline;");

            // populate drop down with airline options
            while (queryResult.next()) {
                airlineDropDown.getItems().add(queryResult.getString(1));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void filter(ActionEvent event) {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        fromDate = fromDateTextField.getText();
        toDate = toDateTextField.getText();
        currentDate = currentDateTextField.getText();
        flightNum = flightNumTextField.getText();

        String selectStr =
                String.format("select Airline_Name, Flight_Num, Flight_Date from flight where Flight_Date > '%s'",
                        currentDate);

        // add to select statement based on filter fields used
        if (!fromDate.isBlank() && !toDate.isBlank()) {
            selectStr += String.format(" and Flight_Date between '%s' and '%s'", fromDate, toDate);
        }
        if (!flightNum.isBlank()) {
            selectStr += String.format(" and Flight_Num = %s", flightNum);
        }
        selectStr += ";";   // close select statement

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<Flight> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new Flight(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getString(3)));
            }

            // set table data as data
            flightTable.setItems(data);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    // getter and setter for airline String
    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public void back(ActionEvent actionEvent) {
    }

    public void remove(ActionEvent actionEvent) {
    }
}

