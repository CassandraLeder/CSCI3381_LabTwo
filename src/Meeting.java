/*
    Functionality for adding meetings to calendar.
 */

import java.time.LocalDateTime;
import java.time.Duration;

public class Meeting extends Event implements Completable {
    private LocalDateTime endDateTime;
    private LocalDateTime startDateTime;
    private String location;
    private String name;

    // status of meeting (whether finished or not)
    private boolean complete;

    // constructors
    // default
    public Meeting() {
        // should i initialize to now or minimum possible date ? hmmmm...
        // 囧 《- me when it literally does not matter because who is using this constructor
        this.endDateTime = LocalDateTime.now(); // LocalDate.MIN;
        this.startDateTime = LocalDateTime.now(); // ^
        this.name = "";
        this.location = "";
        this.complete = false;
    }

    /*
        overloaded constructor
        please note that bool is not included in parameters
        as it would not make sense to schedule a meeting from the past
    */
    public Meeting(String name, LocalDateTime startDateTime, LocalDateTime endDateTime, String location) {
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        this.complete = false;
    }

    // getters && setters
    public boolean isComplete() { return this.complete; }
    // this function sets complete to true
    public void complete() { this.complete = true; }

    /*
        Following the logic of DeadLine.java, I might purge some of these getters/setters, perhaps to the despair
        of Baarsch.
     */

    public LocalDateTime getStartTime() { return this.startDateTime; }
    public void setStartTime(LocalDateTime startDateTime) { this.startDateTime = startDateTime; }

    public LocalDateTime getEndTime() { return this.endDateTime; }
    public void setEndTime(LocalDateTime endDateTime) { this.endDateTime = endDateTime; }

    public String getLocation() { return this.location; }
    public void setLocation(String location) { this.location = location; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }


    // find the total duration of a meeting by subtracting future date from current one
    public Duration getDuration() {

        // subtract start date (should be in the future !) from end date
        return (Duration.between(this.startDateTime, this.endDateTime));
    }
}