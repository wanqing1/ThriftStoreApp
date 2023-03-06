package persistence;

import model.Item;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkItem(String name, double price, String condition, String owner, Item i) {
        assertEquals(name, i.getName());
        assertEquals(price, i.getPrice());
        assertEquals(condition, i.getCondition());
        assertEquals(owner, i.getOwner());
    }
}
