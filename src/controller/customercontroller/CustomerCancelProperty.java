package controller.customercontroller;

import controller.DBConnectionClass;
import controller.LoginController;
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
                statementRemFlight.setString(3, customerEmail);
                statementRemFlight.setString(1, name);
                statementRemFlight.setString(2, ownerEmail);
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
        String ctFlightQry = String.format("select count(*) from reserve where Customer = '%s' and Was_Cancelled = 0;",
                customerEmail);

        // NOT SURE IF THE ABOVE LINE IS CORRECT...


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
