package mainPackage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CheckoutOrder {

    private Inventory inventory;

    private Map<Item, Integer> itemInQuantity = new HashMap<>();
    private Map<Item, Double> itemInWeight = new HashMap<>();


    public CheckoutOrder(Inventory inventory) {
        this.inventory = inventory;
    }

    public Item scanItem(String name) {
        return inventory.findAnItem(name);
    }

    public Map<Item, Integer> getItemInQuantity() {
        return itemInQuantity;
    }

    public Map<Item, Double> getItemInWeight() {
        return itemInWeight;
    }

}
