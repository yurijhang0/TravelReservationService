package table_structures;

public class ViewProperty {
    private String name;
    private String address;
    private String description;
    private String averageRating;
    private String capacity;
    private String cost;

    public ViewProperty(String name, String address, String description, String averageRating, String capacity,
                        String cost) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.averageRating = averageRating;
        this.capacity = capacity;
        this.cost = cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getCost() {
        return cost;
    }
}
