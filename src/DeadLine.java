/*
    This class is used to represent deadlines within the calendar program
 */

import java.time.LocalDateTime;

public class DeadLine extends Event implements Completable {
    // status of deadline (whether finished or not)
    private boolean complete;
    private String name;
    private LocalDateTime deadline;

    // constructors
    // default
    public DeadLine() {
        this.complete = false;
        this.name = "";
        this.deadline = LocalDateTime.now();
    }

    // overloaded constructor
    // no boolean in parameters
    public DeadLine(String name, LocalDateTime deadline) {
        this.name = name;
        this.deadline = deadline;
        this.complete = false;
    }

    // getters && setters
    // set complete to true
    public void complete() {
        this.complete = true;
    }
    public boolean isComplete() {
        return this.complete;
    }

    /*
        it does not make sense to change the name and date of a preexisting deadline (should set to complete or purge),
        so only use getters for these private variables
    */
    public String getName() { return this.name; }
    public LocalDateTime getDeadline() { return this.deadline; }
}
