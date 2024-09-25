/*
    This panel contains the functionality to add events to the user's calendar
*/

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events;

    // Swing objects
    private JPanel controlPanel; // controls for event display
    private JPanel displayPanel; // EventListPanel's panel
    private JPanel picPanel; // my own panel for secret means

    private JComboBox<String> setDropDown; // sorts events
    private JCheckBox filterDisplay;
    private JCheckBox language;
    private JButton addEventButton;

    // constants
    // strings
    private final String[] sort_options_en = {"Name (Ascending)", "Name (Descending)", "Date (Ascending)", "Date (Descending)"};
    private final String [] sort_option_zh = {"升序", "降序"};
    private final String[] en_text = {"filter on/off", "English", "Add Event"};
    private final String[] zh_text = {"过滤器", "中文", "天加事件"};

    // fonts
    private final Font std_font = new Font("Comic Sans MS", Font.BOLD, 20);
    private final Font zh_font = new Font("SimHei", Font.BOLD, 20);

    // color
    private final Color PERIWINKLE = new Color(142, 130, 254);

    // constructor where everything happens
    public EventListPanel(ArrayList<Event> events) {
        // constants
        final Dimension CONTROL_DIM = new Dimension(200, 250);
        final Point CONTROL_LOCATION = new Point(10,300);

        // create objects
        this.events = events;

        controlPanel = new JPanel();
        controlPanel.setPreferredSize(CONTROL_DIM);
        controlPanel.setLocation(CONTROL_LOCATION);

        // the constructor within displayPanel will take care of configuration so this is ok...
        displayPanel = new JPanel();

        setDropDown = new JComboBox(sort_options_en);
        setDropDown.setFont(std_font);

        filterDisplay = new JCheckBox(en_text[0]);
        filterDisplay.setFont(std_font);
        filterDisplay.setBackground(PERIWINKLE);

        language = new JCheckBox(en_text[1] + "/" + zh_text[1]);
        language.setFont(zh_font);
        language.setBackground(PERIWINKLE);

        addEventButton = new JButton(en_text[2]);
        addEventButton.setFont(std_font);

        // add uca logo hahahha
        JLabel uca_pic = new JLabel();

        try {
            BufferedImage uca_logo = ImageIO.read(new File("img/uca.png"));
            uca_pic = new JLabel(new ImageIcon(uca_logo));

        }
        catch (java.io.IOException e) { e.printStackTrace(); }

        picPanel = new JPanel();
        picPanel.setPreferredSize(new Dimension(400,120));
        picPanel.add(uca_pic);
        picPanel.setBackground(PERIWINKLE);

        // add options to control panel
        controlPanel.add(language);
        controlPanel.add(addEventButton);
        controlPanel.add(filterDisplay);
        controlPanel.add(setDropDown);

        // add display/control panel to super panel
        controlPanel.setBackground(PERIWINKLE);
        displayPanel.setBackground(PERIWINKLE);
        super.add(controlPanel);
        super.add(displayPanel);
        super.add(picPanel);
        super.setBackground(PERIWINKLE);

        // action listeners
        addEventButton.addActionListener(e -> {
            AddEventModal add_event = new AddEventModal();
            EventPanel event_panel = new EventPanel(this.events);


            /* for (Event event : this.events) {
                modals.add(new AddEventModal(event));

                // call paintComponent()
                event_panel.revalidate();
                event_panel.repaint();
            } */
            event_panel.updateUrgency();
            // displayPanel.add(event_panel);
        });

        language.addActionListener(e -> {
            // if Mandarin is selected
            if (language.isSelected()) {
                filterDisplay.setText(zh_text[0]);
                filterDisplay.setFont(zh_font);
                addEventButton.setText(zh_text[2]);
                addEventButton.setFont(zh_font);
                setDropDown.removeAllItems();
                setDropDown.addItem(sort_option_zh[0]);
                setDropDown.addItem(sort_option_zh[1]);
                setDropDown.setFont(zh_font);
            }
            // if english is selected
            else {
                filterDisplay.setText(en_text[0]);
                filterDisplay.setFont(std_font);
                addEventButton.setText(en_text[2]);
                addEventButton.setFont(std_font);
                setDropDown.removeAllItems();
                setDropDown.addItem(sort_options_en[0]);
                setDropDown.addItem(sort_options_en[1]);
                setDropDown.setFont(std_font);
            }
        });
    }
}
