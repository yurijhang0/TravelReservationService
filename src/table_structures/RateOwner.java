package table_structures;

public class RateOwner {
    private String date;
    private String ownerEmail;
    private String name;
    private String address;
    private String rating;

    public RateOwner(String date, String ownerEmail, String name, String address, String rating) {
        this.date = date;
        this.ownerEmail = ownerEmail;
        this.name = name;
        this.address = address;
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
