package specialPricePackageTests;

import mainPackage.Inventory;
import mainPackage.Item;
import mainPackage.FormatBigDecimal;
import org.junit.Before;
import org.junit.Test;
import specialPricePacakage.NForXDollar;

import static org.junit.Assert.*;

public class NForXDollarTest extends FormatBigDecimal {
    private Inventory inventory;
    private NForXDollar NFXD;

    @Before
    public void setup() {
        inventory = new Inventory();
        inventory.storeItems(new Item("Bread", false, getFormat(1.99), false));
        inventory.storeItems(new Item("Grape", true, getFormat(0.99), false));

    }

    @Test
    public void setItemSpecialOfferReturnsItemOrderTotalPrice(){
        NFXD = new NForXDollar(3, getFormat(5.00), (Integer) null); //test in quantity
        assertEquals(getFormat(10.00), getFormat(NFXD.calculatePrice(inventory.getItemHashMap().get("Bread"), 6)));

        NFXD = new NForXDollar(3.00, getFormat(2.00), null); //test in weight
        assertEquals(getFormat(3.98), getFormat(NFXD.calculatePrice(inventory.getItemHashMap().get("Grape"), 5.00)));

    }
}