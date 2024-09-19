/*
    The main program for LabTwo
    For the record, I will use 24 hour time system in order to simply the math
    It makes more sense to format after, although I have the utmost disrespect for the 24-hour format
    - Cassandra Leder
    16/9/24
 */

import java.time.LocalDateTime;
import java.time.Duration;

public class EventTester {
    public static void main(String[] args) {
        test_function();
    }

    /* overloaded print_date method prints the following to console:
       - total duration of meeting
       - meeting name, location, times
       - deadline name, location, time

       i need to make error checking for these
     */

    private static void print_date(Meeting meeting, Duration duration) {
        boolean today;
        StringBuilder output_string = new StringBuilder();

        // check if meeting is happening today (for formatting)
        today = duration.toDays() <= 1;

        // all strings share this piece in common
        output_string.append("Meeting " + meeting.getName() + " at " + meeting.getLocation() + " is ");

        // if meeting is today, print hours and mins not days...
        if (today) {
            if (duration.toHours() > 1) {  // self-explanatory
                output_string.append(duration.toHours() + " hours");

                if (duration.toMinutes() > 1) {
                    output_string.append(duration.toMinutes() + " minutes");
                }
            } else {
                // this case means only minutes matter
                output_string.append(duration.toMinutes() + " minutes");
            }
        } else { // not today

            // god forbid any person have a multi-day long meeting...
            System.out.println("Meeting " + meeting.getName() + " at " + meeting.getLocation() +
                    " is " + duration.toDays() + " days");
        }
        output_string.append(" long.");
        System.out.println(output_string);
    }

    // very simple
    private static void print_date(Meeting meeting) {
        System.out.println("Meeting " + meeting.getName() + " : " +
                meeting.getStartTime() + " - " + meeting.getEndTime() +
                "\n" + meeting.getLocation());
    }

    private static void print_date(DeadLine deadline) {
        System.out.println("Deadline for " + deadline.getName() + " : " +
                deadline.getDeadline());
    }

    // method to carry out various test for the functionality of the program
    public static void test_function() {
        System.out.println("hi!!! running some tests....");

        // a meeting at the philosophy department during halloween at 12:00-12:50 for example
        LocalDateTime start_date = LocalDateTime.of(2024, 10, 31, 12, 0);
        LocalDateTime end_date = LocalDateTime.of(2024, 10, 31, 12, 50);

        // create meeting object
        Meeting newMeeting = new Meeting("Spooky Philosophy Class", start_date, end_date, "Irby 307");

        // find time between now and current meeting
        Duration duration_before_meeting = newMeeting.getDuration();

        print_date(newMeeting);
        print_date(newMeeting, duration_before_meeting);

        LocalDateTime lab_deadline = LocalDateTime.of(2024, 9, 25, 23, 59);
        DeadLine lab_two = new DeadLine("Lab 2", lab_deadline);

        if (!lab_two.isComplete()) {
            print_date(lab_two);
            System.out.println(lab_two.getName() + " is uh........ a work in progress");
        }
        else {
            System.out.println("Will the skills acquired from this course bring financial compensation that will markedly improve my quality of life" +
                    " or was the Buddha right about suffering?");
        }
    }
}