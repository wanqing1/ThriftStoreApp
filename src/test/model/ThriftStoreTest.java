package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ThriftStoreTest {
    ThriftStore myStore;

    @BeforeEach
    public void setup() {
        myStore = new ThriftStore();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, myStore.getAllItems().size());
        assertEquals(new ArrayList<>(), myStore.getAllItems());
    }

    @Test
    public void testUploadOne() {
        Item item = new Item("ZaraDress", 21, "VeryGood", "Sandy");
        myStore.upload(item);
        assertEquals(1, myStore.getAllItems().size());
        assertEquals("ZaraDress", myStore.getAllItems().get(0).getName());
    }

    @Test
    public void testUploadMultiple() {
        assertEquals(new ArrayList<>(), myStore.getAllItems());
        Item scarf = new Item("Scarf", 6.25, "Good", "Ruby");
        Item hoodie = new Item("RootsHoodie", 10.99, "VeryGood", "Danielle");
        Item dress = new Item("GlownyDress", 46, "Acceptable", "Wanqing");
        myStore.upload(scarf);
        myStore.upload(hoodie);
        assertEquals(6.25, myStore.getAllItems().get(0).getPrice());
        assertEquals("Danielle", myStore.getAllItems().get(1).getOwner());
        myStore.upload(dress);
        assertTrue(myStore.getAllItems().contains(scarf) && myStore.getAllItems().contains(hoodie)
                && myStore.getAllItems().contains(dress));
    }

    @Test
    public void testRemoveOne() {
        Item jeans = new Item("OldNavyJeans", 19, "VeryGood", "Lesley");
        myStore.upload(jeans);
        assertEquals(1, myStore.getAllItems().size());
        myStore.remove(jeans);
        assertEquals(new ArrayList<>(), myStore.getAllItems());
    }

    @Test
    public void testRemoveMultiple() {
        Item hairClip = new Item("HairClip", 12, "Good", "Ruby");
        Item sweatshirt = new Item("NikeSweatshirt", 20.4, "VeryGood", "Ellen");
        myStore.upload(hairClip);
        myStore.upload(sweatshirt);
        myStore.remove(hairClip);
        assertFalse(myStore.getAllItems().contains(hairClip));
        assertEquals("NikeSweatshirt", myStore.getAllItems().get(0).getName());
    }

}
