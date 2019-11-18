package specialPricePacakage.specialPricePackageTests;

import mainPackage.Inventory;
import mainPackage.Item;
import mainPackage.FormattingBigDecimal;
import org.junit.Before;
import org.junit.Test;
import specialPricePacakage.Markdown;

import static org.junit.Assert.*;

public class MarkdownTest extends FormattingBigDecimal {
    private Inventory inventory;
    private Markdown m;

    @Before
    public void setup() {
        inventory = new Inventory();
        inventory.storeItems(new Item("Bread", false, getFormat(1.99), false));
        inventory.storeItems(new Item("Grape", true, getFormat(0.99), false));

    }

    @Test
    public void markdownItemPriceReturnTheUpdatedPrice() {
         m = new Markdown(getFormat(0.49));
        assertEquals(getFormat(4.5), getFormat(m.calculatePrice(inventory.getItemInventory().get("Bread"), 3))); // test in quantity
        assertEquals(getFormat(1.5), getFormat(m.calculatePrice(inventory.getItemInventory().get("Grape"), 3))); //test in Weight
    }

}