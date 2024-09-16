import java.util.Date;

/*
    Event class represents a calendar of events.
    The idea is to add and remove dates from this calendar,
    which is based on the Gregorian calendar!
    (not all countries use this calendar system)
 */

public abstract class Event implements Comparable<Event> {
    String name = "";
    Date dateTime;

    // default constructor
    public Event() {
        this.name = "";
        this.dateTime = new Date();
    }

    // parameterized/overloaded constructor
    public Event(String name, Date dateTime) {
        this.name = name;
        this.dateTime = dateTime;
    }

    // getters && setters
    public String getName() {
        return(this.name);
    }
    public Date getDateTime() {
        return(this.dateTime);
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    // implementation of Comparable<T>
    public int compareTo(Event e) {
        return(e.dateTime.compareTo(this.dateTime));
    }
}
