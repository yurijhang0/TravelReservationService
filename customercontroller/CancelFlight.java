package customercontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class CancelFlight {

    @FXML
    private ComboBox<?> airlineComboBox;

    @FXML
    private Button backButton, cancelFlightButton, filterButton, resetButton;

    @FXML
    private TextField currentDateTextField, flightNumberTextField;

    @FXML
    private TableColumn<?, ?> dateColumn, numberColumn, selectColumn, airlineColumn;


    @FXML
    void back(ActionEvent event) {

    }

    @FXML
    void cancelFlight(ActionEvent event) {

    }

    @FXML
    void cancelFlightTableView(ActionEvent event) {

    }

    @FXML
    void filter(ActionEvent event) {

    }

    @FXML
    void reset(ActionEvent event) {

    }

}
