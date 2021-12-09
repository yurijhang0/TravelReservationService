package customercontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ViewPropertyReservations {

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<?, ?> costColumn, startDateColumn, reviewColumnn, ratingColumn, propertyColumn, custPhoneNumColumn, customerEmailColumn, endDateColumn;

    @FXML
    private TextField ownerEmailTextField, propertyTextField;

    @FXML
    private TableView<?> propertyReservationTableView;

    @FXML
    void back(ActionEvent event) {

    }

}
