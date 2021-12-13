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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import table_structures.Airline;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ViewAirlineController implements Initializable {

    @FXML
    private TextField airlineNameTextField;

    @FXML
    private TableView<Airline> airlineTableView;

    @FXML
    private Button backButton, viewButton;

    @FXML
    private TableColumn<Airline, String> minFlightCostColumn, nameColumn, ratingColumn, totalFlightsColumn;

    public void initialize(URL arg0, ResourceBundle arg1) {
        // column names
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("airlineName"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        totalFlightsColumn.setCellValueFactory(new PropertyValueFactory<>("totalFlight"));
        minFlightCostColumn.setCellValueFactory(new PropertyValueFactory<>("minFlight"));
    }

    @FXML
    void view(ActionEvent event) {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String name = airlineNameTextField.getText();

        String selectStr = "select * from view_airlines";

        // add to select statement based on filter fields used
        if (!name.isBlank()) {
            selectStr += String.format(" where airline_name like '%s'", name);
        }
        selectStr += ";";   // close select statement

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<Airline> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new Airline(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getString(3),
                                queryResult.getString(4))
                );
            }

            // set tableview with data
            airlineTableView.setItems(data);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        // load admin home screen
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/adminfxml/adminhomescreen.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
