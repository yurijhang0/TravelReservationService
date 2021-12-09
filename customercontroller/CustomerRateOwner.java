package customercontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CustomerRateOwner {

    @FXML
    private TableColumn<?, ?> addressColumn, ownerEmailColumn, propertyNameColumn,reservationDateColumn, ratingColumn;

    @FXML
    private Button backButton, submitButton;

    @FXML
    private TableView<?> rateOwnerTableView;

    @FXML
    void back(ActionEvent event) {

    }

    @FXML
    void submit(ActionEvent event) {

    }

}
