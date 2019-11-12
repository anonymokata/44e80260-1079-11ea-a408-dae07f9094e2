package mainPackage;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;

public class InventoryTest {
    @Test
    public void storeAnItemReturnTheItemFromInventory(){
        Price price = new Price(getFormat(2));
        Item item = new Item("Bread", 10, price);
        Inventory inventory = new Inventory();
        inventory.storeItems(item);
        assertTrue(inventory.getItemHashMap().containsKey("Bread"));
        assertEquals(getFormat(2), inventory.getItemHashMap().get(item.getItemName()).getPrice().getRegularPrice());
    }


    private BigDecimal getFormat(int value){
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }
}