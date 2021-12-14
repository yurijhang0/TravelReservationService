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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import table_structures.Property;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class OwnerRemovePropertyController implements Initializable {

    private String ownerEmail, currentDate;

    @FXML
    private TableColumn<Property, String> addressCol;

    @FXML
    private Button backButton, removePropertyButton;

    @FXML
    private TableColumn<Property, String> capacityCol;

    @FXML
    private TableColumn<Property, String> costCol;

    @FXML
    private TableColumn<Property, String> descriptionCol;

    @FXML
    private TableColumn<Property, String> propertyNameCol;

    @FXML
    private TableView<Property> propertyTable;

    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/ownerfxml/ownerhomescreen.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        currentDate = date.toString();

        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("descr"));
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        propertyNameCol.setCellValueFactory(new PropertyValueFactory<>("propertyName"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));


        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        ownerEmail = LoginController.getEmailText();
        String selectStr = String.format("select view_properties.property_name, description, view_properties.capacity, " +
                "cost_per_night, address from view_properties natural join property where Owner_Email like '%s'", ownerEmail);

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<Property> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new Property(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getInt(3),
                                queryResult.getInt(4),
                                queryResult.getString(5))
                );
            }

            // set tableview with data
            propertyTable.setItems(data);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void removeProperty(ActionEvent event) throws IOException {

        if (propertyTable.getSelectionModel().getSelectedItem() != null) {
            //get fields
            String propertyName = propertyTable.getSelectionModel().getSelectedItem().getPropertyName();
            String descr = propertyTable.getSelectionModel().getSelectedItem().getDescr();
            int capacity = propertyTable.getSelectionModel().getSelectedItem().getCapacity();
            int cost = propertyTable.getSelectionModel().getSelectedItem().getCost();
            String address = propertyTable.getSelectionModel().getSelectedItem().getAddress();

            //connect to DB
            DBConnectionClass connectNow = new DBConnectionClass();
            Connection connectDB = connectNow.getConnection();

            int initPropertyCount = getPropertyCount();
            try {
                // prepare call
                CallableStatement statementRemoveProperty = connectDB.prepareCall("{call remove_property(?,?,?)}");

                statementRemoveProperty.setString(1, propertyName);
                statementRemoveProperty.setString(2, ownerEmail);
                statementRemoveProperty.setDate(3, Date.valueOf(currentDate));

                // call procedure
                statementRemoveProperty.execute();
                statementRemoveProperty.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            // get query again after attempting to remove
            int newPropertyCount = getPropertyCount();

            // remove from table if removed in DB
            if (initPropertyCount != newPropertyCount) {
                // remove from tableview
                propertyTable.getItems().remove(propertyTable.getSelectionModel().getSelectedItem());
            }

        }

    }


    private int getPropertyCount() {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        Statement selectStatement = null;
        String ctPropertyQry = "select count(*) from Property;";

        // number of properties
        int propertyCount = 0;

        try {
            // get initial row count before procedure call
            selectStatement = connectDB.createStatement();
            ResultSet queryResult = selectStatement.executeQuery(ctPropertyQry);
            if (queryResult.next()) {
                propertyCount = queryResult.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return propertyCount;
    }
}

