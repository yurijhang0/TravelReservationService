package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {

    @FXML
    private Button loginButton, registerButton;

    @FXML
    private TextField passwordTextField, emailTextField;

    private static String emailText;
    private String passwordText;

    @FXML
    public void loginAccount(ActionEvent event) throws IOException {

        // get text inputs
        emailText = emailTextField.getText();
        passwordText = passwordTextField.getText();

        // validate login
        if (!emailText.isBlank() && !passwordText.isBlank()) {
            if (validateAccount()) {
                Parent root = null;
                if (validateAdmin()) {
                    root = FXMLLoader.load(getClass().getResource("../fxml/adminfxml/adminhomescreen.fxml"));
                } else if (validateCustomer() && validateOwner()) {
                    root = FXMLLoader.load(getClass().getResource("../fxml/ownercustomerhomescreen.fxml"));
                }
                else if (validateCustomer()) {
                    root = FXMLLoader.load(getClass().getResource("../fxml/customerfxml/customerHome.fxml"));
                } else if (validateOwner()) {
                    root = FXMLLoader.load(getClass().getResource("../fxml/ownerfxml/ownerhomescreen.fxml"));
                }
                if (root != null) {
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.setScene(new Scene(root));
                }
            }
        }
    }

    @FXML
    public void registerNewAccount(ActionEvent event) throws IOException {

        // get text inputs
        emailText = emailTextField.getText();
        passwordText = passwordTextField.getText();

        // load register screen
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/registerscreen.fxml"));
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    // validate account
    public boolean validateAccount() {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = String.format("select count(*) from accounts where Email like '%s' and Pass like '%s';",
                emailText, passwordText);

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            if (queryResult.next()) {
                // 1 unique user
                return queryResult.getInt(1) == 1;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // validate admin
    public boolean validateAdmin() {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = String.format("select count(*) from admins where Email like '%s';", emailText);

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {   // 1 unique user
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // validate customer
    public boolean validateCustomer() {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = String.format("select count(*) from customer where Email like '%s';", emailText);

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {   // 1 unique user
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // validate owner
    public boolean validateOwner() {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = String.format("select count(*) from owners where Email like '%s';", emailText);

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {   // 1 unique user
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static String getEmailText() {
        return emailText;
    }
}
