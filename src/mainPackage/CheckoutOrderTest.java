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
    private CheckoutOrder checkout;

    @Before
    public void setup(){
        inventory = new Inventory();
        inventory.storeItems(new Item("Bread", 10, new Price(getFormat(1.99))));
        inventory.storeItems(new Item("Pasta", 10, new Price(getFormat(3))));
        inventory.storeItems(new Item("Bacon", 10, new Price(getFormat(3))));
        inventory.storeItems(new Item("Avocado", 10, new Price(getFormat(1))));
        inventory.storeItems(new Item("GroundBeef", 10.00, new Price(getFormat(1))));

        checkout = new CheckoutOrder(inventory);
    }

    @Test
    public void scanAnExistedItemReturnsTheItemTotalPrice(){ // regular price(without any special offer)

        //checkout this item with regular sell price
        String name = "Bread";
        int quantity = 10;

        Item scannedItem = checkout.scanItem(name);
        BigDecimal actual = inventory.getItemHashMap().get(name).getPrice().getItemRegularTotalPrice(scannedItem, quantity);
        assertEquals(getFormat(19.90),getFormat(actual)) ;

    }

    @Test
    public void scanningAnMarkdownItemReturnTheItemTotalPrice(){ // markdown price

        String name = "Pasta";
        int quantity = 4;
        inventory.getItemHashMap().get(name).getPrice().setMarkdown(new Markdown(getFormat(1.00)));

        Item scannedItem = checkout.scanItem(name);
        BigDecimal actual = inventory.getItemHashMap().get(name).getPrice().getMarkdown().getItemTotalPriceAfterMarkdown(scannedItem, quantity);
        assertEquals(getFormat(8), getFormat(actual));

    }

    @Test
    public void scanningAnPackageDealReturnTheItemTotalPrice(){ // package deal -> N for $M

        //2 for $5
        String name = "Bacon";
        int quantity = 7;
        inventory.getItemHashMap().get(name).getPrice().setNForXDollar(new NForXDollar(getFormat(5), 2));

        Item scannedItem = checkout.scanItem(name);
        BigDecimal actual = inventory.getItemHashMap().get(name).getPrice().getNForXDollar().calculateThisItemTotalPrice(scannedItem, quantity);
        assertEquals(getFormat(18), getFormat(actual));
    }

    @Test
    public void specialOfferInQuantityReturnsTheItemTotalPrice(){ // Buy N for M at X% off

        //buy 2 get 1 for 50% off
        String name = "Avocado";
        int quantity = 3;
        inventory.getItemHashMap().get(name).getPrice().setQuantitySpecial(new BuyNGetMAtAPercentageInQuantity(2, 1, null, 50)); //3rd param: 100% for free

        Item scannedItem = checkout.scanItem(name);
        BigDecimal actual =  inventory.getItemHashMap().get(name).getPrice().getQuantitySpecial().calculateThisItemTotalPrice(scannedItem, quantity);
        assertEquals(getFormat(2.5), getFormat(actual));
    }

    @Test
    public void specialOfferInWeightReturnsTheItemTotalPrice(){  // Buy N pounds get M pounds at X% off

        //buy 2 pounds get 1 pound 50% off
        String name = "GroundBeef";
        double weight = 5.5;
        inventory.getItemHashMap().get(name).getPrice().setWeightSpecial(new BuyNGetMAtAPercentageInWeight(2, 1, 50));

        Item scannedItem = checkout.scanItem(name);
        BigDecimal actual = inventory.getItemHashMap().get(name).getPrice().getWeightSpecial().calculateThisItemTotalPrice(scannedItem, weight);
        assertEquals(getFormat(5.00), getFormat(actual));
    }

}