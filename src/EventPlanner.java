/*
    main method for GUI implementation
 */

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventPlanner {
    public static void main(String[] args) { // Why does Java need me to specify args when there is no need for them ???!!!!! ANGER!
        // constants
        final int X_SIZE = 900;
        final int Y_SIZE = 900;

        System.setProperty("file.encoding", "UTF-8");

        // create jframe
        JFrame frame = new JFrame("Event Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(X_SIZE, Y_SIZE);

        /* // create objects of implemented classes
        EventPlanner eventPlanner = new EventPlanner();
        EventListPanel panel = new EventListPanel(); */

        addDefaultEvents(frame);
        // add to jframe
        frame.pack();
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
        EventPanel event_panel = new EventPanel(events);
        EventListPanel event_list_panel = new EventListPanel(events);
        event_list_panel.add(event_panel);

        frame.getContentPane().add(event_list_panel);
    }
}