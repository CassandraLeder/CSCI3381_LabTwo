/*
    Adds event to planner (deadline or meeting).
 */

import javax.swing.*;

public class AddEventModal extends JDialog {
    JDialog dialog;

    // constructors
    public AddEventModal() {
        dialog = new JDialog();
    }

    // overloaded
    public AddEventModal(Meeting meeting) {
        this.dialog.setTitle(meeting.getName());
    }

    public AddEventModal(DeadLine deadline) {
        this.dialog.setTitle(deadline.getName());
    }

    public AddEventModal(Event event) {
        if (event.getName() == "Meeting") {
            System.out.println("I'm here!");
            // do something
        }
        else if (event.getName() == "Deadline") {
            // ....
            System.out.println("I'm there!");
        }
    }
}
