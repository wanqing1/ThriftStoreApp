package ui;

import model.Item;
import model.ItemsPurchased;
import model.ThriftStore;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


// Represents a thrift store application
public class ThriftStoreApp {
    private ThriftStore thriftStore;
    private ItemsPurchased itemsPurchased;
    private Scanner scanner;
    private String instruction;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/myItemsAndStore.json";


    // EFFECTS: runs the thrift store app
    public ThriftStoreApp() throws FileNotFoundException {
        goThriftStore();
    }

    // MODIFIES: this
    // EFFECTS: interacts with users and processes their instructions
    private void goThriftStore() {
        thriftStore = new ThriftStore();
        itemsPurchased = new ItemsPurchased();
        scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        System.out.println("\nWelcome to Wendy's Thrift Store!");

        boolean ifOperating = true;
        while (ifOperating) {
            menuOptions();
            instruction = scanner.next();
            if (instruction.equals("upload") || instruction.equals("store")
                    || instruction.equals("buy") || instruction.equals("mine")
                    || instruction.equals("save") || instruction.equals("load")) {
                followInstructions(instruction);
            } else {
                ifOperating = false;
            }
        }
        System.out.println("\nThank you! Have a nice day!");
    }

    // EFFECTS: provides users with options to continue
    private void menuOptions() {
        System.out.println("\nEnter one of the following:");
        System.out.println("upload  <-  to upload an item to be sold");
        System.out.println("store   <-  to view everything in the store");
        System.out.println("buy     <-  to purchase an item on sale");
        System.out.println("mine    <-  to view items purchased");
        System.out.println("save    <-  to save purchased items and items in store to file");
        System.out.println("load    <-  to load purchased items and items in store from file");
        System.out.println("quit    <-  to leave the store");
    }

    //需要加!!
    // MODIFIES: this
    // EFFECTS: follows the command given by users
    private void followInstructions(String command) {
        if (command.equals("upload")) {
            readAndUpload();
        } else if (command.equals("store")) {
            printItemsInStore();
        } else if (command.equals("mine")) {
            printItemsPurchased();
        } else if (command.equals("buy")) {
            purchaseAnItem();
        } else if (command.equals("save")) {
            saveItems();
        } else if (command.equals("load")) {
            loadItems();
        } else {
            System.out.println("Invalid instruction...");
        }
    }

    // REQUIRES: price > 0
    // MODIFIES: this
    // EFFECTS: creates an item based on information entered by users and uploads it to the store
    public void readAndUpload() {
        System.out.println("What's the product name?");
        String name = scanner.next();
        System.out.println("At what price would you like to sell it? (no dollar sign)");
        double price = scanner.nextDouble();
        System.out.println("What's the condition of the item? VeryGood/Good/Acceptable...");
        String condition = scanner.next();
        System.out.println("Whom is the item owned by?");
        String owner = scanner.next();

        Item item = new Item(name, price, condition, owner);
        thriftStore.upload(item);
    }

    // EFFECTS: prints out all items in store, with items' name, price, condition and owner
    public void printItemsInStore() {
        ArrayList<Item> allItems = thriftStore.getAllItems();
        for (Item item : allItems) {
            System.out.println(item.getName() + ", $" + item.getPrice() + ", "
                    + item.getCondition() + ", " + item.getOwner());
        }
    }

    // EFFECTS: prints out all items that users have purchased, with items' name, price, condition and owner
    public void printItemsPurchased() {
        ArrayList<Item> itemsPurchased1 = itemsPurchased.getItemsPurchased();
        for (Item item : itemsPurchased1) {
            System.out.println(item.getName() + ", $" + item.getPrice() + ", "
                    + item.getCondition() + ", " + item.getOwner());
        }
    }

    // MODIFIES: this
    // EFFECTS: If there are items in the store, proceed the purchase;
    //          If users purchase an item, remove it from the store.
    public void purchaseAnItem() {
        if (thriftStore.getAllItems().size() == 0) {
            System.out.println("There are no items in the store!");
        } else {
            System.out.println("\nChoose one item from the store by entering its name:");
            printItemsInStore();
            confirmIfPurchase();
            itemsPurchased.removeFromStore(thriftStore);
        }
    }

    // MODIFIES: this
    // EFFECTS:  If users confirm to purchase, put the item into the items purchased;
    //           Otherwise, cancel the purchase.
    public void confirmIfPurchase() {
        String itemWanted = scanner.next();
        ArrayList<Item> allItems = thriftStore.getAllItems();
        for (Item item : allItems) {
            if (itemWanted.equals(item.getName())) {
                System.out.println("\nConfirm to purchase " + item.getName() + " at $"
                        + item.getPrice() + "? Enter yes OR no");
                if (scanner.next().equals("yes")) {
                    itemsPurchased.putInMyItems(item);
                    System.out.println("Successfully purchased " + item.getName() + "!");
                } else {
                    System.out.println("Purchase cancelled.");
                }
            }
        }
    }

    //...
    private void saveItems() {
        try {
            jsonWriter.open();
            jsonWriter.write(itemsPurchased, thriftStore);
            jsonWriter.close();
            System.out.println("Saved purchased items and items in store to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //...
    private void loadItems() {
        try {
            thriftStore = jsonReader.readThriftStore();
            itemsPurchased = jsonReader.readItemsPurchased();
            System.out.println("Loaded purchased items and my items from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Sorry, unable to read from file: " + JSON_STORE);
        }
    }

}
