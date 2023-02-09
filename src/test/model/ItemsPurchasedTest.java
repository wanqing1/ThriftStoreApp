package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ItemsPurchasedTest {
    ItemsPurchased itemsPurchased;
    ThriftStore thriftStore;
    Item tankTop;
    Item skirt;
    Item sunglasses;

    @BeforeEach
    public void setup() {
        itemsPurchased = new ItemsPurchased();
        thriftStore = new ThriftStore();
        tankTop = new Item("BlueTankTop", 19.8, "Acceptable", "Wendy");
        skirt = new Item("ThreetimesSkirt", 79, "Good", "Dora");
        sunglasses = new Item("GucciSunglasses", 104.76, "VeryGood", "Jia");
    }

    @Test
    public void testConstructor() {
        assertEquals(new ArrayList<>(), itemsPurchased.getItemsPurchased());
    }

    @Test
    public void testPutInMyItemsOne() {
        itemsPurchased.putInMyItems(tankTop);
        assertEquals("BlueTankTop", itemsPurchased.getItemsPurchased().get(0).getName());
    }

    @Test
    public void testPutInMyItemsMultiple() {
        itemsPurchased.putInMyItems(sunglasses);
        itemsPurchased.putInMyItems(skirt);
        assertTrue(itemsPurchased.getItemsPurchased().contains(skirt)
                && itemsPurchased.getItemsPurchased().contains(sunglasses));
        assertEquals("Good", itemsPurchased.getItemsPurchased().get(1).getCondition());
    }

    @Test
    public void testRemoveFromStoreSingle() {
        thriftStore.upload(skirt);
        itemsPurchased.putInMyItems(skirt);
        itemsPurchased.removeFromStore(thriftStore);
        assertEquals(new ArrayList<>(), thriftStore.getAllItems());
    }

    @Test
    public void testRemoveFromStoreSame() {
        thriftStore.upload(tankTop);
        thriftStore.upload(tankTop);
        itemsPurchased.putInMyItems(tankTop);
        itemsPurchased.removeFromStore(thriftStore);
        assertEquals("BlueTankTop", thriftStore.getAllItems().get(0).getName());
    }

    @Test
    public void testRemoveFromStoreDifferent() {
        thriftStore.upload(sunglasses);
        thriftStore.upload(skirt);
        itemsPurchased.putInMyItems(sunglasses);
        itemsPurchased.removeFromStore(thriftStore);
        assertEquals("ThreetimesSkirt", thriftStore.getAllItems().get(0).getName());
        itemsPurchased.putInMyItems(tankTop);
        itemsPurchased.removeFromStore(thriftStore);
        assertEquals(1, thriftStore.getAllItems().size());
    }

}
