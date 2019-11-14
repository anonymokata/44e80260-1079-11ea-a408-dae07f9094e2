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

    public void scanItem(String name) {
        scanItem(name, 1, 0);
    }

    public void scanItem(String name, int quantity) {
        scanItem(name, quantity, 0);
    }

    public void scanItem(String name, double weight) {
        scanItem(name, 0, weight);
    }

    public Item scanItem(String name, int quantity, double weight) {
        Item item = inventory.findItem(name);
        System.out.println("Is item exist: " + isExist(item));
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

    public void storeItem(Item item, int quantity, double weight) {
        System.out.println("Is in weight : "+ item.isInWeight());
        if (item.isInWeight()) {
            scannedItemsInWeight.put(item, weight);
            testMethod();

        } else {
            scannedItemsInQuantity.put(item, quantity);
            testMethod();
        }
    }

    private void getUpdatedQuantity(String name, Item item, int quantity) {
        Integer count = scannedItemsInQuantity.get(name);
        if (count == null) {
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

    /*---------------calculate total price methods------------------*/
    public BigDecimal getTotalPrice() {
       return getItemTotalInQuantity().add(getItemTotalInWeight());
    }

    private BigDecimal getItemTotalInQuantity(){
        BigDecimal sum = new BigDecimal(0);
        for (Map.Entry<Item, Integer> entry : scannedItemsInQuantity.entrySet()) {
            if(entry.getKey().isSpecial()){
                sum = sum.add(entry.getKey().getSpecialPrice().calculatePrice(entry.getKey(), entry.getValue()));
            }else{
                sum = sum.add(entry.getKey().calculatePrice(entry.getKey(), entry.getValue()));
            }
        }
        return sum;
    }

    private BigDecimal getItemTotalInWeight(){
        BigDecimal sum = new BigDecimal(0);
        for (Map.Entry<Item, Double> entry : scannedItemsInWeight.entrySet()) {
            if(entry.getKey().isSpecial()){
                sum = sum.add(entry.getKey().getSpecialPrice().calculatePrice(entry.getKey(), entry.getValue()));
            }else{
                sum = sum.add(entry.getKey().calculatePrice(entry.getKey(), entry.getValue()));
            }
        }
        return sum;
    }
//





    /*-------------------------------Remove Methods-------------------------*/
    public void removeItem(String name, boolean removeItem) {
        removeItem(name, removeItem, 0);
    }

    public void removeItem(String name, int quantity) {
        removeItem(name, false, quantity);
    }

    public void removeItem(String name, boolean removeItem, int quantity) {
        Item item = inventory.findItem(name);
        if (removeItem) {
            removeItemAll(item);
        } else {
            partialRemove(item, quantity);
        }
    }

    private void removeItemAll(Item item) {
        if (scannedItemsInQuantity.containsKey(item)) {
            for (Map.Entry<Item, Integer> entry : scannedItemsInQuantity.entrySet()) {
                if (entry.getKey() == item) {
                    scannedItemsInQuantity.remove(entry.getKey());
                    break;
                }
            }
        } else {
            System.out.println("Item is not found --> cannot remove any");
        }
    }

    private void partialRemove(Item item, int quantity) {
        if (scannedItemsInQuantity.containsKey(item)) {
            for (Map.Entry<Item, Integer> entry : scannedItemsInQuantity.entrySet()) {
                if (entry.getKey() == item) {
                    if (quantity > entry.getValue()) {
                        System.out.println("You only have " + entry.getValue() + " in your cart. Try again");
                    }
                    scannedItemsInQuantity.put(entry.getKey(), entry.getValue() - quantity);
                }
            }
        } else {
            System.out.println("Item is not found,  cannot remove any");
        }
    }
    /*----------------------------------------------------------------------------*/

    private void testMethod() {
        int count = 0;
        System.out.println("size: "+ scannedItemsInQuantity.size());
        System.out.println("size in Weight Map: " + scannedItemsInWeight.size());
        for(Map.Entry<Item, Integer> testMap : scannedItemsInQuantity.entrySet()) {
            count++;
            System.out.println(count + ": " + testMap.getKey().toString());

        }
        for(Map.Entry<Item, Double> testMap : scannedItemsInWeight.entrySet()) {
            count++;
            System.out.println(count + ": " + testMap.getKey().toString());
        }
    }
}
