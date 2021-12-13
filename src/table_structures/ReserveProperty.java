package table_structures;

public class ReserveProperty {
    private String name;
    private String ownerEmail;
    private String capacity;
    private String cost;

    public ReserveProperty(String name, String ownerEmail, String capacity, String cost) {
        this.name = name;
        this.ownerEmail = ownerEmail;
        this.capacity = capacity;
        this.cost = cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getCost() {
        return cost;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }
}
