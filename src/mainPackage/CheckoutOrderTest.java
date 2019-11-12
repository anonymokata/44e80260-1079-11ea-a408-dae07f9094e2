package mainPackage;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;

public class CheckoutOrderTest {
    @Test
    public void scanAnExistedItemReturnsTheItemTotalPriceBeforeTax(){
        //store the item
        Price price = new Price(getFormat(2));
        Item item = new Item("Bread", 10, price);
        Inventory inventory = new Inventory();
        inventory.storeItems(item);

        //checkout this item
        String name = "Bread";
        int quantity = 10;
        CheckoutOrder checkout = new CheckoutOrder(inventory);
        assertEquals(getFormat(20), checkout.scanItem(name, quantity));
    }

    private BigDecimal getFormat(int value){
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }
}