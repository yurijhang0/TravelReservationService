package table_structures;

public class OwnerRateCustomer {

    private String reservationDate;
    private String customerEmail;
    private String propertyName;
    private String address;
    private String rating;

    public OwnerRateCustomer(String reservationDate, String customerEmail, String propertyName, String address, String rating) {
        this.reservationDate = reservationDate;
        this.customerEmail = customerEmail;
        this.propertyName = propertyName;
        this.address = address;
        this.rating = rating;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
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
