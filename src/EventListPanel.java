/*
    This panel contains the functionality to add events to the user's calendar
*/

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events;

    // Swing objects
    private JPanel controlPanel; // controls for event display
    private JPanel displayPanel; // EventListPanel's panel

    private JComboBox<String> setDropDown; // sorts events
    private JCheckBox filterDisplay;
    private JCheckBox language;
    private JButton addEventButton;

    // strings
    private final String[] sort_options_en = {"Ascending", "Descending"};
    private final String [] sort_option_zh = {"", ""};
    private final String[] en_text = {"filter on/off", "English", "Add Event"};
    private final String[] zh_text = {"过滤器", "中文", "天加事件"};
    private final Font std_font = new Font("Comic Sans MS", Font.BOLD, 20);
    private final Font zh_font = new Font("SimHei", Font.BOLD, 20);

    // constructor where everything happens
    public EventListPanel(ArrayList<Event> events) {
        // constants
        final Dimension CONTROL_DIM = new Dimension(200, 100);
        final Point CONTROL_LOCATION = new Point(10,300);

        // create objects
        this.events = events;

        controlPanel = new JPanel();
        controlPanel.setPreferredSize(CONTROL_DIM);
        controlPanel.setLocation(CONTROL_LOCATION);

        // the constructor within displayPanel will take care of configuration so this is ok...
        displayPanel = new EventPanel(this.events);

        setDropDown = new JComboBox(sort_options_en);
        setDropDown.setFont(std_font);

        filterDisplay = new JCheckBox(en_text[0]);
        filterDisplay.setFont(std_font);

        language = new JCheckBox(en_text[1] + "/" + zh_text[1]);
        language.setFont(zh_font);

        addEventButton = new JButton(en_text[2]);
        addEventButton.setFont(std_font);

        // add everything to panels
        controlPanel.add(addEventButton);
        controlPanel.add(setDropDown);
        controlPanel.add(filterDisplay);
        controlPanel.add(language);

        // add display/control panel to super panel
        super.add(controlPanel);
        super.add(displayPanel);
        // super.setLocation(CONTROL_LOCATION);

        // action listeners
        addEventButton.addActionListener(e -> {
            ArrayList<JDialog> modals = new ArrayList<>();
            EventPanel event_panel = new EventPanel(events);

            for (Event event : this.events) {
                modals.add(new AddEventModal(event));

                // call paintComponent()
                event_panel.revalidate();
                event_panel.repaint();
            }
        });

        language.addActionListener(e -> {
            // if Mandarin is selected
            if (language.isSelected()) {
                filterDisplay.setText(zh_text[0]);
                filterDisplay.setFont(zh_font);
                addEventButton.setText(zh_text[2]);
                addEventButton.setFont(zh_font);
                setDropDown = new JComboBox(sort_option_zh);
            }
            // if english is selected
            else {
                filterDisplay.setText(en_text[0]);
                filterDisplay.setFont(std_font);
                addEventButton.setText(en_text[2]);
                addEventButton.setFont(std_font);
                setDropDown = new JComboBox(sort_options_en);
            }
        });
    }
}
