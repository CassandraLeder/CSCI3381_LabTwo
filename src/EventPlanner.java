/*
    main method for GUI implementation
 */

import javax.swing.*;
import java.time.LocalDateTime;

public class EventPlanner {
    public static void main() {
        // constants
        final int X_SIZE = 500;
        final int Y_SIZE = 500;

        // create jframe
        JFrame frame = new JFrame("Event Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(X_SIZE, Y_SIZE);

        // create objects of implemented classes
        EventPlanner eventPlanner = new EventPlanner();
        EventListPanel panel = new EventListPanel();

        // add to jframe
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    // creates default events to test GUI
    public static void addDefaultEvents(EventPanel events) {
        LocalDateTime start_date = LocalDateTime.of(2024, 10, 31, 12, 0);
        LocalDateTime end_date = LocalDateTime.of(2024, 10, 31, 12, 50);

        Meeting meeting = new Meeting("Spooky Philosophy Class", start_date, end_date, "Irby 307");

        LocalDateTime lab_deadline = LocalDateTime.of(2024, 9, 25, 23, 59);
        DeadLine lab_two = new DeadLine("Lab 2", lab_deadline);



    }
}
