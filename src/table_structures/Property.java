package table_structures;

public class Property {
    private String propertyName;
    private String descr;
    private int capacity;
    private int cost;
    private String address;

    public Property(String propertyName, String descr, int capacity, int cost, String address) {
        this.propertyName = propertyName;
        this.descr = descr;
        this.capacity = capacity;
        this.cost = cost;
        this.address = address;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

