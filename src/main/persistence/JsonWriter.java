package persistence;

import model.ItemsPurchased;
import model.ThriftStore;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes purchased items and the store in JSON representation to file
public class JsonWriter {
    private PrintWriter writer;
    private String destination;
    private static final int TAB = 4;

    // EFFECTS: constructs a writer that writes to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // path is invalid or cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of purchased items and the store to file
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

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
