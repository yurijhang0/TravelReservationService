package controller.customercontroller;

import controller.DBConnectionClass;
import controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import table_structures.Flight2;
import table_structures.Flight3;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CancelFlight implements Initializable{

    @FXML
    private ChoiceBox<String> airlineDropDown;

    @FXML
    private Button backButton, cancelFlightButton, filterButton, resetButton;

    @FXML
    private TextField currentDateTextField, flightNumberTextField;

    @FXML
    private TableView<Flight3> cancelFlightTableView;

    @FXML
    private TableColumn<Flight3, String> dateColumn, numberColumn, selectColumn, airlineColumn;

    private String airline, currentDate, customerEmail;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        currentDate = date.toString();
        currentDateTextField.setText(currentDate);
        currentDateTextField.setDisable(true);

        populateAirlineDropDown();
        setAirline(airlineDropDown.getValue());

        customerEmail = LoginController.getEmailText();
        airlineColumn.setCellValueFactory(new PropertyValueFactory<>("airlineName"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("airlineNum"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("flightDate"));

        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        airline = airlineDropDown.getValue();
        String currentDate = currentDateTextField.getText();
        String flightNum = flightNumberTextField.getText();

        String selectStr =
                String.format("select Airline_Name, Flight_Num, Flight_Date from flight where Flight_Date > '%s';",
                        currentDate);
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<Flight3> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new Flight3(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getString(3))
                );
            }

            // set tableview with data
            cancelFlightTableView.setItems(data);

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/customerfxml/customerHome.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void populateAirlineDropDown() {
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery("select airline.Airline_Name from airline");

            // populate drop down with airline options
            while (queryResult.next()) {
                airlineDropDown.getItems().add(queryResult.getString(1));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void cancelFlight(ActionEvent event) {
        if (cancelFlightTableView.getSelectionModel().getSelectedItem() != null) {

            String airlineName = cancelFlightTableView.getSelectionModel().getSelectedItem().getAirlineName();
            String flightDate = cancelFlightTableView.getSelectionModel().getSelectedItem().getFlightDate();
            String flightNum = cancelFlightTableView.getSelectionModel().getSelectedItem().getAirlineNum();


            DBConnectionClass connectNow = new DBConnectionClass();
            Connection connectDB = connectNow.getConnection();
            int initFlightCount = getFlightCount();

            try {
                CallableStatement statementRemFlight =
                        connectDB.prepareCall("{call cancel_flight_booking(?,?,?,?,?)}");
                statementRemFlight.setString(1, customerEmail);
                statementRemFlight.setString(2, flightNum);
                statementRemFlight.setString(3, airlineName);
                statementRemFlight.setDate(4, Date.valueOf(currentDate));

                statementRemFlight.execute();
                statementRemFlight.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            int newFlightCount = getFlightCount();

            // remove from table if removed in DB
            if (initFlightCount != newFlightCount) {
                // remove from tableview
                cancelFlightTableView.getItems().remove(cancelFlightTableView.getSelectionModel().getSelectedItem());
            }
        }
    }

    private int getFlightCount() {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();
        Statement selectStatement = null;
        String ctFlightQry = String.format("select count(*) from book where Customer = '%s' and Was_Cancelled = 0;",
                customerEmail);

        // NOT SURE IF THE ABOVE LINE IS CORRECT...


        // number of flights
        int flightCount = 0;

        try {
            // get initial row count before procedure call
            selectStatement = connectDB.createStatement();
            ResultSet queryResult = selectStatement.executeQuery(ctFlightQry);
            if (queryResult.next()) {
                flightCount = queryResult.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flightCount;
    }

    @FXML
    void filter(ActionEvent event) {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        airline = airlineDropDown.getValue();
        String currentDate = currentDateTextField.getText();
        String flightNum = flightNumberTextField.getText();

        String selectStr =
                String.format("select Airline_Name, Flight_Num, Flight_Date from flight where Flight_Date > '%s'",
                        currentDate);

        // add to select statement based on filter fields used
        if (airline != null) {
            selectStr += String.format(" and Airline_Name like '%s'", airline);
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
            final ObservableList<Flight3> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new Flight3(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getString(3))
                );
            }

            // set tableview with data
            cancelFlightTableView.setItems(data);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void reset(ActionEvent event) {
        airlineDropDown.setValue("");
        flightNumberTextField.setText("");
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

}
