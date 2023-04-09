package persistence;

import org.json.JSONObject;

// Represents anything that can be turned into a JSONObject
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
