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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import table_structures.Flight3;
import table_structures.ViewFlight;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ViewFlightController implements Initializable {

    @FXML
    private TableColumn<ViewFlight, String> airlineColumn, fromColumn, toColumn, totalSpentColumn, nameColumn,
            arrvTimeColumn, availableSeatsColumn, costPerSeatColumn, dateColumnn, deptTimeColumn;

    @FXML
    private Button backButton, filterButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TableView<ViewFlight> viewFlightsTableView;

//    private String customerEmail;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        airlineColumn.setCellValueFactory(new PropertyValueFactory<>("airline"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        deptTimeColumn.setCellValueFactory(new PropertyValueFactory<>("depart"));
        arrvTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrive"));
        availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));
        costPerSeatColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        totalSpentColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String selectStr =
                String.format(
                        "select view_flight.flight_id, view_flight.airline, flight.From_Airport, flight.To_Airport, " +
                                "flight.Departure_Time, flight.Arrival_Time, view_flight.flight_date, " +
                                "view_flight.num_empty_seats, view_flight.seat_cost, view_flight.total_spent " +
                                "from view_flight " +
                                "left outer join flight on flight.Flight_Num = view_flight.flight_id and " +
                                "flight.Airline_Name = view_flight.airline;");
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<ViewFlight> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new ViewFlight(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getString(3),
                                queryResult.getString(4),
                                queryResult.getString(5),
                                queryResult.getString(6),
                                queryResult.getString(7),
                                queryResult.getString(8),
                                queryResult.getString(9),
                                queryResult.getString(10))
                );
            }

            // set tableview with data
            viewFlightsTableView.setItems(data);
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
    void filter(ActionEvent event) {
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String seats = nameTextField.getText();

        String selectStr =
                String.format(
                        "select view_flight.flight_id, view_flight.airline, flight.From_Airport, flight.To_Airport, " +
                                "flight.Departure_Time, flight.Arrival_Time, view_flight.flight_date, " +
                                "view_flight.num_empty_seats, view_flight.seat_cost, view_flight.total_spent " +
                                "from view_flight " +
                                "left outer join flight on flight.Flight_Num = view_flight.flight_id and " +
                                "flight.Airline_Name = view_flight.airline ");
        if (!seats.isBlank()) {
            selectStr += String.format("where view_flight.num_empty_seats >= %d", Integer.parseInt(seats));
        }

        selectStr += ";";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<ViewFlight> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new ViewFlight(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getString(3),
                                queryResult.getString(4),
                                queryResult.getString(5),
                                queryResult.getString(6),
                                queryResult.getString(7),
                                queryResult.getString(8),
                                queryResult.getString(9),
                                queryResult.getString(10))
                );
            }

            // set tableview with data
            viewFlightsTableView.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}