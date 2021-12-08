package table_structures;

public class Airport {

    private String airlineName;
    private String airlineNum;
    private String flightDate;
    private String timeZone;

    public Airport(String airlineName, String airlineNum, String flightDate, String timeZone) {
        this.airlineName = airlineName;
        this.airlineNum = airlineNum;
        this.flightDate = flightDate;
        this.timeZone = timeZone;
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
    public void setAirlineNum(String airlineNum) {
        this.airlineNum = airlineNum;
    }
    public String getFlightDate() {
        return flightDate;
    }
    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }
    public String getTimeZone() {
        return timeZone;
    }
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
