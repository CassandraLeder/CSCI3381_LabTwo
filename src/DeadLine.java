/*
    This class is used to represent deadlines within the calendar program
 */

public class DeadLine extends Event implements Completable {
    // status of deadline (whether finished or not)
    private boolean complete;

    // use default constructor
    public DeadLine() { this.complete = false; }

    // getters && setters
    // set complete to true
    public void complete() {
        this.complete = true;
    }
    public boolean isComplete() {
        return this.complete;
    }
}
