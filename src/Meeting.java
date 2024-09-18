/*
    Functionality for adding meetings to calendar.
 */

import java.time.LocalDate;
import java.time.Duration;

public class Meeting extends Event implements Completable {
    private LocalDate endDateTime;
    private String location;

    // status of meeting (whether finished or not)
    private boolean complete;

    // constructors
    // default
    public Meeting() {
        this.endDateTime = new LocalDate(0,0,0);
        this.location = "";
        this.complete = false;
    }

    /*
        overloaded constructor
        please note that bool is not included in parameters
        as it would not make sense to schedule a meeting from the past
    */
    public Meeting(String location, LocalDate endDateTime) {
        this.endDateTime = endDateTime;
        this.location = location;
        this.complete = false;
    }

    // getters && setters
    public boolean isComplete() { return this.complete; }
    // this function sets complete to true
    public void complete() {
        this.complete = true;
    }

    public LocalDate getEndTime() {
        return this.endDateTime;
    }
    public void setEndTime(LocalDate endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    // find the total duration of a meeting by subtracting future date from current one
    public Duration getDuration() {
        // get current date
        Duration meeting_duration = new Duration();

        // subtract end date (should be in the future !) from current date
        meeting_duration = dateTime.getTime() - this.endDateTime.getTime();
        return meeting_duration;
    }
}