package mainPackage;

import java.math.BigDecimal;
import java.util.*;

public class CheckoutOrder extends formatBigDecimal {

    private Inventory inventory;

    Map<Item, Integer> scannedItemsInQuantity = new HashMap<>();
    Map<Item, Double> scannedItemsInWeight = new HashMap<>();

    public CheckoutOrder(Inventory inventory) {
        this.inventory = inventory;
    }

    public void scanItem(String name){
        scanItem(name, 1, 0);
    }

    public void scanItem(String name, int quantity){
        scanItem(name, quantity, 0);
    }
    public void scanItem(String name, double weight){
        scanItem(name, 0, weight);
    }
    public Item scanItem(String name, int quantity, double weight) {
        Item item = inventory.findItem(name);
        if (isExist(item)) {
            if (item.isInWeight()) {
                getUpdatedWeight(name, item, weight);
            }
            getUpdatedQuantity(name, item, quantity);
        } else {
           storeItem(item, quantity, weight);
        }
        return item;
    }

    public void storeItem(Item item, int quantity, double weight){
        if(item.isInWeight()){
            scannedItemsInWeight.put(item, weight);
        }else{
            scannedItemsInQuantity.put(item, quantity);
        }
    }

    private void getUpdatedQuantity(String name, Item item, int quantity) {
        Integer count = scannedItemsInQuantity.get(name);
        if(count == null){
            scannedItemsInQuantity.put(item, quantity);
        }
        scannedItemsInQuantity.put(item, count + quantity);
    }

    public boolean isExist(Item item) {
        if (scannedItemsInQuantity.containsKey(item.getItemName())) {
            return true;
        }
        return false;
    }

    private void getUpdatedWeight(String name, Item item, double weight) {
        Double count = scannedItemsInWeight.get(name);
        if (count == null) {
            scannedItemsInWeight.put(item, weight);
        } else {
            scannedItemsInWeight.put(item, count + weight);
        }
    }

    public BigDecimal getItemTotalPriceInQuantity() {
        BigDecimal sum = getFormat(0);
        for (Map.Entry<Item, Integer> entry : scannedItemsInQuantity.entrySet()) {
            if (inventory.getMarkdown()) {
                sum = sum.add(entry.getKey().getSpecialPrice().getQuantitySpecial().calculatePrice(entry.getKey(), entry.getValue()));
            } else if (inventory.getNForXDollar()) {
                sum = sum.add(entry.getKey().getSpecialPrice().getQuantitySpecial().calculatePrice(entry.getKey(), entry.getValue()));
            } else if (inventory.getSpecialInQuantity()) {
                sum = sum.add(entry.getKey().getSpecialPrice().getQuantitySpecial().calculatePrice(entry.getKey(), entry.getValue()));
            } else if(inventory.getSpecialInWeight()) {
                sum = sum.add(entry.getKey().getSpecialPrice().getWeightSpecial().calculatePrice(entry.getKey(), entry.getValue()));
            }
           sum = sum.add(entry.getKey().calculatePrice(entry.getKey(), entry.getValue()));

        }
        return sum;
    }

    public BigDecimal getItemTotalPriceInWeight() {
        BigDecimal sum = getFormat(0);
        for (Map.Entry<Item, Double> entry : scannedItemsInWeight.entrySet()) {
            if (inventory.getMarkdown()) {
                sum = sum.add(entry.getKey().getSpecialPrice().getWeightSpecial().calculatePrice(entry.getKey(), entry.getValue()));
            } else if (inventory.getNForXDollar()) {
                sum = sum.add(entry.getKey().getSpecialPrice().getWeightSpecial().calculatePrice(entry.getKey(), entry.getValue()));
            } else if (inventory.getSpecialInQuantity()) {
                sum = sum.add(entry.getKey().getSpecialPrice().getWeightSpecial().calculatePrice(entry.getKey(), entry.getValue()));
            } else if(inventory.getSpecialInWeight()) {
                sum = sum.add(entry.getKey().getSpecialPrice().getWeightSpecial().calculatePrice(entry.getKey(), entry.getValue()));
            }
            sum = sum.add(entry.getKey().calculatePrice(entry.getKey(), entry.getValue()));

        }
        return sum;
    }

    public BigDecimal getTotalPrice() {
        return getItemTotalPriceInQuantity().add(getItemTotalPriceInWeight());
}
}
