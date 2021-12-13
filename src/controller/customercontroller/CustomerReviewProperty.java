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
import java.time.Duration;
import java.util.ResourceBundle;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class CustomerReviewProperty implements Initializable {

    @FXML
    private TableColumn<CancelPropertyReservation, String> addressColumn, ownerEmailColumn, propertyNameColumn, reservationDateColumn, selectColumn;

    @FXML
    private Button backButton, submitButton;

    @FXML
    private TableView<CancelPropertyReservation> customerReviewTableView;

    @FXML
    private TextField reviewValueTextField, reviewTextField;

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
                String.format("select reserve.Start_Date, reserve.Property_Name, reserve.Owner_Email, " +
                        "view_properties.address from reserve, view_properties where " +
                        "reserve.Property_Name = view_properties.property_name and " +
                        "reserve.Customer = '%s';", customerEmail);
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
            customerReviewTableView.setItems(data);
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
        if (customerReviewTableView.getSelectionModel().getSelectedItem() != null
                && !reviewValueTextField.getText().isBlank() && !reviewTextField.getText().isBlank()) {

            String reservationDate = customerReviewTableView.getSelectionModel().getSelectedItem().getReservationDate();
            String name = customerReviewTableView.getSelectionModel().getSelectedItem().getName();
            String ownerEmail = customerReviewTableView.getSelectionModel().getSelectedItem().getOwnerEmail();
            String address = customerReviewTableView.getSelectionModel().getSelectedItem().getAddress();


            DBConnectionClass connectNow = new DBConnectionClass();
            Connection connectDB = connectNow.getConnection();

            String rating = reviewValueTextField.getText();

            try {
                CallableStatement statementRemFlight =
                        connectDB.prepareCall("{call customer_review_property(?,?,?,?,?,?)}");
                statementRemFlight.setString(1, name);
                statementRemFlight.setString(2, ownerEmail);
                statementRemFlight.setString(3, customerEmail);
                statementRemFlight.setString(4, reviewTextField.getText());
                statementRemFlight.setInt(5, Integer.parseInt(rating));
                statementRemFlight.setDate(6, Date.valueOf(currentDate));

                statementRemFlight.execute();
                statementRemFlight.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
