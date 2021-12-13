package table_structures;

public class Flight3 {
    private String airlineName;
    private String airlineNum;
    private String flightDate;

    public Flight3(String airlineName, String airlineNum, String flightDate) {
        this.airlineName = airlineName;
        this.airlineNum = airlineNum;
        this.flightDate = flightDate;
    }

    public String getAirlineName() {
        return airlineName;
    }
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
    public String getAirlineNum() {
        return airlineNum;
    }
    public void setAirlineNum(String airlineName) {
        this.airlineNum = airlineNum;
    }
    public String getFlightDate() {
        return flightDate;
    }
    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

}
