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
import table_structures.RateOwner;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Duration;
import java.util.ResourceBundle;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class CustomerRateOwner implements Initializable{

    @FXML
    private TableColumn<RateOwner, String> addressColumn, ownerEmailColumn, propertyNameColumn,reservationDateColumn, ratingColumn;

    @FXML
    private Button backButton, submitButton;

    @FXML
    private TableView<RateOwner> rateOwnerTableView;

    @FXML
    private TextField ratingTextField;

    private String customerEmail, currentDate;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        customerEmail = LoginController.getEmailText();
        reservationDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        ownerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("ownerEmail"));
        propertyNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        populateRatingTable();

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        currentDate = date.toString();
    }

    void populateRatingTable() {

        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String selectStr =
                String.format("select Start_Date, reserve.Owner_Email, reserve.Property_Name, address, score " +
                        "from (reserve left outer join customers_rate_owners on reserve.Customer like " +
                        "customers_rate_owners.Customer), view_properties " +
                        "where reserve.Property_Name = view_properties.property_name and " +
                        "reserve.Customer like '%s';", customerEmail);
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<RateOwner> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new RateOwner(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getString(3),
                                queryResult.getString(4),
                                queryResult.getString(5))
                );
            }

            // set tableview with data
            rateOwnerTableView.setItems(data);
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
    void submit(ActionEvent event) {
        if (rateOwnerTableView.getSelectionModel().getSelectedItem() != null
                && !ratingTextField.getText().isBlank()) {

            String reservationDate = rateOwnerTableView.getSelectionModel().getSelectedItem().getDate();
            String name = rateOwnerTableView.getSelectionModel().getSelectedItem().getName();
            String ownerEmail = rateOwnerTableView.getSelectionModel().getSelectedItem().getOwnerEmail();
            String address = rateOwnerTableView.getSelectionModel().getSelectedItem().getAddress();


            DBConnectionClass connectNow = new DBConnectionClass();
            Connection connectDB = connectNow.getConnection();

            String rating = ratingTextField.getText();

            try {
                CallableStatement statementRemFlight =
                connectDB.prepareCall("{call customer_rates_owner(?,?,?,?)}");
                statementRemFlight.setString(1, customerEmail);
                statementRemFlight.setString(2, ownerEmail);
                statementRemFlight.setInt(3, Integer.parseInt(rating));
                statementRemFlight.setDate(4, Date.valueOf(currentDate));

                statementRemFlight.execute();
                statementRemFlight.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        populateRatingTable();
    }
}
