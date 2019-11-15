package mainPackage.mainPackageTests;

import mainPackage.FormatBigDecimal;
import mainPackage.Inventory;
import mainPackage.Item;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ItemTest extends FormatBigDecimal {
    private Inventory inventory;
    private Item i;
    private Item ii;

    @Before
    public void setup() {
        inventory = new Inventory();
        i = new Item("Bread", false, getFormat(1.99), false);
        inventory.storeItems(i);
        ii = new Item("Grape", true, getFormat(0.99), false);
        inventory.storeItems(ii);

    }

    @Test
    public void getItemsAndReturnItsTotalPrice() {
        assertEquals(getFormat(19.9), getFormat(i.calculatePrice(inventory.getItemHashMap().get("Bread"), 10)));
        assertEquals(getFormat(9.9), getFormat(i.calculatePrice(inventory.getItemHashMap().get("Grape"), 10)));
    }
}