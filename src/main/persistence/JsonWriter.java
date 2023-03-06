package persistence;

import model.ItemsPurchased;
import model.ThriftStore;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// ...
public class JsonWriter {
    private PrintWriter writer;
    private String destination;
    private static final int TAB = 4;

    // EFFECTS: constructs a writer that writes to the destination
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //...
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    public void write(ItemsPurchased itemsPurchased, ThriftStore thriftStore) {
        JSONObject json1 = itemsPurchased.toJson();
        JSONObject json2 = thriftStore.toJson();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(json1);
        jsonArray.put(json2);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mineAndStore", jsonArray);
        saveToFile(jsonObject.toString(TAB));
    }

    //...
    public void close() {
        writer.close();
    }

    //...
    private void saveToFile(String json) {
        writer.print(json);
    }

}
