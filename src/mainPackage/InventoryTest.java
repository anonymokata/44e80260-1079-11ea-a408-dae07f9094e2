package mainPackage;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;

public class InventoryTest extends formatBigDecimal{
    @Test
    public void storeAnItemReturnTheItemFromInventory(){

        Item item = new Item("Bread", 10, false, getFormat(2));
        Inventory inventory = new Inventory();
        inventory.storeItems(item);
        assertTrue(inventory.getItemHashMap().containsKey("Bread"));
        assertEquals(getFormat(2), inventory.getItemHashMap().get(item.getItemName()).getItemPrice());
    }
}