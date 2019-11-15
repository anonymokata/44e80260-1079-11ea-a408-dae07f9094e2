package mainPackage.mainPackageTests;

import mainPackage.FormatBigDecimal;
import mainPackage.Inventory;
import mainPackage.Item;
import org.junit.Test;
import static org.junit.Assert.*;

public class InventoryTest extends FormatBigDecimal {
    @Test
    public void storeAnItemReturnTheItemFromInventory(){

        Item item = new Item("Bread", false, getFormat(2),false);
        Inventory inventory = new Inventory();
        inventory.storeItems(item);
        assertTrue(inventory.getItemHashMap().containsKey("Bread"));
        assertEquals(getFormat(2), inventory.getItemHashMap().get(item.getItemName()).getItemPrice());
    }
}