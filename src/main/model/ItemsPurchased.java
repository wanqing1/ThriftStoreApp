package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents users' account showing what items users have purchased from the store
public class ItemsPurchased implements Writable {
    private ArrayList<Item> myItems;

    // EFFECTS: creates an ItemsPurchased to place items users have purchased
    public ItemsPurchased() {
        myItems = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: puts the given item into items that users have purchased; logs this event
    public void putInMyItems(Item item) {
        myItems.add(item);
        EventLog.getInstance().logEvent(new Event(item.getName() + " successfully purchased"));
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("itemsPurchased", itemsPurchasedToJson());
        return json;
    }

    private JSONArray itemsPurchasedToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Item t: myItems) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }
}
