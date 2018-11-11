package oslomet.no.s309854_mappe3.model;

public class Reservation {
    private String name;
    private String building;
    private String room;
    private String date;
    private String from;
    private String to;

    public Reservation(String name, String building, String room, String date, String from, String to) {
        this.name = name;
        this.building = building;
        this.room = room;
        this.date = date;
        this.from = from;
        this.to = to;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public String getBuilding() {
        return building;
    }

    public String getRoom() {
        return room;
    }

    public String getDate() {
        return date;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
