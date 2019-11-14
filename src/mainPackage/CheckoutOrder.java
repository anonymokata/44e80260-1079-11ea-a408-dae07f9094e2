package mainPackage;

import java.math.BigDecimal;
import java.util.*;

public class CheckoutOrder extends formatBigDecimal {

    private Inventory inventory;

    Map<Item, Integer> scannedItemsInQuantity = new HashMap<>();
    Map<Item, Double> scannedItemsInWeight = new HashMap<>();

//    Map<String, BigDecimal> scannedItemPriceMap = new HashMap<>();


    public CheckoutOrder(Inventory inventory) {
        this.inventory = inventory;
    }

    public Item scanItem(String name) {
        Item item = inventory.findItem(name);
        if (isExist(item)) {
            if (item.isInWeight()) {
                getUpdatedWeight(name, item);
            }
            getUpdatedQuantity(name, item);
        } else {
           storeItem(item);
        }
        return item;
    }

    public void storeItem(Item item){
        if(item.isInWeight()){
            scannedItemsInWeight.put(item, getWeight());
        }else{
            scannedItemsInQuantity.put(item, 1);
        }
    }

    private void getUpdatedQuantity(String name, Item item) {
        Integer count = scannedItemsInQuantity.get(name);
        scannedItemsInQuantity.put(item, count++);
    }

    public boolean isExist(Item item) {
        if (scannedItemsInQuantity.containsKey(item.getItemName())) {
            return true;
        }
        return false;
    }

    private void getUpdatedWeight(String name, Item item) {
        Double count = scannedItemsInWeight.get(name);
        double weight = getWeight();
        if (count == null) {
            scannedItemsInWeight.put(item, weight);
        } else {
            scannedItemsInWeight.put(item, count + weight);
        }
    }

    private double getWeight() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please weight your item: ");
        double weight = scan.nextDouble();
        System.out.println("Your item weight " + weight);
        return weight;
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
