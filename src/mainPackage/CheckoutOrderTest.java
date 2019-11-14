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
        inventory.storeItems(new Item("Bread", 10, false, getFormat(1.99)));
        inventory.storeItems(new Item("Pasta", 10, false, getFormat(3)));
        inventory.storeItems(new Item("Bacon", 10, false, getFormat(3)));
        inventory.storeItems(new Item("Avocado", 10, false, getFormat(1)));
        inventory.storeItems(new Item("Ground Beef", true, getFormat(1.25)));
        inventory.storeItems(new Item("Organic Chicken", true, getFormat(3.59)));
        inventory.storeItems(new Item("Small Green Onion", false, getFormat(0.59)));
        inventory.storeItems(new Item("Broccoli", true, getFormat(0.99)));
        inventory.storeItems(new Item("Apple", true, getFormat(0.69)));
        inventory.storeItems(new Item("Ice Cream", false, getFormat(1.99)));
        inventory.storeItems(new Item("Grape", true, getFormat(0.99)));

        checkout = new CheckoutOrder(inventory);
    }

    @Test
    public void scanAnExistedItemReturnsTheUpdatedTotalPrice() { // regular price(without any special offer)
        checkout.scanItem("Bread", 3); //test result in quantity
        BigDecimal actual1 = checkout.getTotalPrice();
        assertEquals(getFormat(5.97), getFormat(actual1));

        checkout.scanItem("Ground Beef", 2.50); //test result in weight
        BigDecimal actual2 = checkout.getTotalPrice();
        assertEquals(getFormat(9.10), getFormat(actual2));

        checkout.removeItem("Bread", 1);//remove 1 bag of bread
        BigDecimal actual3 = checkout.getTotalPrice();
        assertEquals(getFormat(7.11), getFormat(actual3));

        checkout.removeItem("Ice Cream", false); //remove item that is not in the cart
        BigDecimal actual4 = checkout.getTotalPrice();
        assertEquals(getFormat(7.11), getFormat(actual4));

        checkout.removeItem("Bread", true); //remove all bread
        BigDecimal actual5 = checkout.getTotalPrice();
        assertEquals(getFormat(3.13), getFormat(actual5));

       

    }

//    @Test
//    public void scanningAnMarkdownItemReturnTheItemTotalPrice() { // markdown price
//        inventory.setMarkdown("Pasta", getFormat(0.50), true); // markdown $0.50
//
//        checkout.scanItem("Bread", 3);
//        checkout.scanItem("Ground Beef", 2.50);
//        checkout.scanItem("Pasta", 3);
//        BigDecimal actual = checkout.getTotalPrice();
//        assertEquals(getFormat(9), getFormat(actual));
//
//    }

//    @Test
//    public void scanningAnPackageDealReturnTheItemTotalPrice() { // package deal -> N for $M
//        String name = "Bacon";
//
//        inventory.setNForXDollarSpecial(name, 3, getFormat(7), true);// special --> 3 for $5
//        Item scannedItem = checkout.scanItem(name);
//        BigDecimal actual = getSpecialPrice(name).getnForXDollar().calculatePrice(scannedItem, 3);
//        assertEquals(getFormat(7), getFormat(actual));
//    }
//
//    @Test
//    public void specialOfferInQuantityReturnsTheItemTotalPrice() { // Buy N for M at X% off, with or without limitation
//        String name = "Avocado";
//
//        inventory.setBuyNGetMAtAPercentageInQuantity(name, 2, 1, true);//buy 2 get 1 for free
//        Item scannedItem = checkout.scanItem(name);
//        BigDecimal actual1 = getSpecialPrice(name).getQuantitySpecial().calculatePrice(scannedItem, 3);
//        assertEquals(getFormat(2), getFormat(actual1));
//
//        inventory.setBuyNGetMAtAPercentageInQuantity(name, 2, 1, 50.00, true);//buy 2 get 1 for 50% off
//        BigDecimal actual2 = getSpecialPrice(name).getQuantitySpecial().calculatePrice(scannedItem, 3);
//        assertEquals(getFormat(2.50), getFormat(actual2));
//
//        inventory.setBuyNGetMAtAPercentageInQuantity(name, 2, 1, 50.00, 6, true); //buy 2 get 1 for 50% off with a limitation of 6
//        BigDecimal actual3 = getSpecialPrice(name).getQuantitySpecial().calculatePrice(scannedItem, 7);
//        assertEquals(getFormat(6), getFormat(actual3));
//
//    }
//
//    @Test
//    public void specialOfferInWeightReturnsTheItemTotalPrice(){  // Buy N pounds get M pounds at X% off
//        String name = "Ground Beef";
//
//        inventory.setBuyNGetMAtAPercentageInWeight(name, 2, 1, true); // Buy 2 pounds get 1 pound for free
//        Item scannedItem = checkout.scanItem(name);
//        BigDecimal actual1 = getSpecialPrice(name).getWeightSpecial().calculatePrice(scannedItem, 5.50);
//        assertEquals(getFormat(5.63), getFormat(actual1));
//
//        inventory.setBuyNGetMAtAPercentageInWeight(name, 3, 1, 75.00, true);
//        BigDecimal actual2 = getSpecialPrice(name).getWeightSpecial().calculatePrice(scannedItem, 5);
//        assertEquals(getFormat(5.31), getFormat(actual2));
//
//    }

    private SpecialPrice getSpecialPrice(String name) {
        return inventory.getItemHashMap().get(name).getSpecialPrice();
    }

}