/*
    This panel contains the functionality to add events to the user's calendar
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events;
    private String language_flag = "en"; // set to english by default

    // Swing objects
    private JPanel controlPanel; // controls for event display
    private JPanel displayPanel; // EventListPanel's panel

    private JComboBox<String> setDropDown; // sorts events
    private ArrayList<JCheckBox> filterDisplay;
    private JCheckBox language;
    private JButton addEventButton;
    private JButton resetButton;

    // constants
    // strings
    private final String[] sort_options_en = {"Name (Ascending)", "Name (Descending)", "Date (Ascending)", "Date (Descending)"};
    private final String[] sort_option_zh = {"名成（升序）", "名成 （降序)", "日期 （升序）", "日期 （降序）"};
    private final String[] filter_en = {"Hide finished tasks", "Hide deadlines", "Hide meetings"};
    private final String[] filter_zh = {"隐藏已完成的任务", "隐藏日期", "隐藏会议"};
    private final String[] en_text = {"English", "Add Event", "Reset"};
    private final String[] zh_text = {"中文", "天加事件", "重置"};

    // fonts
    private final Font std_font = new Font("Comic Sans MS", Font.BOLD, 20);
    private final Font zh_font = new Font("SimHei", Font.BOLD, 25);

    // color
    private final Color PERIWINKLE = new Color(142, 130, 254);

    // constructor where everything happens
    public EventListPanel(ArrayList<Event> events) {
        // constants
        final Dimension CONTROL_DIM = new Dimension(215, 1000);
        final Dimension DISPLAY_DIM = new Dimension(515, 900);

        // create objects
        this.events = events;

        controlPanel = new JPanel();
        controlPanel.setPreferredSize(CONTROL_DIM);

        displayPanel = new JPanel();
        displayPanel.setPreferredSize(DISPLAY_DIM);

        setDropDown = new JComboBox(sort_options_en);
        setDropDown.setFont(std_font);


        filterDisplay = new ArrayList<>();
        for (String filter : filter_en) {
            JCheckBox checkBox = new JCheckBox(filter);
            checkBox.setFont(std_font);
            checkBox.setBackground(PERIWINKLE);
            checkBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    filterOut();
                }
            });
            filterDisplay.add(checkBox);
        }

        language = new JCheckBox(en_text[0] + "/" + zh_text[0]);
        language.setFont(zh_font);
        language.setBackground(PERIWINKLE);

        addEventButton = new JButton(en_text[1]);
        addEventButton.setFont(std_font);

        resetButton = new JButton(en_text[2]);
        resetButton.setFont(std_font);


        // add options to control panel
        controlPanel.add(language);
        controlPanel.add(addEventButton);
        controlPanel.add(resetButton);
        for (JCheckBox checkBox : filterDisplay) {
            controlPanel.add(checkBox);
        }

        controlPanel.add(setDropDown);

        // add display/control panel to super panel
        controlPanel.setBackground(PERIWINKLE);
        displayPanel.setBackground(PERIWINKLE);
        super.add(controlPanel);
        super.add(displayPanel);
        super.setBackground(PERIWINKLE);

        // action listeners
        // adds a new event / default events
        addEventButton.addActionListener(e -> {
            ArrayList<EventPanel> eventPanels = new ArrayList<>();

            eventPanels.clear();

            // add any default events (needs error checking)
            for (Event event : this.events) {
                eventPanels.add(new EventPanel(event));
            }

            for (EventPanel eventPanel : eventPanels) {
                displayPanel.add(eventPanel);
            }

            displayPanel.revalidate();
            displayPanel.repaint();

            AddEventModal add_event = new AddEventModal(displayPanel, language_flag);
        });

        language.addActionListener(e -> {
            // if Mandarin is selected
            if (language.isSelected()) {
                language_flag = "zh";

                int i = 0;
                for (JCheckBox checkBox : filterDisplay) {
                    checkBox.setText(filter_zh[i]);
                    checkBox.setFont(zh_font);
                    ++i;
                }
                addEventButton.setText(zh_text[1]);
                addEventButton.setFont(zh_font);
                resetButton.setText(zh_text[2]);
                resetButton.setFont(zh_font);
                setDropDown.removeAllItems();
                setDropDown = new JComboBox(sort_option_zh);
                setDropDown.setFont(zh_font);
            }

            // if english is selected
            else {
                language_flag = "en";

                int i = 0;
                for (JCheckBox checkBox : filterDisplay) {
                    checkBox.setText(filter_en[i]);
                    checkBox.setFont(std_font);
                    ++i;
                }

                addEventButton.setText(en_text[1]);
                addEventButton.setFont(std_font);
                resetButton.setText(en_text[2]);
                resetButton.setFont(std_font);
                setDropDown.removeAllItems();
                for (String sort : sort_options_en) {
                    setDropDown.addItem(sort);
                    setDropDown.setFont(std_font);
                }

            }
        });

        // remove all listed events
        resetButton.addActionListener(e -> {
            displayPanel.removeAll();
            displayPanel.revalidate();
            displayPanel.repaint();
        });

        setDropDown.addActionListener(e -> {
            if (language_flag.equals("en")) { // if english is selected (it is by default)
                if (setDropDown.getSelectedItem().equals(sort_options_en[0])) {
                    Collections.sort(events);
                }
            } else if (language_flag.equals("zh")) {
                if (setDropDown.getSelectedItem().equals(sort_option_zh[0])) {
                    Collections.sort(events);
                }
            }

        });
    }


    /*
        0 = filter out finished tasks
        1 = '       '  meetings
        2 = '       '  deadlines
     */
    private void filterOut() {
        int filter_i = 0;
        int event_i = 0;

        // ok, this is a terrible, horrible, horrific way to implement this,
        // but, in my defense, I was 'eepy!
            for (JCheckBox checkBox : filterDisplay) {
                for (Event event : events) {
                // check if meeting or deadline is completed and remove
                if (checkBox.isSelected() && filter_i == 0) {
                    if (event instanceof Meeting) {
                        Meeting meeting = (Meeting) event;
                        if (meeting.isComplete()) {
                            displayPanel.remove(event_i); // needs graveyard implementation to make filtered items reappear
                        }
                    } else if (event instanceof DeadLine) {
                        DeadLine deadline = (DeadLine) event;
                        if (deadline.isComplete()) {
                            displayPanel.remove(event_i);
                        }
                    }
                }

            // if removing meetings
            else if (checkBox.isSelected() && filter_i == 1) {
                if (event instanceof DeadLine) {
                    displayPanel.remove(event_i);
                }
            }

            // if removing deadlines
            else if (checkBox.isSelected() && filter_i == 2) {
                if (event instanceof DeadLine) {
                    displayPanel.remove(event_i);
                }
            }
            ++event_i;
        }
        event_i = 0;
        ++filter_i;
    }
        displayPanel.revalidate();
        displayPanel.repaint();
    }
}