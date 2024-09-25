/*
    Adds event to planner (deadline or meeting).
 */

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AddEventModal extends JDialog {
    private final Dimension DIALOG_DIM = new Dimension(500,500);
    private final Dimension PANEL_DIM = new Dimension(300,100);
    private final Dimension INPUT_PANEL_DIM = new Dimension(300,400);
    private final Color PERIWINKLE = new Color(142, 130, 254);
    private final Font STD_FONT = new Font("Times New Roman", Font.PLAIN, 20);

    // constructors
    // default constructor
    public AddEventModal() {
        super.setTitle("Add Event");
        // create our jpanel
        JPanel dialog_panel = new JPanel();
        dialog_panel.setBackground(PERIWINKLE);
        dialog_panel.setPreferredSize(PANEL_DIM);

        // create new dialog for users to create either new meeting or deadline
        JButton meeting_button = new JButton("Meeting");
        meeting_button.setFont(STD_FONT);

        JButton deadline_button = new JButton("Deadline");
        deadline_button.setFont(STD_FONT);

        dialog_panel.add(meeting_button);
        dialog_panel.add(deadline_button);

        super.add(dialog_panel);
        super.pack();
        super.setSize(DIALOG_DIM);
        super.setBackground(PERIWINKLE);
        super.setFont(STD_FONT);
        super.setVisible(true);


        // input action listener
        meeting_button.addActionListener(e -> {
            JPanel meeting_panel = new JPanel();
            ArrayList<JTextField> meeting_args = new ArrayList<>();
            meeting_args.add(new JTextField("[name]"));
            meeting_args.add(new JTextField("[start date (MM/DD/YYYY)]"));
            meeting_args.add(new JTextField("[start time (24hr)]"));
            meeting_args.add(new JTextField("[end date (MM/DD/YYYY)]"));
            meeting_args.add(new JTextField("[end time (24hr)]"));
            meeting_args.add(new JTextField("[location]"));

            int curr_x = 10;
            int curr_y = 50;

            for (JTextField arg : meeting_args) {
                curr_x += 55;
                arg.setFont(STD_FONT);
                arg.setSize(70, 50);
                arg.setLocation(10, curr_y);
                meeting_panel.add(arg);
            }

            meeting_panel.setVisible(true);
            meeting_panel.setSize(DIALOG_DIM);

            super.add(meeting_panel);
        });

        deadline_button.addActionListener(e -> {

        });
    }

    // overloaded
    public void newMeeting(String name, LocalDateTime startDateTime, LocalDateTime endDateTime, String location) {
        Meeting meeting = new Meeting(name, startDateTime, endDateTime, location);
        super.setTitle("Meeting: " + meeting.getName());
        super.setVisible(true);
    }

    public void newDeadLine(String name, LocalDateTime deadline) {
        DeadLine new_deadline = new DeadLine(name, deadline);
        super.setTitle("Deadline: " + new_deadline.getName());
        super.setVisible(true);
    }

    // this is a special constructor that creates a jdialog for events and diplays them
    public AddEventModal(Event event) {


        // check to see if the event is a meeting
        if (event.toString().contains("Meeting")) {
        }
        // if event is a deadline
        else if (event.toString().contains("DeadLine")) {
            // ....
        }
        else {
            throw new RuntimeException("Event object other than meeting or deadline passed to function: " + event.toString());
        }

        // configure dialog options
        super.pack();
        super.setSize(new Dimension(400, 200));
        super.setVisible(true);
    }
}
