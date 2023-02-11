package model;

import java.util.ArrayList;

// Represents users' account showing what items users have purchased from the store
public class ItemsPurchased {
    private ArrayList<Item> myItems;

    // EFFECTS: creates an ItemsPurchased to place items users have purchased
    public ItemsPurchased() {
        myItems = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: puts the given item into items that users have purchased
    public void putInMyItems(Item item) {
        myItems.add(item);
    }

    // MODIFIES: this
    // EFFECTS: removes all the items from the store that have already appeared in the items purchased
    public void removeFromStore(ThriftStore thriftStore) {
        for (Item i : myItems) {
            if (thriftStore.getAllItems().contains(i)) {
                thriftStore.remove(i);
            }
        }
    }

    public ArrayList<Item> getItemsPurchased() {
        return myItems;
    }
}
