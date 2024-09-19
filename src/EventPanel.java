/*

 */

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventPanel extends JPanel {
    private ArrayList<Event> events;
    private JButton completeButton;

    public EventPanel(ArrayList<Event> events) {
        this.events = events;
        this.completeButton = new JButton("Complete?");
    }

    // update background colour of EventPanel based on event
    public void updateUrgency() {
        LocalDateTime current_time = LocalDateTime.now();
        DeadLine current_deadline = new DeadLine("now", current_time); // use for comparison

        // for every event see if it occurs before, after, or during current day
        for (Event event : this.events) {
            if (current_deadline.compareTo(event) > 1) {
                super.setBackground(Color.RED);
            }
            else if (current_deadline.compareTo(event) < 1) {
               // if (event.getDateTime())
            }
        }

        super.setBackground(Color.RED);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
