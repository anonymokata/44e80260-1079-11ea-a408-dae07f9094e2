package mainPackage;

import org.junit.Test;
import static org.junit.Assert.*;

public class InventoryTest extends formatBigDecimal{
    @Test
    public void storeAnItemReturnTheItemFromInventory(){

        Item item = new Item("Bread", false, getFormat(2),false);
        Inventory inventory = new Inventory();
        inventory.storeItems(item);
        assertTrue(inventory.getItemHashMap().containsKey("Bread"));
        assertEquals(getFormat(2), inventory.getItemHashMap().get(item.getItemName()).getItemPrice());
    }
}