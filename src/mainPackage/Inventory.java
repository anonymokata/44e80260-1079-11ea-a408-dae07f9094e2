package mainPackage;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Item> items = new HashMap<>();


    public void storeItems(Item item) {
        this.items.put(item.getItemName(), item);
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public Item findAnItem(String name){
        if(items.containsKey(name)){
            return items.get(name);
        }else{
            throw new RuntimeException(name + " is not found, please contact the clerk.");
        }
    }
}
