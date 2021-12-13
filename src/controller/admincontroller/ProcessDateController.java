package controller.admincontroller;

import controller.DBConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class ProcessDateController {

    @FXML
    private TextField processDateTextField;

    @FXML
    private Button setDateButton, backButton;

    private String date;
    @FXML
    void setDate(ActionEvent event) {
        // connect to DB
        DBConnectionClass connectNow = new DBConnectionClass();
        Connection connectDB = connectNow.getConnection();

        try {
            // prepare call
            CallableStatement statementProcessDate = connectDB.prepareCall("{call process_date(?)}");

            date = processDateTextField.getText();

            statementProcessDate.setDate(1, Date.valueOf(date));

            // call procedure
            statementProcessDate.execute();
            statementProcessDate.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        // load admin home screen
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/adminfxml/adminhomescreen.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
