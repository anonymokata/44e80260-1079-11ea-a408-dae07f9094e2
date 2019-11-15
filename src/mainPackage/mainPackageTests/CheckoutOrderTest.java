package mainPackage.mainPackageTests;

import mainPackage.CheckoutOrder;
import mainPackage.FormatBigDecimal;
import mainPackage.Inventory;
import mainPackage.Item;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;

public class CheckoutOrderTest extends FormatBigDecimal {
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
    public void scanItemReturnsTheCheckoutTotalPrice() { // regular price(without any special offer)
        checkout.scanItem("Bread", 3); //test result in quantity
        checkout.scanItem("Ground Beef", 2.00);

        BigDecimal actual1 = checkout.getTotalPrice();
        assertEquals(getFormat(8.97), getFormat(actual1));
    }

    @Test
    public void scanMarkdownItemReturnTheCheckoutTotalPrice() { // markdown price
        inventory.setMarkdown("Ground Beef", getFormat(0.50), true); // markdown $0.50

        checkout.scanItem("Ground Beef", 2.50); //test single markdown price total
        BigDecimal actual = checkout.getTotalPrice();
        assertEquals(getFormat(2.50), getFormat(actual));

        checkout.scanItem("Bread", 1); //test total price of all scanned items with markdown item in it
        BigDecimal actualX = checkout.getTotalPrice();
        assertEquals(getFormat(4.49), getFormat(actualX));
    }

    @Test
    public void scanningAnPackageDealReturnTheCheckoutTotalPrice() { // package deal -> N for $M
        inventory.setNForXDollarSpecial("Avocado", 4, getFormat(3.00), true); // 4 for $3
        checkout.scanItem("Avocado", 5);    //test total price when scanning an quantity package deal item
        BigDecimal actual1 = checkout.getTotalPrice();
        assertEquals(getFormat(4), getFormat(actual1));

        checkout.scanItem("Bread", 1); //test total price of all scanned items mixed with non special offer quantity items
        BigDecimal actual2 = checkout.getTotalPrice();
        assertEquals(getFormat(5.99), getFormat(actual2));

        inventory.setNForXDollarSpecial("Ice Cream", 6, getFormat(10), 6, true);//test total price of all scanned item mixed with quantity package item with limitation
        checkout.scanItem("Ice Cream", 7);
        BigDecimal actual3 = checkout.getTotalPrice();
        assertEquals(getFormat(17.98), getFormat(actual3));

        inventory.setNForXDollarSpecial("Grape", 4.00, getFormat(3), true);// test total price of all scanned item mixed with weighted package deal items.
        checkout.scanItem("Grape", 8.00);
        BigDecimal actual4 = checkout.getTotalPrice();
        assertEquals(getFormat(23.98), getFormat(actual4));

        inventory.setNForXDollarSpecial("Broccoli", 3, getFormat(2.50), 6.00,  true);// test total price of all scanned item mixed with weighted package deal items with limitation.
        checkout.scanItem("Broccoli", 7.00);
        BigDecimal actual5 = checkout.getTotalPrice();
        assertEquals(getFormat(29.97), getFormat(actual5));
    }

    @Test
    public void scanningByNForMAtXPercentageOffItemsReturnsTheCheckoutTotalPrice() { // Buy N for M at X% off, with or without limitation
        inventory.setBuyNGetMAtAPercentage("Avocado", 2, 1, true);//buy 2 get 1 for free

        checkout.scanItem("Avocado", 6);
        BigDecimal actual1 = checkout.getTotalPrice();
        assertEquals(getFormat(4), getFormat(actual1));

        inventory.setBuyNGetMAtAPercentage("Avocado", 2, 1, 50.00, true);//buy 2 get 1 for 50% off
        BigDecimal actual2 = checkout.getTotalPrice();
        assertEquals(getFormat(5), getFormat(actual2));

        inventory.setBuyNGetMAtAPercentage("Avocado", 1, 1, 50.00, 4, true); //buy 1 get 1 for 50% off with a limitation of 4
        BigDecimal actual3 = checkout.getTotalPrice();
        assertEquals(getFormat(5), getFormat(actual3));

        inventory.setBuyNGetMAtAPercentage("Ground Beef", 2.00, 1.00, 50.00, 6.00, true); //buy 2 pounds get 1 pound for half off with a limitation of 6

        checkout.scanItem("Ground Beef", 7.00);
        BigDecimal actual4 = checkout.getTotalPrice();
        assertEquals(getFormat(14), getFormat(actual4));

        checkout.scanItem("Apple", 5.00); // scan more item to test the updated total price
        BigDecimal actual5 = checkout.getTotalPrice();
        assertEquals(getFormat(17.45), getFormat(actual5));
    }

    @Test
    public void removingItemsFromCartReturnTheCheckoutTotalPrice(){
        inventory.setBuyNGetMAtAPercentage("Ground Beef", 2.00, 1.00, 50.00, 6.00, true); //buy 2 pounds get 1 pound for half off with a limitation of 6
        inventory.setNForXDollarSpecial("Broccoli", 3, getFormat(2.50), 6.00,  true);// buy 3 pounds for $2.5, limit 6 pounds
        inventory.setMarkdown("Apple", getFormat(0.19), true); // markdown $0.19
        inventory.setBuyNGetMAtAPercentage("Pasta", 3, 1, true);// buy 3 get 1 for free
        inventory.setBuyNGetMAtAPercentage("Small Green Onion", 2, 1, 25.00, true); // buy 2 get 1 for 25% off


        checkout.scanItem("Pasta", 5);
        checkout.scanItem("Bread", 3);
        checkout.scanItem("Avocado", 5);
        checkout.scanItem("Ground Beef", 10.00);
        checkout.scanItem("Apple", 3.00);
        checkout.scanItem("Bacon", 1);
        checkout.scanItem("Small Green Onion", 2);
        checkout.scanItem("Organic Chicken", 5.15);
        checkout.scanItem("Small Green Onion", 2);

        checkout.testMethod();
        assertEquals(getFormat(61.67), getFormat(checkout.getTotalPrice()));

        checkout.removeItem("Bread", 1);//remove 1 bag of bread
        assertEquals(getFormat(59.68), getFormat(checkout.getTotalPrice()));

        checkout.removeItem("Ice Cream", false); //remove item that is not in the cart
        assertEquals(getFormat(59.68), getFormat(checkout.getTotalPrice()));

        checkout.removeItem("Bread", true); //remove all bread
        assertEquals(getFormat(55.7), getFormat(checkout.getTotalPrice()));

        checkout.removeItem("Small Green Onion", 2); // remove 2, which results not qualify any special offer
        assertEquals(getFormat(54.67), getFormat(checkout.getTotalPrice()));
    }

}