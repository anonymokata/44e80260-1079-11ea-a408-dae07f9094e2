package mainPackage;

import org.junit.Before;
import org.junit.Test;
import specialPricePacakage.BuyNGetMAtAPercentageInQuantity;
import specialPricePacakage.BuyNGetMAtAPercentageInWeight;
import specialPricePacakage.Markdown;
import specialPricePacakage.NForXDollar;

import java.math.BigDecimal;
import static org.junit.Assert.*;

public class CheckoutOrderTest extends formatBigDecimal{
    private Inventory inventory;
    private Item item;
    private Price price;
    private CheckoutOrder checkout;

    @Before
    public void setup(){
        inventory = new Inventory();
        inventory.storeItems(new Item("Bread", 10, new Price(getFormat(1.99))));
    }

    @Test
    public void scanAnExistedItemReturnsTheItemTotalPrice(){ // regular price(without any special offer)
        //store the item
//        Price price = new Price(getFormat(2));
//        Item item = new Item("Bread", 10, price);
//        Inventory inventory = new Inventory();
//        inventory.storeItems(item);

        //checkout this item with regular sell price
        String name = "Bread";
        int quantity = 10;
        CheckoutOrder checkout = new CheckoutOrder(inventory);
        Item scannedItem = checkout.scanItem(name);
        BigDecimal actual = inventory.getItemHashMap().get(name).getPrice().getItemRegularTotalPrice(scannedItem, quantity);
        assertEquals(getFormat(19.90),getFormat(actual)) ;

    }

    @Test
    public void scanningAnMarkdownItemReturnTheItemTotalPrice(){ // markdown price
        Price price = new Price(getFormat(3));
        Item item = new Item("Pasta", 10, price);
        Inventory inventory = new Inventory();
        inventory.storeItems(item);

        String name = "Pasta";
        int quantity = 4;
        item.getPrice().setMarkdown(new Markdown(getFormat(1.00)));

        CheckoutOrder checkout = new CheckoutOrder(inventory);
        Item scannedItem = checkout.scanItem(name);
        BigDecimal actual = scannedItem.getPrice().getMarkdown().getItemTotalPriceAfterMarkdown(scannedItem, quantity);
        assertEquals(getFormat(8), getFormat(actual));

    }

    @Test
    public void scanningAnPackageDealReturnTheItemTotalPrice(){ // package deal -> N for $M
        Price price = new Price(getFormat(3));
        Item item = new Item("Pasta", 10, price);
        Inventory inventory = new Inventory();
        inventory.storeItems(item);

        //2 for $5

        String name = "Pasta";
        int quantity = 7;
        item.getPrice().setNForXDollar(new NForXDollar(getFormat(5), 2));

        CheckoutOrder checkout = new CheckoutOrder(inventory);
        Item scannedItem = checkout.scanItem(name);
        BigDecimal actual = scannedItem.getPrice().getNForXDollar().calculateThisItemTotalPrice(scannedItem, quantity);
        assertEquals(getFormat(18), getFormat(actual));
    }

    @Test
    public void specialOfferInQuantityReturnsTheItemTotalPrice(){ // Buy N for M at X% off
        Price price = new Price(getFormat(1));
        Item item = new Item("Avocado", 10, price);
        Inventory inventory = new Inventory();
        inventory.storeItems(item);

        //buy 2 get 1 for 50% off
        String name = "Avocado";
        int quantity = 3;
        item.getPrice().setQuantitySpecial(new BuyNGetMAtAPercentageInQuantity(2, 1, null, 50)); //3rd param: 100% for free

        CheckoutOrder checkout = new CheckoutOrder(inventory);
        Item scannedItem = checkout.scanItem(name);
        BigDecimal actual = scannedItem.getPrice().getQuantitySpecial().calculateThisItemTotalPrice(scannedItem, quantity);
        assertEquals(getFormat(2.5), getFormat(actual));
    }

    @Test
    public void specialOfferInWeightReturnsTheItemTotalPrice(){  // Buy N pounds get M pounds at X% off
        Price price = new Price(getFormat(1));
        Item item = new Item("GroundBeef", 10.00, price);
        Inventory inventory = new Inventory();
        inventory.storeItems(item);

        //buy 2 pounds get 1 pound 50% off
        String name = "GroundBeef";
        double weight = 5.5;
        item.getPrice().setWeightSpecial(new BuyNGetMAtAPercentageInWeight(2, 1, 50));
        CheckoutOrder checkout = new CheckoutOrder(inventory);
        Item scannedItem = checkout.scanItem(name);
        BigDecimal actual = scannedItem.getPrice().getWeightSpecial().calculateThisItemTotalPrice(scannedItem, weight);
        assertEquals(getFormat(5.00), getFormat(actual));
    }

}