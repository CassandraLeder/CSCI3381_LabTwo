/*
    Functionality for adding meetings to calendar.
 */

import java.util.Date;

public class Meeting extends Event implements Completable {
    private Date endDateTime;
    private String location;

    // status of meeting (whether finished or not)
    private boolean complete;

    // constructors
    // default
    public Meeting() {
        this.endDateTime = new Date();
        this.location = "";
        this.complete = false;
    }

    /*
        overloaded constructor
        please note that bool is not included in parameters
        as it would not make sense to schedule a meeting from the past
    */
    public Meeting(String location, Date endDateTime) {
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

    public Date getEndTime() {
        return this.endDateTime;
    }
    public void setEndTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    // find the total duration of a meeting by subtracting future date from current one
    public int getDuration() {
        // get current date
        Date current_date = new Date();

        // subtract end date (should be in the future !) from current date
        long time = dateTime.getTime() - this.endDateTime.getTime();
        return (int) time;
    }
}