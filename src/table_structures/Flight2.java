package table_structures;

import javafx.scene.control.cell.PropertyValueFactory;

public class Flight2 {

//    private String customerEmail;
    private String airlineName;
    private String flightNum;
    private String availableSeats;
    private String cost;

    public Flight2(String airlineName, String flightNum, String availableSeats, String cost) {
//        this.customerEmail = customerEmail;
        this.airlineName = airlineName;
        this.flightNum = flightNum;
        this.availableSeats = availableSeats;
        this.cost = cost;
    }

//    public void setCustomerEmail(String customerEmail) {
//        this.customerEmail = customerEmail;
//    }
//
//    public String getCustomerEmail() {
//        return customerEmail;
//    }


    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(String availableSeats) {
        this.availableSeats = availableSeats;
    }
}
