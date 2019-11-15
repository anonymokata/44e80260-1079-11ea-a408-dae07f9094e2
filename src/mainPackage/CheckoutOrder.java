package mainPackage;

import jdk.swing.interop.SwingInterOpUtils;

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
        if (isExist(item)) {
            if (item.isInWeight()) {
                getUpdatedWeight(item, weight);
            }
            getUpdatedQuantity(item, quantity);
        } else {
            storeItem(item, quantity, weight);
        }
        return item;
    }

    public void storeItem(Item item, int quantity, double weight) {
        if (item.isInWeight()) {
            scannedItemsInWeight.put(item, weight);
        } else {
            scannedItemsInQuantity.put(item, quantity);
        }
    }

    private void getUpdatedQuantity(Item item, int quantity) {
        Integer count = scannedItemsInQuantity.get(item);
        if (count == null) {
            scannedItemsInQuantity.replace(item, quantity);
        }
        scannedItemsInQuantity.replace(item, count + quantity);
    }

    public boolean isExist(Item item) {
        if (scannedItemsInQuantity.containsKey(item)) {
            return true;
        }
        return false;
    }

    private void getUpdatedWeight(Item item, double weight) {
        Double count = scannedItemsInWeight.get(item);
        if (count == null) {
            scannedItemsInWeight.replace(item, weight);
        } else {
            scannedItemsInWeight.replace(item, count + weight);
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

   public void testMethod() {
        int count = 0;
        for(Map.Entry<Item, Integer> testMap : scannedItemsInQuantity.entrySet()) {
            count++;
            System.out.print(count + ": " + testMap.getKey().toString());
            System.out.println("         quantity: "+ testMap.getValue());

        }
        for(Map.Entry<Item, Double> testMap : scannedItemsInWeight.entrySet()) {
            count++;
            System.out.print(count + ": " + testMap.getKey().toString());
            System.out.println("           weight: "+ testMap.getValue());
        }
    }
}
