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


//...
public class JsonReader {
    private String source;

    //...
    public JsonReader(String source) {
        this.source = source;
    }

    //...改！
    public ItemsPurchased readItemsPurchased() throws IOException {
        String sourceInJson = readFile(source);
        JSONObject json = new JSONObject((sourceInJson));
        return parseItemsPurchased(json);
    }

    //...
    public ThriftStore readThriftStore() throws IOException {
        String sourceInJson = readFile(source);
        JSONObject json = new JSONObject((sourceInJson));
        return parseThriftStore(json);
    }

    //...
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //...
    private ItemsPurchased parseItemsPurchased(JSONObject jsonObject) {
        ItemsPurchased itemsPurchased = new ItemsPurchased();
        addToMine(itemsPurchased, jsonObject);
        return itemsPurchased;
    }

    //...
    private ThriftStore parseThriftStore(JSONObject jsonObject) {
        ThriftStore thriftStore = new ThriftStore();
        addToStore(thriftStore, jsonObject);
        return thriftStore;
    }

    private void addItemPurchased(ItemsPurchased purchased, JSONObject json) {
        String name = json.getString("name");
        double price = json.getDouble("price");
        String condition = json.getString("condition");
        String owner = json.getString("owner");
        Item i = new Item(name, price, condition, owner);
        purchased.putInMyItems(i);
    }


    public void addThriftStore(ThriftStore thriftStore, JSONObject json) {
        String name = json.getString("name");
        double price = json.getDouble("price");
        String condition = json.getString("condition");
        String owner = json.getString("owner");
        Item i = new Item(name, price, condition, owner);
        thriftStore.upload(i);
    }


    public void addToMine(ItemsPurchased itemsPurchased, JSONObject jsonObject) {
        JSONArray jsonArrayBig = jsonObject.getJSONArray("mineAndStore");
        JSONObject jsonItemsPurchased = (JSONObject) jsonArrayBig.get(0);
        JSONArray jsonArrayItemsPurchased = jsonItemsPurchased.getJSONArray("itemsPurchased");
        for (Object j: jsonArrayItemsPurchased) {
            JSONObject nextItemPurchased = (JSONObject) j;
            addItemPurchased(itemsPurchased, nextItemPurchased);
        }

    }

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
