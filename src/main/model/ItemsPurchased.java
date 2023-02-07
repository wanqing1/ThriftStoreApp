package model;

import java.util.ArrayList;

// Represents my account showing what items I have purchased from the store
public class ItemsPurchased {
    private ArrayList<Item> myItems;

    // EFFECTS: creates an ItemsPurchased to place items users have purchased
    public ItemsPurchased() {
        myItems = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: put the given item into items that users have purchased
    public void putInMyItems(Item item) {
        myItems.add(item);
    }


    public ArrayList<Item> getItemsPurchased() {
        return myItems;
    }


}
