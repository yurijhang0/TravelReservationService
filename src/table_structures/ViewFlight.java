package table_structures;

public class ViewFlight {
    private String id;
    private String airline;
    private String from;
    private String to;
    private String depart;
    private String arrive;
    private String date;
    private String seats;
    private String cost;
    private String total;

    public ViewFlight(String id, String airline, String from, String to, String depart, String arrive, String date, String seats, String cost, String total) {
        this.id = id;
        this.airline = airline;
        this.from = from;
        this.to = to;
        this.depart = depart;
        this.arrive = arrive;
        this.date = date;
        this.seats = seats;
        this.cost = cost;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}