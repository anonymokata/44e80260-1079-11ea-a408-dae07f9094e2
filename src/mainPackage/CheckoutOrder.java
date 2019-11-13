package mainPackage;

import java.util.HashMap;
import java.util.Map;

public class CheckoutOrder {

    private Inventory inventory;

    Map<String, Item> scannedItemsMap = new HashMap<>();

    public CheckoutOrder(Inventory inventory) {
        this.inventory = inventory;
    }

    public Item scanItem(String name) {
        Item item = inventory.findItem(name);
        storeScannedItems(item);
        return item;
    }

    public void storeScannedItems(Item item){
        scannedItemsMap.put(item.getItemName(), item);
    }



}
