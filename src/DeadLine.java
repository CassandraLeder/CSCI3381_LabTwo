/*
    This class is used to represent deadlines within the calendar program
 */

public class DeadLine extends Event implements Completable {
    boolean complete = false;

    public void complete() {
        complete = true;
    }

    public boolean isComplete() {
        return complete;
    }
}
