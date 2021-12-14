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
import table_structures.ViewPropertyReservation;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Duration;
import java.util.ResourceBundle;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class ViewPropertyReservations implements Initializable{

    @FXML
    private Button backButton, SearchButton;

    @FXML
    private TableColumn<ViewPropertyReservation, String> costColumn, startDateColumn, reviewColumnn, ratingColumn, propertyColumn, custPhoneNumColumn, customerEmailColumn, endDateColumn;

    @FXML
    private TextField ownerEmailTextField, propertyTextField;

    @FXML
    private TableView<ViewPropertyReservation> propertyReservationTableView;

    private String customerEmail, currentDate;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        customerEmail = LoginController.getEmailText();
        propertyColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        custPhoneNumColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        reviewColumnn.setCellValueFactory(new PropertyValueFactory<>("review"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));


        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        currentDate = date.toString();

        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String owner = ownerEmailTextField.getText();
        String property = propertyTextField.getText();

        String selectStr = "select review.Property_Name, reserve.Start_Date, reserve.End_Date, clients.Phone_Number, " +
                        "clients.Email, property.Cost, review.Content, review.Score from review  " +
                        "left outer join clients on clients.Email = review.Customer  " +
                        "left outer join reserve on reserve.Customer = review.Customer  " +
                        "left outer join property on property.Property_Name = review.Property_Name;";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<ViewPropertyReservation> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new ViewPropertyReservation(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getString(3),
                                queryResult.getString(4),
                                queryResult.getString(5),
                                queryResult.getString(6),
                                queryResult.getString(7),
                                queryResult.getString(8))
                );
            }

            // set tableview with data
            propertyReservationTableView.setItems(data);
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
    void search(ActionEvent event) {
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String owner = ownerEmailTextField.getText();
        String property = propertyTextField.getText();

        String selectStr =
                String.format("select review.Property_Name, reserve.Start_Date, reserve.End_Date, clients.Phone_Number, " +
                                "clients.Email, property.Cost, review.Content, review.Score from review  " +
                                "left outer join clients on clients.Email = review.Customer  " +
                                "left outer join reserve on reserve.Customer = review.Customer  " +
                                "left outer join property on property.Property_Name = review.Property_Name ");

        if (!owner.isBlank()) {
            selectStr += String.format("where '%s' = property.Owner_Email;", owner);
        }
        else if (!property.isBlank()) {
            selectStr += String.format("where '%s' = property.Property_Name;", property);
        }
        else if (!owner.isBlank() && !property.isBlank()) {
            selectStr += String.format("where '%s' = property.Owner_Email and '%s' = property.Property_Name;",
                    owner, property);
        }


        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<ViewPropertyReservation> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new ViewPropertyReservation(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getString(3),
                                queryResult.getString(4),
                                queryResult.getString(5),
                                queryResult.getString(6),
                                queryResult.getString(7),
                                queryResult.getString(8))
                );
            }

            // set tableview with data
            propertyReservationTableView.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
