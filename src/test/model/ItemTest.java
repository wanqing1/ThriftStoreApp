package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    Item pants;
    Item gloves;

    @BeforeEach
    public void setup() {
        pants = new Item("NikePants", 15.99, "Good", "Jerry");
        gloves = new Item("VintageGloves", 37, "VeryGood", "Jasmine");
    }

    @Test
    public void testConstructor() {
        assertEquals("NikePants", pants.getName());
        assertEquals(15.99, pants.getPrice());
        assertFalse(gloves.getCondition().equals( "Acceptable"));
        assertTrue(pants.getName().equals("NikePants"));
    }


}