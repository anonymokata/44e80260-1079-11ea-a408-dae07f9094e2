package mainPackage;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CheckoutOrderTest extends formatBigDecimal {
    private Inventory inventory;
    private CheckoutOrder checkout;

    @Before
    public void setup() {
        inventory = new Inventory();
        inventory.storeItems(new Item("Bread", false, getFormat(1.99), false));
        inventory.storeItems(new Item("Pasta", false, getFormat(3), false));
        inventory.storeItems(new Item("Bacon", false, getFormat(3), false));
        inventory.storeItems(new Item("Avocado", false, getFormat(1), false));
        inventory.storeItems(new Item("Ground Beef", true, getFormat(1.50), false));
        inventory.storeItems(new Item("Organic Chicken", true, getFormat(3.59), false));
        inventory.storeItems(new Item("Small Green Onion", false, getFormat(0.59), false));
        inventory.storeItems(new Item("Broccoli", true, getFormat(0.99), false));
        inventory.storeItems(new Item("Apple", true, getFormat(0.69), false));
        inventory.storeItems(new Item("Ice Cream", false, getFormat(1.99), false));
        inventory.storeItems(new Item("Grape", true, getFormat(0.99), false));

        checkout = new CheckoutOrder(inventory);
    }

    @Test
    public void scanItemReturnsTheUpdatedTotalPrice() { // regular price(without any special offer)
        checkout.scanItem("Bread", 3); //test result in quantity
        checkout.scanItem("Ground Beef", 2.00);

        BigDecimal actual1 = checkout.getTotalPrice();
        assertEquals(getFormat(8.97), getFormat(actual1));

//        checkout.removeItem("Bread", 1);//remove 1 bag of bread
//        BigDecimal actual3 = checkout.getTotalPrice();
//        assertEquals(getFormat(6.98), getFormat(actual3));
//
//        checkout.removeItem("Ice Cream", false); //remove item that is not in the cart
//        BigDecimal actual4 = checkout.getTotalPrice();
//        assertEquals(getFormat(6.98, getFormat(actual4));

//        checkout.removeItem("Bread", true); //remove all bread
//        BigDecimal actual5 = checkout.getTotalPrice();
//        assertEquals(getFormat(2.00), getFormat(actual5));

    }

    @Test
    public void scanMarkdownItemReturnTheUpdatedTotalPrice() { // markdown price
        inventory.setMarkdown("Ground Beef", getFormat(0.50), true); // markdown $0.50

        checkout.scanItem("Ground Beef", 2.50); //test single markdown price total
        BigDecimal actual = checkout.getTotalPrice();
        assertEquals(getFormat(2.50), getFormat(actual));

        checkout.scanItem("Bread", 1); //test total price of all scanned items with markdown item in it
        BigDecimal actualX = checkout.getTotalPrice();
        assertEquals(getFormat(4.49), getFormat(actualX));
    }

    @Test
    public void scanningAnPackageDealReturnTheItemTotalPrice() { // package deal -> N for $M
        inventory.setNForXDollarSpecial("Avocado", 4, getFormat(3.00), true); // 4 for $3

        checkout.scanItem("Avocado", 5);
        BigDecimal actual = checkout.getTotalPrice();
        assertEquals(getFormat(4), getFormat(actual));

        checkout.scanItem("Bread", 1); //test total price of all scanned items with N for $N item in it
        BigDecimal actualX = checkout.getTotalPrice();
        assertEquals(getFormat(5.99), getFormat(actualX));

    }

    @Test
    public void specialOfferReturnsTheItemTotalPriceInQuantity() { // Buy N for M at X% off, with or without limitation
        String name1 = "Avocado";
        String name2 = "Apple";
        inventory.setBuyNGetMAtAPercentage(name1, 2, 1, true);//buy 2 get 1 for free

        checkout.scanItem(name1, 6);
        BigDecimal actual1 = checkout.getTotalPrice();
        assertEquals(getFormat(4), getFormat(actual1));

        inventory.setBuyNGetMAtAPercentage(name1, 2, 1, 50.00, true);//buy 2 get 1 for 50% off
        BigDecimal actual2 = checkout.getTotalPrice();
        assertEquals(getFormat(5), getFormat(actual2));

        inventory.setBuyNGetMAtAPercentage(name1, 1, 1, 50.00, 4, true); //buy 1 get 1 for 50% off with a limitation of 4
        BigDecimal actual3 = checkout.getTotalPrice();
        assertEquals(getFormat(5), getFormat(actual3));

        checkout.scanItem(name2, 5.00); // scan more item to test the updated total price
        BigDecimal actual4 = checkout.getTotalPrice();
        assertEquals(getFormat(8.45), getFormat(actual4));

    }

//    @Test
//    public void specialOfferReturnsTheItemTotalPriceInWeight(){  // Buy N pounds get M pounds at X% off
//        String name = "Ground Beef";
//
//        inventory.setBuyNGetMAtAPercentage(name, 2, 1, true); // Buy 2 pounds get 1 pound for free
//        Item scannedItem = checkout.scanItem(name);
//        BigDecimal actual1 = getSpecialPrice(name).getWeightSpecial().calculatePrice(scannedItem, 5.50);
//        assertEquals(getFormat(5.63), getFormat(actual1));
//
//        inventory.setBuyNGetMAtAPercentageInWeight(name, 3, 1, 75.00, true);
//        BigDecimal actual2 = getSpecialPrice(name).getWeightSpecial().calculatePrice(scannedItem, 5);
//        assertEquals(getFormat(5.31), getFormat(actual2));
//
//    }
//
//    private SpecialPrice getSpecialPrice(String name) {
//        return inventory.getItemHashMap().get(name).getSpecialPrice();
//    }

}