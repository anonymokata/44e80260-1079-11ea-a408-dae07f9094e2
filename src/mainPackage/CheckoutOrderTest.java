package mainPackage;

import org.junit.Test;
import specialPricePacakage.Markdown;
import specialPricePacakage.NForXDollar;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;

public class CheckoutOrderTest {
    @Test
    public void scanAnExistedItemReturnsTheItemTotalPrice(){
        //store the item
        Price price = new Price(getFormat(2));
        Item item = new Item("Bread", 10, price);
        Inventory inventory = new Inventory();
        inventory.storeItems(item);

        //checkout this item
        String name = "Bread";
        int quantity = 10;
        CheckoutOrder checkout = new CheckoutOrder(inventory);
        Item scannedItem = checkout.scanItem(name);
        assertEquals(getFormat(20), price.getItemRegularTotalPrice(scannedItem, quantity));

    }

    @Test
    public void scanningAnMarkdownItemReturnTheItemTotalPrice(){
        Price price = new Price(getFormat(3));
        Item item = new Item("Pasta", 10, price);
        item.getPrice().setMarkdown(getFormat(1));
        Inventory inventory = new Inventory();
        inventory.storeItems(item);

        String name = "Pasta";
        int quantity = 4;
        CheckoutOrder checkout = new CheckoutOrder(inventory);
        Item scannedItem = checkout.scanItem(name);
        assertEquals(getFormat(8.00), scannedItem.getPrice().getItemTotalPriceAfterMarkdown(scannedItem, quantity));

    }

    @Test
    public void scanningAnPackageDealReturnTheItemTotalPrice(){
        Price price = new Price(getFormat(3));
        Item item = new Item("Pasta", 10, price);
        Inventory inventory = new Inventory();
        inventory.storeItems(item);

        //2 for $5

        String name = "Pasta";
        int quantity = 7;
        item.getPrice().setnForXDollar(new NForXDollar(getFormat(5), 2));

        CheckoutOrder checkout = new CheckoutOrder(inventory);
        Item scannedItem = checkout.scanItem(name);
        assertEquals(getFormat(18), scannedItem.getPrice().getnForXDollar().calculateThisItemTotalPrice(scannedItem, quantity));

    }


    
    private BigDecimal getFormat(int value){
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getFormat(double value){
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }
}