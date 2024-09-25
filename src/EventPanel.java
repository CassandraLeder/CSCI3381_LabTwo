/*
    This class sets the background colour of the EventPanel based on the calculated urgency of the event
    And creates a panel who owns a button
 */

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class EventPanel extends JPanel {
    private final Event event;
    private JButton completeButton;
    private final Font STD_FONT = new Font("Times New Roman", Font.BOLD, 20);
    private final Color PERIWINKLE = new Color(142, 130, 254);

    // members used in paintComponent
    int lambda = 0; // this serves as a multiplier for spacing in paintComponent()
    final int X_DIM = 30;
    final int Y_DIM = 70;
    final int SPACING = 50;

    public EventPanel(Event event) {
        // set up panel
        final Dimension PANEL_DIM = new Dimension(565, 120);

        setPreferredSize(PANEL_DIM);
        setBackground(PERIWINKLE);

        // populate events
        this.event = event;

        // create button and add to panel
        this.completeButton = new JButton("Complete?");
        this.completeButton.setFont(STD_FONT);
        super.add(completeButton);

        // update urgency
        updateUrgency();

        // completeButton's action_listener
        // changes every event to complete
        completeButton.addActionListener(e -> {
            // if event is meeting then set to complete
            if (event.getClass().isInstance(event) && event.toString().contains("Meeting")) {
                Meeting meeting = (Meeting) event;
                    if (!meeting.isComplete()) {
                        meeting.complete();
                    }
            }
            // if the event is a deadline set to complete
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

            // make sure to update panel with completion status
            super.revalidate();
            super.repaint();
            updateUrgency();
        });
    }

    // update background colour of EventPanel based on event
    public void updateUrgency() {
        LocalDateTime current_time = LocalDateTime.now();
        DeadLine current_deadline = new DeadLine("now", current_time); // use for comparison
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        final int HOURS_IN_DAY = 24;
        final int LENGTH = 2; // number of days that make green

        // if event is meeting
        if (this.event.getClass().isInstance(this.event) && this.event.toString().contains("Meeting")) {
            Meeting meeting = (Meeting) this.event;

            Event buff_event = this.event;
            buff_event.setDateTime(meeting.getStartTime());

            if (current_deadline.compareTo(buff_event) < 0) { // meeting already begun
                super.setBackground(Color.RED);
            }
            else if (current_deadline.compareTo(buff_event) > 0) { // if meeting hasn't begun
                if (meeting.getStartTime().isAfter(tomorrow)) { // if meeting starts after tomorrow
                    super.setBackground(Color.GREEN);
                }
                else if (meeting.getStartTime().isBefore(current_time.plusHours(23))) { // if meeting starts before tomorrow
                    super.setBackground(Color.YELLOW);
                }
                else if (meeting.getStartTime().isBefore(current_time.plusHours(1))) { // if meeting starts in less than an hour
                    super.setBackground(Color.RED);
                }
            }
            else { // 0 returned; meeting is beginning now
                super.setBackground(Color.RED);
            }
        }
        else if (this.event.getClass().isInstance(this.event) && this.event.toString().contains("DeadLine")) {
            DeadLine deadline = (DeadLine) this.event;

            Event buff_event = this.event;
            buff_event.setDateTime(deadline.getDeadline());

            if (current_deadline.compareTo(buff_event) < 0) { // deadline already past
                super.setBackground(Color.RED);
            }
            else if (current_deadline.compareTo(buff_event) > 0) { // deadline not yet past
                if (deadline.getDeadline().isAfter(tomorrow)) { // something is due at least a day from now
                    super.setBackground(Color.GREEN);
                }
                else if (deadline.getDeadline().isBefore(tomorrow)) { // if deadline occurs before tomorrow
                    super.setBackground(Color.YELLOW);
                }
                else if (deadline.getDeadline().isBefore(current_time.plusHours(1))) { // if assignment due less than one hour from now
                    super.setBackground(Color.RED);
                }
            }
            else { // deadline is now
                super.setBackground(Color.RED);
            }
        }

        // update
        super.revalidate();
        super.repaint();
    }

    // this method will write out the details of an event to the dialog box
    // dear reader: learn from my mistake, use labels
    public void paintComponent(Graphics g) {
        int curr_x = this.X_DIM; // represents x point at any given time (always equal to X_DIM)
        int curr_y = this.Y_DIM; // '      '   y '                   '

        super.paintComponent(g);
        g.setFont(STD_FONT);

        // if our event is a meeting, output the details
        if (this.event.getClass().isInstance(this.event) && this.event.toString().contains("Meeting")) {
            Meeting meeting = (Meeting) this.event;

            // create an output string
            StringBuilder meeting_output = new StringBuilder();

            // add meeting name
            meeting_output.append("Meeting: ").append(meeting.getName()).append(" ");

            // avoid displaying "0" if the minutes are "00"
            String start_minute = "";
            String end_minute = "";

            if (meeting.getStartTime().getMinute() == 0) {
                start_minute = "00";
            } else {
                start_minute = meeting.getStartTime().getMinute() + "";
            }
            if (meeting.getEndTime().getMinute() == 0) {
                end_minute = "00";
            } else {
                end_minute = meeting.getEndTime().getMinute() + "";
            }

            // add start and end times
            meeting_output.append(meeting.getStartTime().getHour()).append(":").append(start_minute);
            meeting_output.append(" - ").append(meeting.getEndTime().getHour()).append(":").append(end_minute);

            // add meeting location
            meeting_output.append(" at ").append(meeting.getLocation());

            // this is usually where meeting is cut off...
            g.drawString(meeting_output.toString(), curr_x, curr_y);
            meeting_output = new StringBuilder();

            curr_y += (this.SPACING / 2);
            // add meeting duration
            String hours = "";

            if (meeting.getDuration().toHours() == 0) {
               hours = "and ";
            }
            else {
                hours = meeting.getDuration().toHours() + " hours and ";
            }

            meeting_output.append("total duration: ").append(hours)
                    .append(meeting.getDuration().toMinutes()).append(" minutes");
            // add completion status
            meeting_output.append(" completion status: ").append(meeting.isComplete());

            // finally, draw it
            g.drawString(meeting_output.toString(), curr_x, curr_y);
        }
        // if our event is a deadline, output its details
        else if (this.event.getClass().isInstance(this.event) && this.event.toString().contains("DeadLine")) {
            DeadLine deadline = (DeadLine) this.event;

            // create new deadline_output string
            StringBuilder deadline_output = new StringBuilder();

            // add name
            deadline_output.append("Deadline: ").append(deadline.getName());

            // avoid having minute only display 0 instead of 00
            String hour = deadline.getDeadline().getHour() + "";
            String minute = "";

            if (deadline.getDeadline().getMinute() == 0) {
                minute = "00";
            } else {
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
