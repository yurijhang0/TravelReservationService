package controller.ownercontroller;

import controller.DBConnectionClass;
import controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class OwnerAddPropertyController implements Initializable {
    private String ownerEmail, propertyName,descr, capacity, cost, street, city, state, zip, nearestAirport, distToAirport;
    private int costInt, distInt, capacityInt;

    @FXML
    private TextField capacityTextField, cityTextField, descriptionTextField, distToAirportTextField, nameTextField,
            nearAirportTextField, streetTextField,zipTextField, costTextField;

    @FXML
    private ChoiceBox<String> stateChoiceBox;
    ;

    @FXML
    private Button addButton, cancelButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // populate dropdown
        setStateChoiceBox();

    }

    //populating with a list of state abbreviations
    public void setStateChoiceBox() {
        stateChoiceBox.getItems().addAll("AL", "AK", "AZ", "AR","CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL",
                "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT","NE", "NV", "NH", "NJ", "NM", "NY",
                "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");
    }



    @FXML
    void addProperty(ActionEvent event) throws IOException {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        try {
            // prepare call
            CallableStatement statementAddProperty = connectDB.prepareCall("{call add_property(?,?,?,?,?,?,?,?,?,?,?)}");
            propertyName = nameTextField.getText();
            descr = descriptionTextField.getText();
            street = streetTextField.getText();
            city = cityTextField.getText();
            state = stateChoiceBox.getValue();
            zip = zipTextField.getText();
            nearestAirport = nearAirportTextField.getText();
            distToAirport = distToAirportTextField.getText(); //NEED TO CAST TO INTEGER
            cost = costTextField.getText(); //NEED TO CAST TO INTEGER
            capacity = capacityTextField.getText(); //NEED TO CAST TO INTEGER
            ownerEmail = LoginController.getEmailText();

            capacityInt = Integer.parseInt(capacity);
            costInt = Integer.parseInt(cost);
            distInt = Integer.parseInt(distToAirport);



            statementAddProperty.setString(1, propertyName);
            statementAddProperty.setString(2, ownerEmail);
            statementAddProperty.setString(3, descr);
            statementAddProperty.setInt(4, capacityInt);
            statementAddProperty.setInt(5, costInt);
            statementAddProperty.setString(6, street);
            statementAddProperty.setString(7, city);
            statementAddProperty.setString(8, state);
            statementAddProperty.setString(9, zip);
            statementAddProperty.setString(10, nearestAirport);
            statementAddProperty.setInt(11, distInt);

            // call procedure
            statementAddProperty.execute();
            statementAddProperty.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void cancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/ownerfxml/ownerhomescreen.fxml"));
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.setScene(new Scene(root));

    }
}
