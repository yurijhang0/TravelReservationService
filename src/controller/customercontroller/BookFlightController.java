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

import table_structures.Flight;
import table_structures.Flight2;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class BookFlightController implements Initializable {

    @FXML
    private TableColumn<Flight2, String> airlineColumn, availableSeatsColumn, flightNumColumn, costColumn;

    @FXML
    private Button backButton, bookFlightButton;

    @FXML
    private TableView<Flight2> mainTableView;

    @FXML
    private TextField numSeatsBookedField, BookedFlightNum, amountSpent;

    private String numSeatsBooked, customerEmail, currentDate;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        customerEmail = LoginController.getEmailText();
        airlineColumn.setCellValueFactory(new PropertyValueFactory<>("airlineName"));
        flightNumColumn.setCellValueFactory(new PropertyValueFactory<>("flightNum"));
        availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        currentDate = date.toString();

        String selectStr =
                String.format("select airline, flight_id, num_empty_seats, seat_cost from view_flight;");
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<Flight2> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new Flight2(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getString(3),
                                queryResult.getString(4))
                );
            }

            // set tableview with data
            mainTableView.setItems(data);
        } catch (Exception e) {
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
    void bookFlight(ActionEvent event) {
        if (mainTableView.getSelectionModel().getSelectedItem() != null && !numSeatsBookedField.getText().isBlank()) {

            String airlineName = mainTableView.getSelectionModel().getSelectedItem().getAirlineName();
            String flightNum = mainTableView.getSelectionModel().getSelectedItem().getFlightNum();
            String availableSeats = mainTableView.getSelectionModel().getSelectedItem().getAvailableSeats();
            String cost = mainTableView.getSelectionModel().getSelectedItem().getCost();


            DBConnectionClass connectNow = new DBConnectionClass();
            Connection connectDB = connectNow.getConnection();

            numSeatsBooked = numSeatsBookedField.getText();
            BookedFlightNum.setText(flightNum);
            int totalCost = Integer.parseInt(numSeatsBooked) * (int) Double.parseDouble(cost);
            amountSpent.setText(Integer.toString(totalCost));

            try {
                CallableStatement statementRemFlight =
                        connectDB.prepareCall("{call book_flight(?,?,?,?,?)}");
                statementRemFlight.setString(1, customerEmail);
                statementRemFlight.setString(2, flightNum);
                statementRemFlight.setString(3, airlineName);
                statementRemFlight.setInt(4, Integer.parseInt(numSeatsBooked));
                statementRemFlight.setDate(5, Date.valueOf(currentDate));

                statementRemFlight.execute();
                statementRemFlight.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}