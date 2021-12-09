package customercontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class BookFlightController {

    @FXML
    private TableColumn<?, ?> BookedFlightNumColumn, seatsToBookColumn, selectColumn, airlineColumnn, amountSpentColumn, availableSeatsColumn, flightNumColumn;

    @FXML
    private Button backButton, bookFlightButton;

    @FXML
    private TableView<?> mainTableView, dataTableView;

    @FXML
    void back(ActionEvent event) {

    }

    @FXML
    void bookFlight(ActionEvent event) {

    }

}
