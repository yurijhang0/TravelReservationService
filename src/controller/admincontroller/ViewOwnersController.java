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
import table_structures.Owner;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ViewOwnersController implements Initializable {

    @FXML
    private TableColumn<Owner, String> avgPropRatingColumn, nameColumn, numPropColumn, ratingColumn;

    @FXML
    private Button backButton, viewButton;

    @FXML
    private TableView<Owner> customerTableView;

    @FXML
    private TextField ownerNameTextField;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // column names
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        numPropColumn.setCellValueFactory(new PropertyValueFactory<>("numProp"));
        avgPropRatingColumn.setCellValueFactory(new PropertyValueFactory<>("avgPropRating"));

        populateTable();
    }

    @FXML
    void view(ActionEvent event) {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String name = ownerNameTextField.getText();

        String selectStr = "select * from view_owners";

        // add to select statement based on filter fields used
        if (!name.isBlank()) {
            selectStr += String.format(" where owner_name like '%%%s%%'", name);
        }
        selectStr += ";";   // close select statement

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<Owner> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new Owner(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getString(3),
                                queryResult.getString(4))
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

        String name = ownerNameTextField.getText();

        String selectStr = "select * from view_owners;";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult =
                    statement.executeQuery(selectStr);

            // populate tableview with result of select statement
            final ObservableList<Owner> data = FXCollections.observableArrayList();
            while (queryResult.next()) {
                data.add(
                        new Owner(queryResult.getString(1),
                                queryResult.getString(2),
                                queryResult.getString(3),
                                queryResult.getString(4))
                );
            }

            // set tableview with data
            customerTableView.setItems(data);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
