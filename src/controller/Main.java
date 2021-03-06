package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/viewairlinescreen.fxml"));
        primaryStage.setTitle("Travel Reservation Service");
        primaryStage.setScene(new Scene(root, 600.0, 400.0));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
