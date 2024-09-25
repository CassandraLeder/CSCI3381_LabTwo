/*
    Adds event to planner (deadline or meeting).
 */

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddEventModal extends JDialog {
    // constants
    private final Dimension DIALOG_DIM = new Dimension(500,500);
    private final Dimension PANEL_DIM = new Dimension(300,100);
    private final Dimension INPUT_PANEL_DIM = new Dimension(300,250);
    private final Color PERIWINKLE = new Color(142, 130, 254);
    private final Font STD_FONT = new Font("Times New Roman", Font.PLAIN, 20);
    private final Font STD_ZH_FONT = new Font("SimSun", Font.PLAIN, 30);
    private final String[] en_text = {"Add Event", "Meeting", "DeadLine"};
    private final String[] zh_text = {"添加新事件", "会议", "限期"};

    private JPanel dialog_panel;
    JPanel input_panel;
    record Attribute(String name, JComponent value) {}             // Baarsch kinda went off with this one
    ArrayList<Attribute> attributes;
    private String language_flag;
    private JPanel displaypanel; // for use in final actionlistener only


    // constructors
    // doing all of this is the constructor feels dirty... we need more methods !
    public AddEventModal(JPanel displaypanel, String language_flag) {
        this.language_flag = language_flag;
        this.displaypanel = displaypanel;

        if (this.language_flag.equals("en")) {
            super.setTitle(en_text[0]); // Add Event
        }
        else if (this.language_flag.equals("zh")) {
            super.setTitle(zh_text[0]);
        }

        attributes = new ArrayList<>();

        // create our jpanel
        dialog_panel = new JPanel();
        dialog_panel.setBackground(PERIWINKLE);
        dialog_panel.setPreferredSize(PANEL_DIM);

        // actually initialized below
        JButton meeting_button = null;
        JButton deadline_button = null;

        // create new dialog for users to create either new meeting or deadline
        if (language_flag.equals("en")) {
            meeting_button = new JButton(en_text[1]); // Meeting
            meeting_button.setFont(STD_FONT);

            deadline_button = new JButton(en_text[2]); // Deadline
            deadline_button.setFont(STD_FONT);
        }
        else if (language_flag.equals("zh")) {
            meeting_button = new JButton(zh_text[1]);
            meeting_button.setFont(STD_ZH_FONT);

            deadline_button = new JButton(zh_text[2]);
            deadline_button.setFont(STD_ZH_FONT);
        }

        dialog_panel.add(meeting_button);
        dialog_panel.add(deadline_button);

        super.add(dialog_panel);
        super.pack();
        super.setSize(DIALOG_DIM);
        super.setBackground(PERIWINKLE);
        super.setVisible(true);

        input_panel = new JPanel();

        // action listeners
        meeting_button.addActionListener(e -> {
            input_panel = addInputPanel(0); // 0 = meeting
            dialog_panel.add(input_panel);
            dialog_panel.revalidate();
            dialog_panel.repaint();
        });

        deadline_button.addActionListener(e -> {
            input_panel = addInputPanel(1); // 1 = deadline
            dialog_panel.add(input_panel);
            dialog_panel.revalidate();
            dialog_panel.repaint();
        });
    }

    /*
        creates an input panel for Meeting or DeadLine events
            0 = meeting
            1 = deadline
    */

    private JPanel addInputPanel(int choice) {
        // configure panel;
        input_panel.setBackground(PERIWINKLE);
        input_panel.setPreferredSize(INPUT_PANEL_DIM);

        attributes.clear();
        input_panel.removeAll();

        // if a meeting
        if (choice == 0) {
            // english text
            if (this.language_flag.equals("en")) {
                attributes.add(new Attribute("[name]: ", new JTextField(10)));
                attributes.add(new Attribute("[start date (yyyy-MM-dd)]: ", new JTextField(10)));
                attributes.add(new Attribute("[end date (yyyy-MM-dd)]: ", new JTextField(10)));
                attributes.add(new Attribute("[start time (24hr) (Ex. 14:52)]: ", new JTextField(10)));
                attributes.add(new Attribute("[end time (24hr) (Ex. 14:52)]: ", new JTextField(10)));
                attributes.add(new Attribute("[location]: ", new JTextField(10)));
            }

            // mandarin text
            else if (this.language_flag.equals("zh")) {
                attributes.add(new Attribute("[名称]: ", new JTextField(10)));
                attributes.add(new Attribute("[开始日期 (yyyy-MM-dd)]: ", new JTextField(10))); // 开始 = start
                attributes.add(new Attribute("[结束日期 (yyyy-MM-dd)]: ", new JTextField(10))); // 结束 = end
                attributes.add(new Attribute("[开始时间 (24hr) (Ex. 14:52)]: ", new JTextField(10)));
                attributes.add(new Attribute("[结束时间 (24hr) (Ex. 14:52)]: ", new JTextField(10)));
                attributes.add(new Attribute("[地点]: ", new JTextField(10)));
            }
        }
        // if a deadline
        else if (choice == 1) {
            if (this.language_flag.equals("en")) {
                // get the arguments for a new deadline
                attributes.add(new Attribute("[name]: ", new JTextField(10)));
                attributes.add(new Attribute("[date due (yyyy-MM-dd)]: ", new JTextField(10)));
                attributes.add(new Attribute("[time due (24hr) (Ex. 14:52)]: ", new JTextField(10)));
            }
            else if (this.language_flag.equals("zh")) {
                attributes.add(new Attribute("[名称]: ", new JTextField(10)));
                attributes.add(new Attribute("[到日期 (yyyy-MM-dd)]: ", new JTextField(10)));
                attributes.add(new Attribute("[到期时间 (24hr) (Ex. 14:52)]: ", new JTextField(10)));
            }
        }

        // add all attributes to panel
        attributes.forEach(attribute -> {
            input_panel.add(new JLabel(attribute.name));
            input_panel.add(attribute.value);
        });

        // create submit button based on language
        if (this.language_flag.equals("en")) {
            JButton submit_button = new JButton("Submit");
            submit_button.addActionListener(createEvent(choice));

            input_panel.add(submit_button);
            input_panel.setFont(STD_FONT);
        }
        else if (this.language_flag.equals("zh")) {
            JButton submit_button = new JButton("上传");
            submit_button.addActionListener(createEvent(choice));

            input_panel.add(submit_button);
            input_panel.setFont(STD_ZH_FONT);
        }

        // configure and return panel
        input_panel.revalidate();
        input_panel.repaint();

        return(input_panel);
    }

    /*
        Creates specified event when submit button is clicked
            0 = meeting
            1 = deadline
     */
    private ActionListener createEvent(int choice) {
        return event -> {
            // both events share this feature
            String name = getInput(attributes.get(0).value());

            if (choice == 0) {
                // args are String name, LocalDateTime start, LocalDateTime end, String location
                // needs more error checking


                LocalDate start_date = LocalDate.parse(getInput(attributes.get(1).value()), DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDate end_date = LocalDate.parse(getInput(attributes.get(2).value()), DateTimeFormatter.ISO_LOCAL_DATE);
                LocalTime start_time = LocalTime.parse(getInput(attributes.get(3).value()), DateTimeFormatter.ISO_LOCAL_TIME);
                LocalTime end_time = LocalTime.parse(getInput(attributes.get(4).value()), DateTimeFormatter.ISO_LOCAL_TIME);

                LocalDateTime start = LocalDateTime.of(start_date, start_time);
                LocalDateTime end = LocalDateTime.of(end_date, end_time);

                String location = getInput(attributes.get(5).value());

                // create a meeting object and event panel
                Meeting meeting = new Meeting(name, start, end, location);
                EventPanel event_panel = new EventPanel(meeting);
                this.displaypanel.add(event_panel);
            }
            else if (choice == 1) {
                // args are String name and LocalDateTime deadline
                String date = getInput(attributes.get(1).value());
                String time = getInput(attributes.get(2).value());
                LocalDate due_date = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
                LocalTime due_time = LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME);

                LocalDateTime due = LocalDateTime.of(due_date, due_time); // awkward naming to avoid two deadlines

                // create a deadline object and its event panel
                DeadLine deadline = new DeadLine(name, due);
                EventPanel event_panel = new EventPanel(deadline);
                this.displaypanel.add(event_panel);
            }
        };
    }

    private String getInput (JComponent c) {
        if (c instanceof JTextComponent) {
            return ((JTextComponent) c).getText();
        }
        return "";
    }
}
