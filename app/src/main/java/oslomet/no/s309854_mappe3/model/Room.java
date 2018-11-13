package oslomet.no.s309854_mappe3.model;

public class Room {
    private String name;
    private String details;
    private String latitude;
    private String longitude;

    public Room(String name, String details, String latitude, String longitude) {
        this.name = name;
        this.details = details;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
