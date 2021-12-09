package table_structures;

public class Airport {

    private String id;
    private String name;
    private String timeZone;
    private String totArrFlights;
    private String totDepFlights;
    private String avgDepFlightCost;

    public Airport(String id, String name, String timeZone, String totArrFlights,
                   String totDepFlights, String avgDepFlightCost) {
        this.id = id;
        this.name = name;
        this.timeZone = timeZone;
        this.totArrFlights = totArrFlights;
        this.totDepFlights = totDepFlights;
        this.avgDepFlightCost = avgDepFlightCost;

    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTimeZone() {
        return timeZone;
    }
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
    public String getTotArrFlights() {
        return totArrFlights;
    }
    public void setTotArrFlights(String totArrFlights) {
        this.totArrFlights = totArrFlights;
    }
    public String getTotDepFlights() {
        return totDepFlights;
    }
    public void setTotDepFlights(String totDepFlights) {
        this.totDepFlights = totDepFlights;
    }
    public String getAvgDepFlightCost() {
        return avgDepFlightCost;
    }
    public void setAvgDepFlightCost(String avgDepFlightCost) {
        this.avgDepFlightCost = avgDepFlightCost;
    }

}
