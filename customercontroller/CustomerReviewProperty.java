package customercontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CustomerReviewProperty {

    @FXML
    private TableColumn<?, ?> addressColumn, ownerEmailColumn, propertyNameColumn, reservationDateColumn, selectColumn;

    @FXML
    private Button backButton, submitButton;

    @FXML
    private TableView<?> customerReviewTableView;

    @FXML
    private TextField reviewValueTextField;


    @FXML
    void back(ActionEvent event) {

    }

    @FXML
    void submit(ActionEvent event) {

    }

}
