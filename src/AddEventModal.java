/*
    Adds event to planner (deadline or meeting).
 */

import javax.swing.*;

public class AddEventModal extends JDialog {
    JDialog dialog;

    // constructors based on parameter type
    public AddEventModal(Meeting meeting) {
        this.dialog = new JDialog();
        this.dialog.setTitle(meeting.getName());
    }

    public AddEventModal(DeadLine deadline) {
        this.dialog = new JDialog();
        this.dialog.setTitle(deadline.getName());
    }
}
