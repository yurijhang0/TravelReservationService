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
import table_structures.ReserveProperty;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Duration;
import java.util.ResourceBundle;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class CustomerReserveProperty implements Initializable {

    @FXML
    private TableColumn<ReserveProperty, String> costColumn, capacityColumn, propertyNameColumn, ownerEmailColumn;

    @FXML
    private Button backButton, reservePropertyButton;

    @FXML
    private TextField currentDateTextField, fromDateTextField, toDateTextField, amountSpent, bookedPropertyName, numGuests;

    @FXML
    private TableView<ReserveProperty> mainTableView;

    private String customerEmail, currentDate, numGuestsBooked;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        customerEmail = LoginController.getEmailText();
        propertyNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ownerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("ownerEmail"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String selectStr =
                String.format("select Property_Name, Owner_Email, Capacity, Cost from property");
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<ReserveProperty> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new ReserveProperty(queryResult.getString(1),
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

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        currentDate = date.toString();
        currentDateTextField.setText(currentDate);
        currentDateTextField.setDisable(true);
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/customerfxml/customerHome.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void reserveProperty(ActionEvent event) throws Exception{
        if (mainTableView.getSelectionModel().getSelectedItem() != null && !fromDateTextField.getText().isBlank()
                && !toDateTextField.getText().isBlank() && !numGuests.getText().isBlank()) {

            String name = mainTableView.getSelectionModel().getSelectedItem().getName();
            String ownerEmail = mainTableView.getSelectionModel().getSelectedItem().getOwnerEmail();
            String capacity = mainTableView.getSelectionModel().getSelectedItem().getCapacity();
            String cost = mainTableView.getSelectionModel().getSelectedItem().getCost();


            DBConnectionClass connectNow = new DBConnectionClass();
            Connection connectDB = connectNow.getConnection();

            numGuestsBooked = numGuests.getText();

            java.util.Date fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(fromDateTextField.getText());
            java.util.Date toDate = new SimpleDateFormat("yyyy-MM-dd").parse(toDateTextField.getText());

            long differenceMilli = Math.abs(toDate.getTime() - fromDate.getTime());
            int differenceDays = (int) TimeUnit.DAYS.convert(differenceMilli, TimeUnit.MILLISECONDS);

            try {
                CallableStatement statementRemFlight =
                        connectDB.prepareCall("{call reserve_property(?,?,?,?,?,?,?)}"); // FIX THIS
                statementRemFlight.setString(1, name);
                statementRemFlight.setString(2, ownerEmail);
                statementRemFlight.setString(3, customerEmail);
                statementRemFlight.setDate(4, Date.valueOf(fromDateTextField.getText()));
                statementRemFlight.setDate(5, Date.valueOf(toDateTextField.getText()));
                statementRemFlight.setInt(6, Integer.parseInt(numGuestsBooked));
                statementRemFlight.setDate(7, Date.valueOf(currentDate));

                statementRemFlight.execute();
                statementRemFlight.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            bookedPropertyName.setText(name);
            int totalCost = Integer.parseInt(numGuestsBooked) * (int) Double.parseDouble(cost) * differenceDays;
            amountSpent.setText(Integer.toString(totalCost));
        }
    }
}
