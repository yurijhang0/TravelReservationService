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
import table_structures.CancelPropertyReservation;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomerCancelProperty implements Initializable{

    @FXML
    private TableColumn<CancelPropertyReservation, String> addressColumn, ownerEmailColumn, propertyNameColumn, reservationDateColumn, selectColumn;

    @FXML
    private Button backButton, submitButton;

    @FXML
    private TableView<CancelPropertyReservation> ccpTableView;

    private String customerEmail, currentDate;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        customerEmail = LoginController.getEmailText();
        reservationDateColumn.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));
        propertyNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ownerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("ownerEmail"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String selectStr =
                String.format("select reserve.Start_Date, reserve.Property_Name, reserve.Owner_Email, view_properties.address " +
                        "from reserve " +
                        "left outer join view_properties on view_properties.Property_Name = reserve.Property_Name " +
                        "where reserve.customer = '%s' and reserve.Was_Cancelled = 0;", customerEmail);
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<CancelPropertyReservation> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new CancelPropertyReservation(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getString(3),
                                queryResult.getString(4))
                );
            }

            // set tableview with data
            ccpTableView.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        currentDate = date.toString();
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/customerfxml/customerHome.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void submit(ActionEvent event) {
        if (ccpTableView.getSelectionModel().getSelectedItem() != null) {

            String reservationDate = ccpTableView.getSelectionModel().getSelectedItem().getReservationDate();
            String name = ccpTableView.getSelectionModel().getSelectedItem().getName();
            String ownerEmail = ccpTableView.getSelectionModel().getSelectedItem().getOwnerEmail();
            String address = ccpTableView.getSelectionModel().getSelectedItem().getAddress();


            DBConnectionClass connectNow = new DBConnectionClass();
            Connection connectDB = connectNow.getConnection();

            int initReservationCount = getReservationCount();

            try {
                CallableStatement statementRemFlight =
                        connectDB.prepareCall("{call cancel_property_reservation(?,?,?,?)}");
                statementRemFlight.setString(1, name);
                statementRemFlight.setString(2, ownerEmail);
                statementRemFlight.setString(3, customerEmail);
                statementRemFlight.setDate(4, Date.valueOf(currentDate));

                statementRemFlight.execute();
                statementRemFlight.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            int newReservationCount = getReservationCount();

            // remove from table if removed in DB
            if (initReservationCount != newReservationCount) {
                // remove from tableview
                ccpTableView.getItems().remove(ccpTableView.getSelectionModel().getSelectedItem());
            }
        }
    }

    private int getReservationCount() {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();
        Statement selectStatement = null;
        String ctFlightQry = "select count(*) from reserve where Was_Cancelled = 0;";

        // number of flights
        int reservationCount = 0;

        try {
            // get initial row count before procedure call
            selectStatement = connectDB.createStatement();
            ResultSet queryResult = selectStatement.executeQuery(ctFlightQry);
            if (queryResult.next()) {
                reservationCount = queryResult.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return reservationCount;
    }
}