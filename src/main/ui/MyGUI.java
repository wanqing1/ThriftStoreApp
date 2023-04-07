package ui;

import model.Event;
import model.EventLog;
import model.Item;
import model.ItemsPurchased;
import model.ThriftStore;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Represents a graphic user interface through which users can interact with the thrift store
public class MyGUI extends JFrame {
    private ThriftStore thriftStore;
    private ItemsPurchased itemsPurchased;
    private JPanel sideBar;
    private JPanel rightPanel;
    private JButton uploadButton;
    private JButton storeButton;
    private JButton mineButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton submitButton;
    private JLabel nameLabel;
    private JLabel priceLabel;
    private JLabel ownerLabel;
    private JLabel conditionLabel;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField ownerField;
    private JTextField conditionField;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/myItemsAndStore.json";


    // EFFECTS: creates a graphic user interface with a proper size and all functions activated;
    //          prints out all logged events when the application exits
    public MyGUI() {
        super("Wendy's Thrift Store");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                printLog(EventLog.getInstance());
            }
        });
        setSize(new Dimension(700, 500));
        setLayout(new BorderLayout());
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20));
        initWindow();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }


    private void printLog(EventLog eventLog) {
        for (Event event: EventLog.getInstance()) {
            System.out.println(event.toString() + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes components, left menu bar and right panel
    private void initWindow() {
        initComponents();
        addListenersForButtons();
        initSideBar();
        initRightPanel();
    }


    // MODIFIES: this
    // EFFECTS: creates all components for construction of menu bar, right panel and all functionalities
    private void initComponents() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        thriftStore = new ThriftStore();
        itemsPurchased = new ItemsPurchased();
        sideBar = new JPanel();
        rightPanel = new JPanel();
        nameLabel = new JLabel("Name: ");
        priceLabel = new JLabel("Price: ");
        ownerLabel = new JLabel("Owner: ");
        conditionLabel = new JLabel("Condition: ");
        nameField = new JTextField(10);
        priceField = new JTextField(10);
        ownerField = new JTextField(10);
        conditionField = new JTextField(10);
        uploadButton = new JButton("Upload");
        storeButton = new JButton("Store");
        mineButton = new JButton("Mine");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        submitButton = new JButton("Submit");
    }

    // EFFECTS: adds action listeners for all buttons
    private void addListenersForButtons() {
        uploadListener();
        storeListener();
        mineListener();
        saveListener();
        loadListener();
        submitListener();
    }

    // MODIFIES: this
    // EFFECTS: enables the user to enter corresponding information of an item to upload to store when clicking
    //          the Upload button
    private void uploadListener() {
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightPanel.removeAll();
                initRightPanel();
                updateRightPanel();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: displays the name, price, owner, condition and a buy option for the given item on the right panel;
    //          moves the item from the store to purchased items and show a notice message when clicking the Buy button
    private void displayAnItemWithBuyOption(Item i) {
        JLabel name = new JLabel(i.getName());
        JLabel price = new JLabel(Double.toString(i.getPrice()));
        JLabel owner = new JLabel(i.getOwner());
        JLabel condition = new JLabel(i.getCondition());

        JPanel itemsInStorePanel = new JPanel(new GridLayout(1, 5));
        itemsInStorePanel.add(name);
        itemsInStorePanel.add(price);
        itemsInStorePanel.add(owner);
        itemsInStorePanel.add(condition);

        JButton purchaseButton = new JButton("Buy");

        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemsPurchased.putInMyItems(i);
                itemsPurchased.removeFromStore(thriftStore);
                rightPanel.remove(itemsInStorePanel);
                JOptionPane.showMessageDialog(
                        rightPanel, "Successfully purchased " + i.getName() + " !");
                updateRightPanel();
            }
        });
        itemsInStorePanel.add(purchaseButton);
        rightPanel.add(itemsInStorePanel);
    }

    // MODIFIES: this
    // EFFECTS: enables the user to view all items in store with a buy option for each item when clicking
    //          the Store button
    private void storeListener() {
        storeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightPanel.removeAll();
                ArrayList<Item> itemsInStore = thriftStore.getAllItems();
                for (Item i : itemsInStore) {
                    displayAnItemWithBuyOption(i);
                }
                updateRightPanel();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: displays the name, price, owner and condition of the given item on the right panel
    private void displayAnItem(Item i) {
        JLabel name = new JLabel(i.getName());
        JLabel price = new JLabel(Double.toString(i.getPrice()));
        JLabel owner = new JLabel(i.getOwner());
        JLabel condition = new JLabel(i.getCondition());
        JPanel itemPanel = new JPanel(new GridLayout(1, 4));
        itemPanel.add(name);
        itemPanel.add(price);
        itemPanel.add(condition);
        itemPanel.add(owner);
        rightPanel.add(itemPanel);
    }

    // MODIFIES: this
    // EFFECTS: displays all purchased items on the right panel when clicking the Mine button
    private void mineListener() {
        mineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightPanel.removeAll();
                ArrayList<Item> myItems = itemsPurchased.getItemsPurchased();
                for (Item i : myItems) {
                    displayAnItem(i);
                }
                updateRightPanel();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: saves the state of application to file and show a notice message
    private void saveListener() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(itemsPurchased, thriftStore);
                    jsonWriter.close();
                    JOptionPane.showMessageDialog(rightPanel, "All saved!");
                } catch (FileNotFoundException exception) {
                    JOptionPane.showMessageDialog(rightPanel, "Unable to write to file: " + JSON_STORE);
                }
                updateRightPanel();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: loads the state of application from file and show a notice message
    private void loadListener() {
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    thriftStore = jsonReader.readThriftStore();
                    itemsPurchased = jsonReader.readItemsPurchased();
                    JOptionPane.showMessageDialog(
                            rightPanel, "All reloaded!");
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(rightPanel, "Unable to read from file: " + JSON_STORE);
                }
                updateRightPanel();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adds the item with entered name, price, owner and condition to the store and show a notice message;
    //          clears the fields for name, price, condition and owner
    private void submitListener() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                String condition = conditionField.getText();
                String owner = ownerField.getText();

                Item item = new Item(name, price, condition, owner);
                thriftStore.upload(item);

                JOptionPane.showMessageDialog(rightPanel, item.getName() + " uploaded!");

                nameField.setText("");
                priceField.setText("");
                conditionField.setText("");
                ownerField.setText("");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates a menu bar on the left, with Upload, Store, Mine, Save, Load buttons
    private void initSideBar() {
        Color myColor = new Color(164, 187, 222, 128);
        sideBar.setBackground(myColor);
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.add(Box.createRigidArea(new Dimension(0, 40)));
        sideBar.add(uploadButton);
        sideBar.add(Box.createRigidArea(new Dimension(0, 40)));
        sideBar.add(storeButton);
        sideBar.add(Box.createRigidArea(new Dimension(0, 40)));
        sideBar.add(mineButton);
        sideBar.add(Box.createRigidArea(new Dimension(0, 40)));
        sideBar.add(saveButton);
        sideBar.add(Box.createRigidArea(new Dimension(0, 40)));
        sideBar.add(loadButton);
        add(sideBar, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: creates a right panel and provides the users with spaces to enter information of an item
    private void initRightPanel() {
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(nameLabel);
        rightPanel.add(nameField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(priceLabel);
        rightPanel.add(priceField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(conditionLabel);
        rightPanel.add(conditionField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(ownerLabel);
        rightPanel.add(ownerField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        rightPanel.add(submitButton);
        add(rightPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: updates the layout and appearance of the right panel
    private void updateRightPanel() {
        rightPanel.revalidate();
        rightPanel.repaint();
    }
}