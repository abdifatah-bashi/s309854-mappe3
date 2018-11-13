package oslomet.no.s309854_mappe3.model;

public class Reservation {
    private String firstName;
    private String lastName;


    private String room;
    private String date;
    private String from;
    private String to;

    public Reservation(String firstName, String lastName, String room, String date, String from, String to) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.room = room;
        this.date = date;
        this.from = from;
        this.to = to;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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
