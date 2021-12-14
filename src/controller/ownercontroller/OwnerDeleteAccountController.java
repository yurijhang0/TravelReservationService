package controller.ownercontroller;

import controller.DBConnectionClass;
import controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class OwnerDeleteAccountController {

    private String ownerEmail;

    @FXML
    private Button backButton;

    @FXML
    private Button deleteAccountButton;

    @FXML
    private Button logOutButton;

    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/ownerfxml/ownerhomescreen.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    @FXML
    void deleteAccount(ActionEvent event) throws IOException {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        try {
            // prepare call
            CallableStatement statementDeleteOwnerAcc = connectDB.prepareCall("{call remove_owner(?)}");
            ownerEmail = LoginController.getEmailText();

            statementDeleteOwnerAcc.setString(1, ownerEmail);

            // call procedure
            statementDeleteOwnerAcc.execute();
            statementDeleteOwnerAcc.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/loginscreen.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/loginscreen.fxml"));
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
