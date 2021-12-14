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
import table_structures.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ViewCustomersController implements Initializable {

    @FXML
    private Button backButton, viewButton;

    @FXML
    private TextField customerNameTextField;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, String> isOwnerColumn, locationColumn, nameColumn, ratingColumn, totalSeatsPurchasedColumn;

    public void initialize(URL arg0, ResourceBundle arg1) {
        // column names
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        isOwnerColumn.setCellValueFactory(new PropertyValueFactory<>("isOwner"));
        totalSeatsPurchasedColumn.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));

        populateTable();
    }

    @FXML
    void view(ActionEvent event) {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String name = customerNameTextField.getText();

        String selectStr = "select * from view_customers";

        // add to select statement based on filter fields used
        if (!name.isBlank()) {
            selectStr += String.format(" where customer_name like '%s'", name);
        }
        selectStr += ";";   // close select statement

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<Customer> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new Customer(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getString(3),
                                queryResult.getString(4),
                                queryResult.getString(5))
                );
            }

            // set tableview with data
            customerTableView.setItems(data);

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

    void populateTable() {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String name = customerNameTextField.getText();

        String selectStr = "select * from view_customers;";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<Customer> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new Customer(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getString(3),
                                queryResult.getString(4),
                                queryResult.getString(5))
                );
            }

            // set tableview with data
            customerTableView.setItems(data);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
