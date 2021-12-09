package table_structures;

public class Customer {

    private String customerName;
    private String rating;
    private String location;
    private String isOwner;
    private String totalSeats;

    public Customer(String customerName, String rating, String location, String isOwner, String totalSeats) {
        this.customerName = customerName;
        this.rating = rating;
        this.location = location;
        this.isOwner = isOwner;
        this.totalSeats = totalSeats;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getIsOwner() {
        return isOwner;
    }
    public void setIsOwner(String isOwner) {

    }
    public String getTotalSeats() {
        return totalSeats;
    }
    public void setTotalSeats(String totalSeats) {
        this.totalSeats = totalSeats;
    }

}
