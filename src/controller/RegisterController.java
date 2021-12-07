package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class RegisterController {

    @FXML
    private TextField cardNumTextField, cvvTextField, emailTextField, expDateTextField, fNameTextField, lNameTextField,
            passwordTextField, phoneNumTextField, locationTextField;

    @FXML
    private CheckBox customerCheckBox, ownerCheckBox;

    @FXML
    private Button registerButton, backButton;

    private String email, fname, lname, password, phoneNum, ccNum, cvv, expDate, location;

    @FXML
    void customerChecked(ActionEvent event) {
        cardNumTextField.setDisable(false);
        cvvTextField.setDisable(false);
        expDateTextField.setDisable(false);
        locationTextField.setDisable(false);
    }

    @FXML
    void ownerChecked(ActionEvent event) {
        cardNumTextField.setDisable(true);
        cvvTextField.setDisable(true);
        expDateTextField.setDisable(true);
        locationTextField.setDisable(true);
    }

    @FXML
    void registerAccount(ActionEvent event) throws IOException {

        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        try {
            // prepare call
            CallableStatement statementRegOwner = connectDB.prepareCall("{call register_owner(?,?,?,?,?)}");

            // set Strings and params for procedure call
            if (ownerCheckBox.isSelected()) { // owner
                email = emailTextField.getText();
                fname = fNameTextField.getText();
                lname = lNameTextField.getText();
                password = passwordTextField.getText();
                phoneNum = phoneNumTextField.getText();

                statementRegOwner.setString(1, email);
                statementRegOwner.setString(2, fname);
                statementRegOwner.setString(3, lname);
                statementRegOwner.setString(4, password);
                statementRegOwner.setString(5, phoneNum);

                statementRegOwner.execute();
                statementRegOwner.close();
                if (customerCheckBox.isSelected()) { // customer
                    // prepare call
                    CallableStatement statementRegCust = connectDB.prepareCall("{call register_customer(?,?,?,?,?,?,?,?,?)}");

                    ccNum = cardNumTextField.getText();
                    cvv = cvvTextField.getText();
                    expDate = expDateTextField.getText();
                    location = locationTextField.getText();

                    statementRegCust.setString(1, email);
                    statementRegCust.setString(2, fname);
                    statementRegCust.setString(3, lname);
                    statementRegCust.setString(4, password);
                    statementRegCust.setString(5, phoneNum);
                    statementRegCust.setString(6, ccNum);
                    statementRegCust.setString(7, cvv);
                    statementRegCust.setDate(8, Date.valueOf(expDate));
                    statementRegCust.setString(9, location);

                    statementRegCust.execute();
                    statementRegCust.close();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // load home page of customer or owner or both
        Parent root = null;
        if (ownerCheckBox.isSelected() && customerCheckBox.isSelected()) {
            root = FXMLLoader.load(getClass().getResource("ownercustomerhomescreen.fxml"));
        } else if (ownerCheckBox.isSelected()) {
            root = FXMLLoader.load(getClass().getResource("../fxml/ownerhomescreen.fxml"));
        } else if (customerCheckBox.isSelected()) {
            root = FXMLLoader.load(getClass().getResource("../fxml/customerhomescreen.fxml"));
        }
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        // load login screen
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/loginscreen.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
