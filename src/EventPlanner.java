/*
    main method for GUI implementation
 */

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventPlanner {
    public static void main(String[] args) { // Why does Java need me to specify args when there is no need for them ???!!!!! ANGER!
        // constants
        final int X_SIZE = 1000;
        final int Y_SIZE = 1000;
        final Color PERIWINKLE = new Color(142, 130, 254);

        System.setProperty("file.encoding", "UTF-8");

        // create jframe
        JFrame frame = new JFrame("Event Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(X_SIZE, Y_SIZE);


        // creates objects of implemented classes
        addDefaultEvents(frame);

        // add to jframe
        frame.pack();
        frame.setBackground(PERIWINKLE);
        frame.setVisible(true);
    }

    // creates default events to test GUI
    public static void addDefaultEvents(JFrame frame) {
        // example meeting
        LocalDateTime start_date = LocalDateTime.of(2024, 10, 31, 12, 0);
        LocalDateTime end_date = LocalDateTime.of(2024, 10, 31, 12, 50);
        Meeting meeting = new Meeting("Spooky Philosophy Class", start_date, end_date, "Irby 307");

        // example deadline
        LocalDateTime lab_deadline = LocalDateTime.of(2024, 9, 25, 23, 59);
        DeadLine lab_two = new DeadLine("Lab 2", lab_deadline);

        ArrayList<Event> events = new ArrayList<Event>();
        events.add(meeting);
        events.add(lab_two);

        // create panel objects and add to frame
        EventListPanel event_list_panel = new EventListPanel(events);
        frame.getContentPane().add(event_list_panel);
    }
}