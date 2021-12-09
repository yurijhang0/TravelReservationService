package customercontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ViewFlightController {

    @FXML
    private TableColumn<?, ?> airlineColumn, fromColumn, toColumn, totalSpentColumn, nameColumn, arrvTimeColumn, availableSeatsColumn, costPerSeatColumn, dateColumnn, deptTimeColumn;

    @FXML
    private Button backButton, filterButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TableView<?> viewFlightsTableView;

    @FXML
    void back(ActionEvent event) {

    }

    @FXML
    void filter(ActionEvent event) {

    }

}
