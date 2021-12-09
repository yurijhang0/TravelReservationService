package table_structures;

public class Airline {

    private String airlineName;
    private String rating;
    private String totalFlight;
    private String minFlight;

    public Airline(String airlineName, String rating, String totalFlight, String minFlight) {
        this.airlineName = airlineName;
        this.rating = rating;
        this.totalFlight = totalFlight;
        this.minFlight = minFlight;
    }

    public String getAirlineName() {
        return airlineName;
    }
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getTotalFlight() {
        return totalFlight;
    }
    public void setTotalFlight(String totalFlight) {
        this.totalFlight = totalFlight;
    }
    public String getMinFlight() {
        return minFlight;
    }
    public void setMinFlight(String minFlight) {
        this.minFlight = minFlight;
    }
}
