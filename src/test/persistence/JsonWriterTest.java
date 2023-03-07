package persistence;

import model.Item;
import model.ItemsPurchased;
import model.ThriftStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class JsonWriterTest extends JsonTest{
    Item dress;
    Item pants;
    Item scarf;
    Item sunglasses;
    ItemsPurchased itemsPurchased;
    ThriftStore thriftStore;

    @BeforeEach
    public void setup() {
        dress = new Item("PinkDress", 14.56, "Good", "Sandy");
        pants = new Item("NikePants", 10, "Good", "Amy");
        scarf = new Item("RedScarf", 8.2, "VeryGood", "Jia");
        sunglasses = new Item("Sunglasses", 19.9, "Acceptable", "Jay");
        itemsPurchased = new ItemsPurchased();
        thriftStore = new ThriftStore();
    }

    @Test
    public void testWriterInvalidFile() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/Purchased\0andStore.json");
            jsonWriter.open();
            fail("IO Exception was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyBoth() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/testWriterEmptyBoth");
            jsonWriter.open();
            jsonWriter.write(itemsPurchased, thriftStore);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testWriterEmptyBoth");
            assertEquals(0, jsonReader.readItemsPurchased().getItemsPurchased().size());
            assertEquals(0, jsonReader.readThriftStore().getAllItems().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterEmptyPurchased() {
        try {
            thriftStore.upload(dress);
            thriftStore.upload(pants);

            JsonWriter jsonWriter = new JsonWriter("./data/testWriterEmptyPurchased.json");
            jsonWriter.open();
            jsonWriter.write(itemsPurchased, thriftStore);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testWriterEmptyPurchased.json");
            assertEquals(0, jsonReader.readItemsPurchased().getItemsPurchased().size());
            assertEquals(2, jsonReader.readThriftStore().getAllItems().size());
            checkItem("PinkDress", 14.56, "Good", "Sandy",
                    jsonReader.readThriftStore().getAllItems().get(0));
            checkItem("NikePants", 10, "Good", "Amy",
                    jsonReader.readThriftStore().getAllItems().get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterEmptyStore() {
        try {
            itemsPurchased.putInMyItems(pants);
            itemsPurchased.putInMyItems(scarf);

            JsonWriter jsonWriter = new JsonWriter("./data/testWriterEmptyStore.json");
            jsonWriter.open();
            jsonWriter.write(itemsPurchased, thriftStore);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testWriterEmptyStore.json");
            assertEquals(0, jsonReader.readThriftStore().getAllItems().size());
            assertEquals(2, jsonReader.readItemsPurchased().getItemsPurchased().size());
            checkItem("NikePants", 10, "Good", "Amy",
                    jsonReader.readItemsPurchased().getItemsPurchased().get(0));
            checkItem("RedScarf", 8.2, "VeryGood", "Jia",
                    jsonReader.readItemsPurchased().getItemsPurchased().get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneral() {
        try {
            itemsPurchased.putInMyItems(dress);
            itemsPurchased.putInMyItems(sunglasses);
            thriftStore.upload(scarf);
            thriftStore.upload(pants);

            JsonWriter jsonWriter = new JsonWriter("./data/testWriterGeneral.json");
            jsonWriter.open();
            jsonWriter.write(itemsPurchased, thriftStore);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testWriterGeneral.json");
            assertEquals(2, jsonReader.readThriftStore().getAllItems().size());
            assertEquals(2, jsonReader.readItemsPurchased().getItemsPurchased().size());
            checkItem("PinkDress", 14.56, "Good", "Sandy",
                    jsonReader.readItemsPurchased().getItemsPurchased().get(0));
            checkItem("Sunglasses", 19.9, "Acceptable", "Jay",
                    jsonReader.readItemsPurchased().getItemsPurchased().get(1));
            checkItem("RedScarf", 8.2, "VeryGood", "Jia",
                    jsonReader.readThriftStore().getAllItems().get(0));
            checkItem("NikePants", 10, "Good", "Amy",
                    jsonReader.readThriftStore().getAllItems().get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
