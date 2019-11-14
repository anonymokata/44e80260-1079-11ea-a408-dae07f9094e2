package mainPackage;

import org.junit.Before;
import org.junit.Test;
import specialPricePacakage.BuyNGetMAtAPercentageInWeight;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CheckoutOrderTest extends formatBigDecimal {
    private Inventory inventory;
    private CheckoutOrder checkout;

    @Before
    public void setup() {
        inventory = new Inventory();
        inventory.storeItems(new Item("Bread", 10, getFormat(1.99)));
        inventory.storeItems(new Item("Pasta", 10, getFormat(3)));
        inventory.storeItems(new Item("Bacon", 10, getFormat(3)));
        inventory.storeItems(new Item("Avocado", 10, getFormat(1)));
        inventory.storeItems(new Item("Ground Beef", 10.00, getFormat(1.25)));

        checkout = new CheckoutOrder(inventory);
    }

    @Test
    public void scanAnExistedItemReturnsTheItemTotalPrice() { // regular price(without any special offer)
        String name = "Bread";
        int quantity = 10;

        Item scannedItem = checkout.scanItem(name);
        BigDecimal actual = inventory.getItemHashMap().get(name).calculatePrice(scannedItem, quantity);
        assertEquals(getFormat(19.90), getFormat(actual));

    }

    @Test
    public void scanningAnMarkdownItemReturnTheItemTotalPrice() { // markdown price

        String name = "Pasta";
        int quantity = 4;
        inventory.setMarkdown(name, getFormat(0.50)); // markdown $0.50

        Item scannedItem = checkout.scanItem(name);
        BigDecimal actual = getSpecialPrice(name).getMarkdown().calculatePrice(scannedItem, quantity);
        assertEquals(getFormat(10), getFormat(actual));

    }

    @Test
    public void scanningAnPackageDealReturnTheItemTotalPrice() { // package deal -> N for $M
        String name = "Bacon";

        inventory.setNForXDollarSpecial(name, 3, getFormat(7));// special --> 3 for $5
        Item scannedItem = checkout.scanItem(name);
        BigDecimal actual = getSpecialPrice(name).getnForXDollar().calculatePrice(scannedItem, 3);
        assertEquals(getFormat(7), getFormat(actual));
    }

    @Test
    public void specialOfferInQuantityReturnsTheItemTotalPrice() { // Buy N for M at X% off, with or without limitation
        String name = "Avocado";

        inventory.setBuyNGetMAtAPercentageInQuantity(name, 2, 1);//buy 2 get 1 for free
        Item scannedItem = checkout.scanItem(name);
        BigDecimal actual1 = getSpecialPrice(name).getQuantitySpecial().calculatePrice(scannedItem, 3);
        assertEquals(getFormat(2), getFormat(actual1));

        inventory.setBuyNGetMAtAPercentageInQuantity(name, 2, 1, 50.00);//buy 2 get 1 for 50% off
        BigDecimal actual2 = getSpecialPrice(name).getQuantitySpecial().calculatePrice(scannedItem, 3);
        assertEquals(getFormat(2.50), getFormat(actual2));

        inventory.setBuyNGetMAtAPercentageInQuantity(name, 2, 1, 50.00, 6); //buy 2 get 1 for 50% off with a limitation of 6
        BigDecimal actual3 = getSpecialPrice(name).getQuantitySpecial().calculatePrice(scannedItem, 7);
        assertEquals(getFormat(6), getFormat(actual3));

    }

    @Test
    public void specialOfferInWeightReturnsTheItemTotalPrice(){  // Buy N pounds get M pounds at X% off
        String name = "Ground Beef";

        inventory.setBuyNGetMAtAPercentageInWeight(name, 2, 1); // Buy 2 pounds get 1 pound for free
        Item scannedItem = checkout.scanItem(name);
        BigDecimal actual1 = getSpecialPrice(name).getWeightSpecial().calculatePrice(scannedItem, 5.50);
        assertEquals(getFormat(5.63), getFormat(actual1));

        inventory.setBuyNGetMAtAPercentageInWeight(name, 3, 1, 75.00);
        BigDecimal actual2 = getSpecialPrice(name).getWeightSpecial().calculatePrice(scannedItem, 5);
        assertEquals(getFormat(5.31), getFormat(actual2));

    }

    private SpecialPrice getSpecialPrice(String name) {
        return inventory.getItemHashMap().get(name).getSpecialPrice();
    }

}