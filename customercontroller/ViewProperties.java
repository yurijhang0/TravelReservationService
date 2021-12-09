package customercontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ViewProperties {

    @FXML
    private TableColumn<?, ?> addressColumn, costColumn, descriptionColumn, nameColumn, avgRatingColumn, capacityColumn;

    @FXML
    private Button backButton, filter;

    @FXML
    private TextField capacityFromTextField, capacityToTextField;

    @FXML
    private TableView<?> viewPropertiesTableView;

    @FXML
    void back(ActionEvent event) {

    }

    @FXML
    void filterButton(ActionEvent event) {

    }

}
