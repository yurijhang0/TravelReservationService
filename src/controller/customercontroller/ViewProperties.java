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
import table_structures.ViewProperty;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ViewProperties implements Initializable{

    @FXML
    private TableColumn<ViewProperty, String> addressColumn, costColumn, descriptionColumn, nameColumn, avgRatingColumn, capacityColumn;

    @FXML
    private Button backButton, filter;

    @FXML
    private TextField capacityFromTextField, capacityToTextField;

    @FXML
    private TableView<ViewProperty> viewPropertiesTableView;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        avgRatingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/customerfxml/customerHome.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void filterButton(ActionEvent event) {
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String lowerBound = capacityFromTextField.getText();
        String upperBound = capacityToTextField.getText();


        String selectStr =
                String.format("select property_name, average_rating_score, description, address, capacity," +
                        "cost_per_night from view_properties");

        // add to select statement based on filter fields used
        if (!lowerBound.isBlank() && !upperBound.isBlank()) {
            selectStr += String.format(" where capacity >= '%s' and capacity <= '%s'", lowerBound, upperBound);
        }
        selectStr += ";";   // close select statement

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<ViewProperty> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new ViewProperty(queryResult.getString(1),
                                queryResult.getString(4),
                                queryResult.getString(3),
                                queryResult.getString(2),
                                queryResult.getString(5),
                                queryResult.getString(6))
                );
            }

            // set tableview with data
            viewPropertiesTableView.setItems(data);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
