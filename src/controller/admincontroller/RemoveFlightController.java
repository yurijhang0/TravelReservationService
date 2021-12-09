package controller.admincontroller;

import controller.DBConnectionClass;
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
import table_structures.Flight;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class RemoveFlightController implements Initializable {

    @FXML
    private TableView<Flight> flightTable;

    @FXML
    private TableColumn<Flight, String> airlineColumn, dateColumn, numColumn;

    @FXML
    private ChoiceBox<String> airlineDropDown;

    @FXML
    private Button backButton, filterButton, removeButton;

    @FXML
    private TextField currentDateTextField, flightNumTextField, fromDateTextField, toDateTextField;

    private String airline, currentDate;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // populate dropdown
        populateAirlineDropDown();
        setAirline(airlineDropDown.getValue());

        // column names
        airlineColumn.setCellValueFactory(new PropertyValueFactory<>("airlineName"));
        numColumn.setCellValueFactory(new PropertyValueFactory<>("airlineNum"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("flightDate"));

        // set current date
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

        airline = airlineDropDown.getValue();
        String fromDate = fromDateTextField.getText();
        String toDate = toDateTextField.getText();
        String currentDate = currentDateTextField.getText();
        String flightNum = flightNumTextField.getText();

        String selectStr =
                String.format("select Airline_Name, Flight_Num, Flight_Date from flight where Flight_Date > '%s'",
                        currentDate);

        // add to select statement based on filter fields used
        if (airline != null) {
            selectStr += String.format(" and Airline_Name like '%s'", airline);
        }
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
                        queryResult.getString(3))
                );
            }

            // set tableview with data
            flightTable.setItems(data);

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    // setter for airline String
    public void setAirline(String airline) {
        this.airline = airline;
    }

    public void back(ActionEvent actionEvent) throws IOException {
        // load admin home screen
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/adminhomescreen.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void remove(ActionEvent actionEvent) {
        // do nothing if no row is selected
        if (flightTable.getSelectionModel().getSelectedItem() != null) {
            // get removed fields
            String airlineName = flightTable.getSelectionModel().getSelectedItem().getAirlineName();
            String airlineNum = flightTable.getSelectionModel().getSelectedItem().getAirlineNum();

            // connect to DB
            DBConnectionClass connectNow = new DBConnectionClass();
            Connection connectDB = connectNow.getConnection();

            // int var for flight count comparison after removal
            int initFlightCount = getFlightCount();
            try {
                // prepare call
                CallableStatement statementRemFlight =
                        connectDB.prepareCall("{call remove_flight(?,?,?)}");

                statementRemFlight.setString(1, airlineNum);
                statementRemFlight.setString(2, airlineName);
                statementRemFlight.setDate(3, Date.valueOf(currentDate));

                // call procedure
                statementRemFlight.execute();
                statementRemFlight.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            // get query again after attempting to remove
            int newFlightCount = getFlightCount();

            // remove from table if removed in DB
            if (initFlightCount != newFlightCount) {
                // remove from tableview
                flightTable.getItems().remove(flightTable.getSelectionModel().getSelectedItem());
            }

        }
    }

    private int getFlightCount() {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();
        Statement selectStatement = null;
        String ctFlightQry = "select count(*) from flight;";

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
}

