package mainPackage;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;

public class InventoryTest {
    @Test
    public void initializeAnItemReturnTheItemFromInventory(){
        Price price = new Price(getFormat(2));
        Item item = new Item("Bread", 10, price);
        Inventory inventory = new Inventory();
        inventory.storeItems(item);
        assertTrue(inventory.getItems().containsKey("Bread"));
        assertEquals(getFormat(2), inventory.getItems().get(item.getItemName()).getPrice().getRegularPrice());
    }


    private BigDecimal getFormat(int value){
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }
}