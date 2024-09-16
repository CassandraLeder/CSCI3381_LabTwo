import java.util.Date;

/*

 */


public class Meeting extends Event implements Completable {
    Date endDateTime;
    String location;
    boolean complete;

    public void complete() {
        this.complete = true;
    }

    public boolean isComplete() {
        return this.complete;
    }

    public Date getEndTime() {
        return this.endDateTime;
    }

    // this is absolutely incorrect
    public int getDuration() {
        return (getDateTime() - getEndTime());
    }

    public String getLocation() {
        return this.location;
    }
    public void setEndTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
