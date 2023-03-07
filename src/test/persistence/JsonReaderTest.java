package persistence;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
public class JsonReaderTest extends JsonTest{


    @Test
    public void testReaderNotExistFile() {
        JsonReader jsonReader = new JsonReader("./data/cannotFind.json");
        try {
            jsonReader.readItemsPurchased();
            fail("IO Exception was expected");
        } catch(IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyBoth() {
        JsonReader jsonReader = new JsonReader("./data/testReaderEmptyBoth.json");
        try {
            assertEquals(0, jsonReader.readThriftStore().getAllItems().size());
            assertEquals(0, jsonReader.readItemsPurchased().getItemsPurchased().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderEmptyPurchased() {
        JsonReader jsonReader = new JsonReader("./data/testReaderEmptyPurchased.json");
        try {
            assertEquals(0, jsonReader.readItemsPurchased().getItemsPurchased().size());
            assertEquals(2, jsonReader.readThriftStore().getAllItems().size());
            checkItem("Sneakers", 97.5, "Good", "Jake",
                    jsonReader.readThriftStore().getAllItems().get(0));
            checkItem("VintageHairClip", 34.2, "Good", "Jia",
                    jsonReader.readThriftStore().getAllItems().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderEmptyStore() {
        JsonReader jsonReader = new JsonReader("./data/testReaderEmptyStore.json");
        try {
            assertEquals(0, jsonReader.readThriftStore().getAllItems().size());
            assertEquals(2, jsonReader.readItemsPurchased().getItemsPurchased().size());
            checkItem("HighHeels", 11, "Acceptable", "Amy",
                    jsonReader.readItemsPurchased().getItemsPurchased().get(0));
            checkItem("GlownyJeans",134.12, "Good", "Wendy",
                    jsonReader.readItemsPurchased().getItemsPurchased().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneral() {
        JsonReader jsonReader = new JsonReader("./data/testReaderGeneral.json");
        try {
            assertEquals(1, jsonReader.readItemsPurchased().getItemsPurchased().size());
            assertEquals(2,jsonReader.readThriftStore().getAllItems().size());
            checkItem("Sunglasses", 19.9, "Acceptable", "Jay",
                    jsonReader.readItemsPurchased().getItemsPurchased().get(0));
            checkItem("TankTop", 12.78, "Good", "Jia",
                    jsonReader.readThriftStore().getAllItems().get(0));
            checkItem("Earrings", 7.99, "VeryGood", "Quan",
                    jsonReader.readThriftStore().getAllItems().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
