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
    void ownerCustomerCheck(ActionEvent event) {
        registerButton.setDisable(false);
        if (ownerCheckBox.isSelected()) {
            fNameTextField.setDisable(false);
            lNameTextField.setDisable(false);
            emailTextField.setDisable(false);
            passwordTextField.setDisable(false);
            phoneNumTextField.setDisable(false);
        } else {
            fNameTextField.setDisable(true);
            lNameTextField.setDisable(true);
            emailTextField.setDisable(true);
            passwordTextField.setDisable(true);
            phoneNumTextField.setDisable(true);
        }
        if (customerCheckBox.isSelected()) {
            cardNumTextField.setDisable(false);
            cvvTextField.setDisable(false);
            expDateTextField.setDisable(false);
            locationTextField.setDisable(false);
        } else {
            cardNumTextField.setDisable(true);
            cvvTextField.setDisable(true);
            expDateTextField.setDisable(true);
            locationTextField.setDisable(true);
        }
        if (!ownerCheckBox.isSelected() && customerCheckBox.isSelected()) {
            fNameTextField.setDisable(false);
            lNameTextField.setDisable(false);
            emailTextField.setDisable(false);
            passwordTextField.setDisable(false);
            phoneNumTextField.setDisable(false);
            cardNumTextField.setDisable(false);
            cvvTextField.setDisable(false);
            expDateTextField.setDisable(false);
            locationTextField.setDisable(false);
        }
        if (!ownerCheckBox.isSelected() && !customerCheckBox.isSelected()) {
            registerButton.setDisable(true);
        }
    }

    @FXML
    void registerAccount(ActionEvent event) throws IOException {

        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        // booleans for calling other screens
        boolean owner = false;
        boolean customer = false;

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

                owner = !email.isBlank() && !fname.isBlank() && !lname.isBlank() && !password.isBlank() &&
                        !phoneNum.isBlank();
            }
            if (customerCheckBox.isSelected()) { // customer
                // prepare call
                CallableStatement statementRegCust = connectDB.prepareCall("{call register_customer(?,?,?,?,?,?,?,?,?)}");
                email = emailTextField.getText();
                fname = fNameTextField.getText();
                lname = lNameTextField.getText();
                password = passwordTextField.getText();
                phoneNum = phoneNumTextField.getText();
                ccNum = cardNumTextField.getText();
                System.out.println(ccNum);
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
                customer = owner && !ccNum.isBlank() && !cvv.isBlank() && !expDate.isBlank();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // load home page of customer or owner or both
        Parent root = null;
        if (ownerCheckBox.isSelected() && customerCheckBox.isSelected()) {
            if (customer) {
                root = FXMLLoader.load(getClass().getResource("../fxml/ownercustomerhomescreen.fxml"));
                Stage stage = (Stage) registerButton.getScene().getWindow();
                stage.setScene(new Scene(root));
            }
        } else if (ownerCheckBox.isSelected()) {
            if (owner) {
                root = FXMLLoader.load(getClass().getResource("../fxml/ownerhomescreen.fxml"));
                Stage stage = (Stage) registerButton.getScene().getWindow();
                stage.setScene(new Scene(root));
            }
        } else if (customerCheckBox.isSelected()) {
            if (customer) {
                root = FXMLLoader.load(getClass().getResource("../fxml/customerhomescreen.fxml"));
                Stage stage = (Stage) registerButton.getScene().getWindow();
                stage.setScene(new Scene(root));
            }
        }
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        // load login screen
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/loginscreen.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
