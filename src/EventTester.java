/*
    The main program for LabTwo
    - Cassandra Leder
    16/9/24
 */

import java.util.Calendar;
import java.time.LocalDate;

public class EventTester {
    public static void main(String[] args) {
        test_function();

    }

    public static void test_function() {
        System.out.println("hi!!! running some tests....");

        // a meeting for the philosophy department for example
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 10);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.YEAR, 2024);
        LocalDate date;

        Meeting newMeeting = new Meeting("Irby 307", date);
        System.out.println("I have a meeting at " + newMeeting.getLocation() + " for " + newMeeting.getDuration());

        DeadLine LabTwo = new DeadLine();

        if (!LabTwo.isComplete()) {
            System.out.println("Lab 2 is uh........ a work in progress");
        }
        else if (LabTwo.isComplete()) {
            System.out.println("Will the skills acquired from this course bring financial compensation that will markedly improve my quality of life" +
                    " or was the Buddha right about suffering?");
        }
        else {
            System.out.println("yikes!");
        }
    }
}