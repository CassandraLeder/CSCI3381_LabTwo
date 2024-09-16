/*
    main method for GUI implementation
 */

import javax.swing.*;

public class EventPlanner {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Event Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EventPlanner eventPlanner = new EventPlanner();
        EventListPanel panel = new EventListPanel();

        frame.add(panel);
    }
}
