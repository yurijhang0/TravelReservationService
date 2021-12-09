package customercontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CustomerReserveProperty {

    @FXML
    private TableColumn<?, ?> amountSpentColumn, selectColumn, bookedPropertyNameColumn, capacityColumn, propertyNameColumn, numOfGuestsColumn, ownerEmailColumn;

    @FXML
    private Button backButton, reservePropertyButton;

    @FXML
    private TextField currentDateTextField, fromDateTextField, toDateTextField;

    @FXML
    private TableView<?> dataTableView, mainTableView;


    @FXML
    void back(ActionEvent event) {

    }

    @FXML
    void reserveProperty(ActionEvent event) {

    }

}
