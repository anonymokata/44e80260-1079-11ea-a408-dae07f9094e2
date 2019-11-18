package mainPackage.mainPackageTests;

import mainPackage.FormattingBigDecimal;
import mainPackage.Inventory;
import mainPackage.Item;
import org.junit.Test;
import static org.junit.Assert.*;

public class InventoryTest extends FormattingBigDecimal {
    @Test
    public void storeAnItemReturnTheItemFromInventory(){

        Item item = new Item("Bread", false, getFormat(2),false);
        Inventory inventory = new Inventory();
        inventory.storeItems(item);

        assertTrue(inventory.getItemInventory().containsKey("Bread"));
        assertEquals(getFormat(2), inventory.getItemInventory().get(item.getItemName()).getItemPrice());
    }
}