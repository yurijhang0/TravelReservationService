package controller.ownercontroller;

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
import table_structures.OwnerRateCustomer;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class OwnerRateCustomerController implements Initializable {

    private String ownerEmail, currentDate;

    @FXML
    private TableColumn<OwnerRateCustomer, String> addressCol;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<OwnerRateCustomer, String> customerEmailCol;

    @FXML
    private TableColumn<OwnerRateCustomer, String> dateCol;

    @FXML
    private TableColumn<OwnerRateCustomer, String> propertyNameCol;

    @FXML
    private TableView<OwnerRateCustomer> rateTable;

    @FXML
    private TableColumn<OwnerRateCustomer, String> ratingCol;

    @FXML
    private Button submitButton;

    @FXML
    private TextField ratingTextField;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        ownerEmail = LoginController.getEmailText();
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        currentDate = date.toString();

        //column names
        dateCol.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));
        customerEmailCol.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
        propertyNameCol.setCellValueFactory(new PropertyValueFactory<>("propertyName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));

        populateRatingTable();

    }

    void populateRatingTable() {

        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String selectStr =
                String.format("select Start_Date, reserve.Customer, reserve.Property_Name, address, score \n" +
                        "from (reserve left outer join owners_rate_customers on reserve.Owner_Email like owners_rate_customers.Owner_Email), view_properties \n" +
                        "where reserve.Property_Name = view_properties.property_name and reserve.Owner_Email like '%s';", ownerEmail);
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<OwnerRateCustomer> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new OwnerRateCustomer(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getString(3),
                                queryResult.getString(4),
                                queryResult.getString(5))
                );
            }

            // set tableview with data
            rateTable.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/ownerfxml/ownerhomescreen.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
        if (rateTable.getSelectionModel().getSelectedItem() != null
                && !ratingTextField.getText().isBlank()) {

            String reservationDate = rateTable.getSelectionModel().getSelectedItem().getReservationDate();
            String name = rateTable.getSelectionModel().getSelectedItem().getPropertyName();
            String customerEmail = rateTable.getSelectionModel().getSelectedItem().getCustomerEmail();
            String address = rateTable.getSelectionModel().getSelectedItem().getAddress();

            System.out.println(ownerEmail);
            System.out.println(customerEmail);
            System.out.println(currentDate);


            DBConnectionClass connectNow = new DBConnectionClass();
            Connection connectDB = connectNow.getConnection();

            String rating = ratingTextField.getText();
            System.out.println(rating);

            try {
                CallableStatement statementRateCustomer =
                        connectDB.prepareCall("{call owner_rates_customer(?,?,?,?)}");
                statementRateCustomer.setString(1, ownerEmail);
                statementRateCustomer.setString(2, customerEmail);
                statementRateCustomer.setInt(3, Integer.parseInt(rating));
                statementRateCustomer.setDate(4, Date.valueOf(currentDate));

                statementRateCustomer.execute();
                statementRateCustomer.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        populateRatingTable();
    }

}

