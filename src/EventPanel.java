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
    private final Font std_font = new Font("Times New Roman", Font.PLAIN, 17);

    // members used in paintComponent
    int lambda = 0; // this serves as a multiplier for spacing in paintComponent()
    final int X_DIM = 10;
    final int Y_DIM = 70;
    final int SPACING = 100;

    public EventPanel(ArrayList<Event> events) {
        // set up panel
        final Dimension PANEL_DIM = new Dimension(900, 500);
        final Point PANEL_LOCATION = new Point(130, 100);
        final Point BUTTON_LOCATION = new Point(0,0); // (0,0) relative to panel

        setLocation(PANEL_LOCATION);
        setPreferredSize(PANEL_DIM);
        setBackground(Color.BLACK);

        // populate events
        this.events = events;

        // create button and add to panel
        this.completeButton = new JButton("Complete?");
        this.completeButton.setFont(std_font);
        this.completeButton.setLocation(BUTTON_LOCATION);
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
        int curr_x = this.X_DIM; // represents x point at any given time (always equal to X_DIM)
        int curr_y = this.Y_DIM; // '      '   y '                   '

        super.paintComponent(g);
        g.setFont(std_font);

        for (Event event : this.events) {
            // if our event is a meeting, output the details
            if (event.getClass().isInstance(event) && event.toString().contains("Meeting")) {
                Meeting meeting = (Meeting) event;

                // create an output string
                StringBuilder meeting_output = new StringBuilder();

                // add meeting name
                meeting_output.append("Meeting: ").append(meeting.getName());

                // avoid displaying "0" if the minutes are "00"
                String start_minute = "";
                String end_minute = "";

                if (meeting.getStartTime().getMinute() == 0) {
                    start_minute = "00";
                }
                else {
                    start_minute = meeting.getStartTime().getMinute() + "";
                }
                if (meeting.getEndTime().getMinute() == 0) {
                    end_minute = "00";
                }
                else {
                    end_minute = meeting.getEndTime().getMinute() + "";
                }

                // add start and end times
                meeting_output.append(meeting.getStartTime().getHour()).append(":").append(start_minute);
                meeting_output.append(" - ").append(meeting.getEndTime().getHour()).append(":").append(end_minute);

                // add meeting location
                meeting_output.append(" at ").append(meeting.getLocation());
                // add meeting duration
                meeting_output.append(" total duration: ").append(meeting.getDuration());
                // add completion status
                meeting_output.append(" completion status: ").append(meeting.isComplete());

                // finally, draw it
                g.drawString(meeting_output.toString(), curr_x, curr_y);
            }
            // if our event is a deadline, output its details
            else if (event.getClass().isInstance(event) && event.toString().contains("DeadLine")) {

                DeadLine deadline = (DeadLine) event;

                // create new deadline_output string
                StringBuilder deadline_output = new StringBuilder();

                // add name
                deadline_output.append("Deadline: ").append(deadline.getName());

                // avoid having minute only display 0 instead of 00
                String hour = deadline.getDeadline().getHour() + "";
                String minute = "";

                if (deadline.getDeadline().getMinute() == 0) {
                    minute = "00";
                }
                else {
                    minute = deadline.getDeadline().getMinute() + "";
                }

                // add time due
                deadline_output.append(" due at ").append(hour).append(":").append(minute);
                // add completion status
                deadline_output.append(" completion status: ").append(deadline.isComplete());

                // draw at X_DIM so the events are lined up
                g.drawString(deadline_output.toString(), curr_x, curr_y);
            }

            // update current x, y for next iteration
            this.lambda = 0;
            curr_y += (this.SPACING / 2);
        }
    }
}
