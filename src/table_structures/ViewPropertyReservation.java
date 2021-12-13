package table_structures;

public class ViewPropertyReservation {
    private String name;
    private String start;
    private String end;
    private String phone;
    private String customer;
    private String cost;
    private String review;
    private String rating;

    public ViewPropertyReservation(String name, String start, String end, String phone, String customer, String cost,
                                   String review, String rating) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.phone = phone;
        this.customer = customer;
        this.cost = cost;
        this.review = review;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getPhone() {
        return phone;
    }

    public String getCustomer() {
        return customer;
    }

    public String getCost() {
        return cost;
    }

    public String getReview() {
        return review;
    }

    public String getRating() {
        return rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
