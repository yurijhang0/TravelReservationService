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
import table_structures.Airport;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ViewAirportController implements Initializable {

    @FXML
    private TableColumn<Airport, String>  avgDepFlightCostColumn, idColumn, nameColumn, timeZoneColumn,
            totArrFlightsColumn, totDepFlightsColumn;

    @FXML
    private TableView<Airport> airportTable;

    @FXML
    private Button backButton, filterButton;

    @FXML
    private TextField idTextField;

    @FXML
    private ChoiceBox<String> timeZoneDropDown;

    private String timeZone;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // populate dropdown
        populateTimeZoneDropDown();
        setTimeZone(timeZoneDropDown.getValue());

        // column names
        avgDepFlightCostColumn.setCellValueFactory(new PropertyValueFactory<>("avgDepFlightCost"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        timeZoneColumn.setCellValueFactory(new PropertyValueFactory<>("timeZone"));
        totArrFlightsColumn.setCellValueFactory(new PropertyValueFactory<>("totArrFlights"));
        totDepFlightsColumn.setCellValueFactory(new PropertyValueFactory<>("totDepFlights"));

        populateTable();
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        // load admin home screen
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/adminfxml/adminhomescreen.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void populateTimeZoneDropDown() {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery("select distinct Time_Zone from airport;");

            // populate drop down with airline options
            while (queryResult.next()) {
                timeZoneDropDown.getItems().add(queryResult.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // setter for timeZone String
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void filter(ActionEvent actionEvent) {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        timeZone = timeZoneDropDown.getValue();
        String id = idTextField.getText();

        String selectStr = "select * from view_airports";

        // add to select statement based on filter fields used
        if (timeZone != null || !id.isBlank()) {
            selectStr += " where";
        }
        if (timeZone != null) {
            selectStr += String.format(" Time_Zone like '%s'", timeZone);
        }
        if (timeZone != null && !id.isBlank()) {
            selectStr += " and";
        }
        if (!id.isBlank()) {
            selectStr += String.format(" Airport_Id like '%s'", id);
        }
        selectStr += ";";   // close select statement

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<Airport> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new Airport(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getString(3),
                                queryResult.getString(4),
                                queryResult.getString(5),
                                queryResult.getString(6))
                );
            }

            // set tableview with data
            airportTable.setItems(data);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void populateTable() {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        timeZone = timeZoneDropDown.getValue();
        String id = idTextField.getText();

        String selectStr = "select * from view_airports;";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<Airport> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new Airport(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getString(3),
                                queryResult.getString(4),
                                queryResult.getString(5),
                                queryResult.getString(6))
                );
            }

            // set tableview with data
            airportTable.setItems(data);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
