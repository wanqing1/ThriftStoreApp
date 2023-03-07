package persistence;

import model.Item;
import model.ItemsPurchased;
import model.ThriftStore;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


//Represents a reader that reads purchased items and the store from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: construct reader to read from the given source
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads purchased items from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ItemsPurchased readItemsPurchased() throws IOException {
        String sourceInJson = readFile(source);
        JSONObject json = new JSONObject((sourceInJson));
        return parseItemsPurchased(json);
    }

    // EFFECTS: reads the store from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ThriftStore readThriftStore() throws IOException {
        String sourceInJson = readFile(source);
        JSONObject json = new JSONObject((sourceInJson));
        return parseThriftStore(json);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses purchased items from JSON object and returns it
    private ItemsPurchased parseItemsPurchased(JSONObject jsonObject) {
        ItemsPurchased itemsPurchased = new ItemsPurchased();
        addToMine(itemsPurchased, jsonObject);
        return itemsPurchased;
    }

    // EFFECTS: parses the store from JSON object and returns it
    private ThriftStore parseThriftStore(JSONObject jsonObject) {
        ThriftStore thriftStore = new ThriftStore();
        addToStore(thriftStore, jsonObject);
        return thriftStore;
    }

    // MODIFIES: itemsPurchased
    // EFFECTS: parses item from JSON object and adds it to the given purchased items
    private void addItemPurchased(ItemsPurchased itemsPurchased, JSONObject json) {
        String name = json.getString("name");
        double price = json.getDouble("price");
        String condition = json.getString("condition");
        String owner = json.getString("owner");
        Item i = new Item(name, price, condition, owner);
        itemsPurchased.putInMyItems(i);
    }

    // MODIFIES: itemsPurchased
    // EFFECTS: parses items from JSON object and adds them to the given purchased items
    public void addToMine(ItemsPurchased itemsPurchased, JSONObject jsonObject) {
        JSONArray jsonArrayBig = jsonObject.getJSONArray("mineAndStore");
        JSONObject jsonItemsPurchased = (JSONObject) jsonArrayBig.get(0);
        JSONArray jsonArrayItemsPurchased = jsonItemsPurchased.getJSONArray("itemsPurchased");
        for (Object j: jsonArrayItemsPurchased) {
            JSONObject nextItemPurchased = (JSONObject) j;
            addItemPurchased(itemsPurchased, nextItemPurchased);
        }
    }

    // MODIFIES: thriftStore
    // EFFECTS: parses item from JSON object and adds it to the given store
    public void addThriftStore(ThriftStore thriftStore, JSONObject json) {
        String name = json.getString("name");
        double price = json.getDouble("price");
        String condition = json.getString("condition");
        String owner = json.getString("owner");
        Item i = new Item(name, price, condition, owner);
        thriftStore.upload(i);
    }


    // MODIFIES: thriftStore
    // EFFECTS: parses items from JSON object and adds them to the given store
    public void addToStore(ThriftStore thriftStore, JSONObject jsonObject) {
        JSONArray jsonArrayBig = jsonObject.getJSONArray("mineAndStore");
        JSONObject jsonThriftStore = (JSONObject) jsonArrayBig.get(1);
        JSONArray jsonArrayStore = jsonThriftStore.getJSONArray("itemsInStore");
        for (Object j: jsonArrayStore) {
            JSONObject nextItemInStore = (JSONObject) j;
            addThriftStore(thriftStore, nextItemInStore);
        }
    }

}
