package mainPackage.mainPackageTests;

import mainPackage.CheckoutOrder;
import mainPackage.FormattingBigDecimal;
import mainPackage.Inventory;
import mainPackage.Item;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

public class CheckoutOrderTest extends FormattingBigDecimal {
    private Inventory inventory;
    private CheckoutOrder checkout;

    @Before
    public void setup() {
        inventory = new Inventory();
        //constructor params: String itemName, boolean isWeight, BigDecimal itemPrice, Boolean isSpecial
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
        inventory.storeItems(new Item("Pork Shoulder", true, getFormat(3.99), false));

        checkout = new CheckoutOrder(inventory);
    }

    @Test
    public void scanItemReturnsTheCheckoutTotalPrice() { // regular price(without any special offer)
        checkout.scanItem("Bread", 3); //test result in quantity
        checkout.scanItem("Ground Beef", 2.00); // test result in weight

        assertEquals(getFormat(8.97), getFormat(checkout.getTotalPrice()));
    }

    @Test     // markdown price
    public void scanMarkdownItemReturnTheCheckoutTotalPrice() {
        inventory.setMarkdown("Ground Beef", getFormat(0.50), true); //params: (name, markdownPrice, isSpecial)

        checkout.scanItem("Ground Beef", 2.50); //test single markdown price total
        assertEquals(getFormat(2.50), getFormat(checkout.getTotalPrice()));

        checkout.scanItem("Bread", 1); //test updated total price
        assertEquals(getFormat(4.49), getFormat(checkout.getTotalPrice()));
    }

    @Test   // package deal -> N for $M
    public void scanningAnPackageDealReturnTheCheckoutTotalPrice() {

        inventory.setNForXDollarSpecial("Avocado", 4, getFormat(3.00), true); // params: (name, initialQuantity, discountQuantity, isSpecial)
        checkout.scanItem("Avocado", 5);
        assertEquals(getFormat(4), getFormat(checkout.getTotalPrice()));

        checkout.scanItem("Bread", 1);
        assertEquals(getFormat(5.99), getFormat(checkout.getTotalPrice()));

        inventory.setNForXDollarSpecial("Ice Cream", 6, getFormat(10), 6, true);// params: (name, packageQuantity, packagePrice, limitation, isSpecial)
        checkout.scanItem("Ice Cream", 7);
        assertEquals(getFormat(17.98), getFormat(checkout.getTotalPrice()));

        inventory.setNForXDollarSpecial("Grape", 4.00, getFormat(3), true); //params: (name, packageWeight, packagePrice, isSpecial)
        checkout.scanItem("Grape", 8.00);
        assertEquals(getFormat(23.98), getFormat(checkout.getTotalPrice()));

        inventory.setNForXDollarSpecial("Broccoli", 3.00, getFormat(2.50), 6.00, true); //params: (name, packageWeight, packagePrice, limitation, isSpecial)
        checkout.scanItem("Broccoli", 7.00);
        assertEquals(getFormat(29.97), getFormat( checkout.getTotalPrice()));
    }

    @Test
    public void scanningBuyNForMAtXPercentageOffItemsReturnsTheCheckoutTotalPrice() { // Buy N for M at X% off, with or without limitation
        inventory.setBuyNGetMAtAPercentage("Avocado", 2, 1, true);//buy 2 get 1 for free

        checkout.scanItem("Avocado", 6);
        assertEquals(getFormat(4), getFormat(checkout.getTotalPrice()));

        inventory.setBuyNGetMAtAPercentage("Avocado", 2, 1, 50.00, true);//buy 2 get 1 for 50% off
        assertEquals(getFormat(5), getFormat(checkout.getTotalPrice()));

        inventory.setBuyNGetMAtAPercentage("Avocado", 1, 1, 50.00, 4, true); //buy 1 get 1 for 50% off with a limitation of 4
        assertEquals(getFormat(5), getFormat(checkout.getTotalPrice()));

        inventory.setBuyNGetMAtAPercentage("Ground Beef", 2.00, 1.00, 50.00, 6.00, true); //buy 2 pounds get 1 pound for half off with a limitation of 6 lbs

        checkout.scanItem("Ground Beef", 7.00);
        assertEquals(getFormat(14), getFormat(checkout.getTotalPrice()));

        checkout.scanItem("Apple", 5.00); // scan more item to test the updated total price
        assertEquals(getFormat(17.45), getFormat(checkout.getTotalPrice()));

        inventory.setBuyNGetMAtAPercentage("Pork Shoulder", 4.00, 1.50, true); // buy 4 lbs get 1.5 lbs for free
        checkout.scanItem("Pork Shoulder", 11.00);
        assertEquals(getFormat(49.37), getFormat(checkout.getTotalPrice()));
    }

    @Test
    public void removingItemsFromCartReturnTheCheckoutTotalPrice() {
        inventory.setBuyNGetMAtAPercentage("Ground Beef", 2.00, 1.00, 50.00, 6.00, true); //buy 2 pounds get 1 pound for half off with a limitation of 6
        inventory.setNForXDollarSpecial("Broccoli", 3, getFormat(2.50), 6.00, true);// buy 3 pounds for $2.5, limit 6 pounds
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