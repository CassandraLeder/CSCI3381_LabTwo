/*
    main method for GUI implementation
 */

import javax.swing.*;

public class EventPlanner {
    public static void main(String[] args) {
        // constants
        final int X_SIZE = 500;
        final int Y_SIZE = 500;

        // create jframe
        JFrame frame = new JFrame("Event Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(X_SIZE, Y_SIZE);
        frame.pack();

        // create objects of implemented classes
        EventPlanner eventPlanner = new EventPlanner();
        EventListPanel panel = new EventListPanel();

        // add to jframe
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    // creates default events to test GUI
    public static void addDefaultEvents(EventPanel events) {

    }
}
