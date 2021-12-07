package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class ProcessDateController {

    @FXML
    private TextField processDateTextField;

    @FXML
    private Button setDateButton;

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
}
