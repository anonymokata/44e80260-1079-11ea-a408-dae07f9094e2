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
        checkout.scanItem("Avocado", 5);    //test total price when scanning an package deal item
        BigDecimal actual = checkout.getTotalPrice();
        assertEquals(getFormat(4), getFormat(actual));

        checkout.scanItem("Bread", 1); //test total price of all scanned items mixed with non special offer items
        BigDecimal actualX = checkout.getTotalPrice();
        assertEquals(getFormat(5.99), getFormat(actualX));

        inventory.setNForXDollarSpecial("Ice Cream", 6, getFormat(10), 6, true);//test total price of all scanned item mixed with package deal with limitation
        checkout.scanItem("Ice Cream", 7);
        BigDecimal actualY = checkout.getTotalPrice();
        assertEquals(getFormat(17.98), getFormat(actualY));



    }

    @Test
    public void specialOfferReturnsTheItemTotalPrice() { // Buy N for M at X% off, with or without limitation
        String name1 = "Avocado";
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

        String name2 = "Ground Beef";
        inventory.setBuyNGetMAtAPercentage(name2, 2.00, 1.00, 50.00, 6, true); //buy 2 pounds get 1 pound for half off with a limitation of 6

        checkout.scanItem(name2, 7.00);
        BigDecimal actual4 = checkout.getTotalPrice();
        assertEquals(getFormat(14), getFormat(actual4));

        String name3 = "Apple";
        checkout.scanItem(name3, 5.00); // scan more item to test the updated total price
        BigDecimal actual5 = checkout.getTotalPrice();
        assertEquals(getFormat(17.45), getFormat(actual5));
    }

}