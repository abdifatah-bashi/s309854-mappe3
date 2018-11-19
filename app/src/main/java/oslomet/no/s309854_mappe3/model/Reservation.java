package oslomet.no.s309854_mappe3.model;

public class Reservation {
    private String firstName;
    private String lastName;
    private String room;
    private String date;
    private String time;

    public Reservation(String firstName, String lastName, String room, String date, String time) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.room = room;
        this.date = date;
        this.time = time;
    }

    public void setFirstName(String name) {
        this.firstName = name;
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

    public void setTime(String time) {
        this.time = time;
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

    public String getTime() {
        return time;
    }
}
