/*
    This panel contains the functionality to add events to the user's calendar
*/

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events;
    private JPanel controlPanel; // controls for event display
    private JPanel displayPanel; // EventListPanel's panel

    private JComboBox<String> setDropDown; // sorts events
    private JCheckBox filterDisplay;
    private JCheckBox language;
    private JButton addEventButton;

    private final String[] sort_options = {"Ascending", "Descending"};
    private final String[] en_text = {"filter on/off", "English", "Add Event"};
    private final String[] zh_text = {"过滤器", "中文", "天加事件"};
    private final Font std_font = new Font("Comic Sans MS", Font.BOLD, 20);
    private final Font zh_font = new Font("SimHei", Font.BOLD, 20);

    // constructor where everything happens
    public EventListPanel(ArrayList<Event> events) {
        // constants
        final int X_SIZE = 500;
        final int Y_SIZE = 500;

        setPreferredSize(new Dimension(X_SIZE, Y_SIZE));
        this.events = events;
        controlPanel = new JPanel();
        displayPanel = new JPanel();
        setDropDown = new JComboBox(sort_options);
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
        displayPanel.add(controlPanel);

        // add display panel to super panel
        super.add(displayPanel);

        // action listeners
        addEventButton.addActionListener(e -> {
            JDialog dialog;

            for (Event event : this.events) {
                System.out.println(event.getName());
                dialog = new AddEventModal(event);
            }
            // super.add(displayPanel);
        });

        language.addActionListener(e -> {
            // if Mandarin is selected
            if (language.isSelected()) {
                filterDisplay.setText(zh_text[0]);
                filterDisplay.setFont(zh_font);
                addEventButton.setText(zh_text[2]);
                addEventButton.setFont(zh_font);
            }
            // if english is selected
            else {
                filterDisplay.setText(en_text[0]);
                filterDisplay.setFont(std_font);
                addEventButton.setText(en_text[2]);
                addEventButton.setFont(std_font);
            }
        });
    }
}
