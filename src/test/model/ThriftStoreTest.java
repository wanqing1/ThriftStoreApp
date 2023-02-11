package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ThriftStoreTest {
    ThriftStore myStore;
    Item scarf;
    Item hoodie;
    Item jeans;
    Item hairClip;
    Item sweatshirt;

    @BeforeEach
    public void setup() {
        myStore = new ThriftStore();
        scarf = new Item("Scarf", 6.25, "Good", "Ruby");
        hoodie = new Item("RootsHoodie", 10.99, "VeryGood", "Danielle");
        jeans = new Item("GlownyJeans", 46, "Acceptable", "Wanqing");
        hairClip = new Item("HairClip", 12, "Good", "Ruby");
        sweatshirt = new Item("NikeSweatshirt", 20.4, "VeryGood", "Ellen");
    }

    @Test
    public void testConstructor() {
        assertEquals(0, myStore.getAllItems().size());
        assertEquals(new ArrayList<>(), myStore.getAllItems());
    }

    @Test
    public void testUploadOne() {
        myStore.upload(hairClip);
        assertEquals(1, myStore.getAllItems().size());
        assertEquals("HairClip", myStore.getAllItems().get(0).getName());
    }

    @Test
    public void testUploadMultiple() {
        assertEquals(new ArrayList<>(), myStore.getAllItems());
        myStore.upload(scarf);
        myStore.upload(hoodie);
        assertEquals(6.25, myStore.getAllItems().get(0).getPrice());
        assertEquals("Danielle", myStore.getAllItems().get(1).getOwner());
        myStore.upload(jeans);
        assertTrue(myStore.getAllItems().contains(scarf) && myStore.getAllItems().contains(hoodie)
                && myStore.getAllItems().contains(jeans));
    }

    @Test
    public void testRemoveOne() {
        myStore.upload(jeans);
        assertEquals(1, myStore.getAllItems().size());
        myStore.remove(jeans);
        assertEquals(new ArrayList<>(), myStore.getAllItems());
    }

    @Test
    public void testRemoveMultiple() {
        myStore.upload(hairClip);
        myStore.upload(sweatshirt);
        myStore.remove(hairClip);
        assertFalse(myStore.getAllItems().contains(hairClip));
        assertEquals("NikeSweatshirt", myStore.getAllItems().get(0).getName());
    }

}
