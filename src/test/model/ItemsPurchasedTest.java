package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ItemsPurchasedTest {
    ItemsPurchased itemsPurchased;

    @BeforeEach
    public void setup() {
        itemsPurchased = new ItemsPurchased();
    }

    @Test
    public void testConstructor() {
        assertEquals(new ArrayList<>(), itemsPurchased.getItemsPurchased());
    }

    @Test
    public void testPutInMyItemsOne() {
        Item tankTop = new Item("BlueTankTop", 19.8, "Acceptable", "Wendy");
        itemsPurchased.putInMyItems(tankTop);
        assertEquals("BlueTankTop", itemsPurchased.getItemsPurchased().get(0).getName());
    }

    @Test
    public void testPutInMyItemsMultiple() {
        Item skirt = new Item("ThreetimesSkirt", 79, "Good", "Dora");
        Item sunglasses = new Item("GucciSunglasses", 104.76, "VeryGood", "Jia");
        itemsPurchased.putInMyItems(sunglasses);
        itemsPurchased.putInMyItems(skirt);
        assertTrue(itemsPurchased.getItemsPurchased().contains(skirt)
                && itemsPurchased.getItemsPurchased().contains(sunglasses));
        assertEquals("Good", itemsPurchased.getItemsPurchased().get(1).getCondition());
    }

}
