/*
    Adds event to planner (deadline or meeting).
 */

import javax.swing.*;
import java.awt.*;

public class AddEventModal extends JDialog {
    JDialog dialog;

    // constructors
    public AddEventModal() {
        dialog = new JDialog();
    }

    // overloaded
    public AddEventModal(Meeting meeting) {
        this.dialog.setTitle("Meeting: " + meeting.getName());
        this.dialog.setVisible(true);
    }

    public AddEventModal(DeadLine deadline) {
        this.dialog.setTitle("Deadline: " + deadline.getName());
        this.dialog.setVisible(true);
    }

    // this is a special constructor that creates a jdialog for events and diplays them
    public AddEventModal(Event event) {

        // check to see if the event is a meeting
        if (event.toString().contains("Meeting")) {
            System.out.println("I'm here!");
            this.dialog = new JDialog(this.dialog, "Meeting: " + event.getName());
        }
        // if event is a deadline
        else if (event.toString().contains("DeadLine")) {
            // ....
            this.dialog = new JDialog(this.dialog, "Deadline: " + event.getName());
            System.out.println("I'm there!");
        }
        else {
            throw new RuntimeException("Event object other than meeting or deadline passed to function: " + event.toString());
        }

        // configure dialog options
        dialog.pack();
        dialog.setSize(new Dimension(400, 200));
        dialog.setVisible(true);
    }
}
