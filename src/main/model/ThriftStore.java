package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a thrift store where users upload and purchase second-hand products.
public class ThriftStore implements Writable {
    private ArrayList<Item> store;

    // EFFECTS: creates a store
    public ThriftStore() {
        store = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given item into the store
    public void upload(Item item) {
        this.store.add(item);
    }

    // MODIFIES: this
    // EFFECTS: removes the given item from the store
    public void remove(Item item) {
        this.store.remove(item);
    }

    public ArrayList<Item> getAllItems() {
        return store;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("itemsInStore", itemsInStoreToJson());
        return json;
    }

    private JSONArray itemsInStoreToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Item t: store) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }
}
