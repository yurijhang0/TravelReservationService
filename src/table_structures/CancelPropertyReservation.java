package table_structures;

public class CancelPropertyReservation {
    private String reservationDate;
    private String name;
    private String ownerEmail;
    private String address;

    public CancelPropertyReservation(String reservationDate, String name, String ownerEmail, String address) {
        this.reservationDate = reservationDate;
        this.name = name;
        this.ownerEmail = ownerEmail;
        this.address = address;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getReservationDate() {
        return reservationDate;
    }
}

