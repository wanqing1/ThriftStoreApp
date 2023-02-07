package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    Item i;

    @BeforeEach
    public void setup() {
        i = new Item("NikePants", 15.99, "Good", "Jerry");
    }

    @Test
    public void testConstructor() {
        assertEquals("NikePants", i.getName());
        assertEquals(15.99, i.getPrice());
        assertEquals("Good", i.getCondition());
        assertEquals("Jerry", i.getOwner());
    }

}