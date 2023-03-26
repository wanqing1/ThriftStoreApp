package ui;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


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


    public MyGUI() {
        super("Wendy's Thrift Store");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(700, 500));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(18, 18, 18, 18));
        initWindow();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void initWindow() {
        initComponents();
        initSideBar();
        initRightPanel();
    }

    private void initComponents() {
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

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        initAllButtons();
    }

    public void initAllButtons() {
        uploadButton = new JButton("Upload");
        uploadListener();

        storeButton = new JButton("Store");
        storeListener();

        mineButton = new JButton("Mine");
        mineListener();

        saveButton = new JButton("Save");
        saveListener();

        loadButton = new JButton("Load");
        loadListener();

        submitButton = new JButton("Submit");
        submitListener();
    }

    public void uploadListener() {
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightPanel.removeAll();
                initRightPanel();
                updateRightPanel();
            }
        });
    }

    public void displayAnItemWithBuyOption(Item i) {
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

    public void storeListener() {
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

    public void displayAnItem(Item i) {
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

    public void mineListener() {
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

    public void saveListener() {
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
            }
        });
    }

    public void loadListener() {
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    thriftStore = jsonReader.readThriftStore();
                    itemsPurchased = jsonReader.readItemsPurchased();
                    JOptionPane.showMessageDialog(
                            rightPanel, "Loaded store and purchased items successfully.");
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(rightPanel, "Unable to read from file: " + JSON_STORE);
                }
            }
        });
    }

    public void submitListener() {
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

    public void initRightPanel() {
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

    public void updateRightPanel() {
        rightPanel.revalidate();
        rightPanel.repaint();
    }
}
