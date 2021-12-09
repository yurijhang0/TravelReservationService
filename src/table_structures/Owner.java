package table_structures;

public class Owner {

    private String ownerName;
    private String rating;
    private String numProp;
    private String avgPropRating;

    public Owner(String ownerName, String rating, String numProp, String avgPropRating) {
        this.ownerName = ownerName;
        this.rating = rating;
        this.numProp = numProp;
        this.avgPropRating = avgPropRating;
    }

    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getNumProp() {
        return numProp;
    }
    public void setNumProp(String numProp) {
        this.numProp = numProp;
    }
    public String getAvgPropRating() {
        return avgPropRating;
    }
    public void setAvgPropRating(String avgPropRating) {
        this.avgPropRating = avgPropRating;
    }
}
