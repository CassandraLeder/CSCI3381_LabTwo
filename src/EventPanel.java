/*
    This class sets the background colour of the EventPanel based on the calculated urgency of the event
    And creates a panel who owns a button
 */

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventPanel extends JPanel {
    private final ArrayList<Event> events;
    private JButton completeButton;
    private final Font std_font = new Font("Helvetica", Font.ITALIC, 20);

    public EventPanel(ArrayList<Event> events) {
        // set up panel
        final Dimension STD_DIM = new Dimension(500, 500);
        final Point LOCATION = new Point(10, 300);

        setLocation(LOCATION);
        setPreferredSize(STD_DIM);

        // populate events
        this.events = events;

        // create button and add to panel
        this.completeButton = new JButton("Complete?");
        this.completeButton.setFont(std_font);
        super.add(completeButton);

        // completeButton's action_listener
        // changes every event to complete, but should actually only do one event... to be continued.
        completeButton.addActionListener(e -> {
            // for every event in events
            for (Event event : events) {

                // if event is meeting then set to complete
                if (event.getClass().isInstance(event) && event.toString().contains("Meeting")) {
                    Meeting meeting = (Meeting) event;
                    if (!meeting.isComplete()) {
                        meeting.complete();
                    }
                }
                // if the event is a deadline
                else if (event.getClass().isInstance(event) && event.toString().contains("DeadLine")) {
                    DeadLine deadLine = (DeadLine) event;
                    if (!deadLine.isComplete()) {
                        deadLine.complete();
                    }
                }
                // if error
                else {
                    throw new RuntimeException("Encountered event object that is not Meeting or DeadLine" + event.toString());
                }
            }
        });
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
                super.setBackground(Color.GREEN);
            }
            else if (current_deadline.compareTo(event) == 0) {
                super.setBackground(Color.YELLOW);
            }
        }
    }

    // this method will write out the details of an event to the dialog box
    public void paintComponent(Graphics g) {
        // constants
        final int X_DIM = 10;
        final int Y_DIM = 200;
        final int SPACING = 100;
        int i = 0;

        super.paintComponent(g);
        g.setFont(std_font);

        for (Event event : this.events) {
            // if our event is meeting, output the details
            if (event.getClass().isInstance(event) && event.toString().contains("Meeting")) {
                Meeting meeting = (Meeting) event;

                // output meeting name
                g.drawString("Meeting: " + meeting.getName(), (X_DIM + (SPACING * i)), Y_DIM);

                ++i; // there must be a better way...

                // output meeting time
                String time = ", " + meeting.getStartTime().getHour() + ":" + meeting.getStartTime().getMinute() + " - "
                        + meeting.getEndTime().getHour() + ":" + meeting.getEndTime().getMinute();
                g.drawString(time, (X_DIM + (SPACING * i)), Y_DIM);

                ++i;

                // output meeting location
                g.drawString(", " + meeting.getLocation(), (X_DIM + (SPACING * i)), Y_DIM);

                ++i;

                // output meeting duration
                g.drawString(", total duration: " + meeting.getDuration(), (X_DIM + (SPACING * i)), Y_DIM);

                ++i;

                // output completion status
                g.drawString(", completion status: " + meeting.isComplete(), (X_DIM + (SPACING * i)), Y_DIM);

            }
            // if our event is a deadline, output its details
            else if (event.getClass().isInstance(event) && event.toString().contains("DeadLine")) {
                DeadLine deadline = (DeadLine) event;

                g.drawString("Deadline: " + deadline.getName(), (X_DIM + (SPACING * i)), (Y_DIM + (SPACING * i)));

                ++i;

                g.drawString(", due at " + deadline.getDeadline().getHour() + ":" + deadline.getDeadline().getMinute(),
                        (X_DIM + (SPACING * i)), (Y_DIM + (SPACING * i)));

                ++i;

                g.drawString(", completion status: " + deadline.isComplete(), (X_DIM + (SPACING * i)), (Y_DIM + (SPACING * i)));

            }

            ++i;
        }
    }
}
