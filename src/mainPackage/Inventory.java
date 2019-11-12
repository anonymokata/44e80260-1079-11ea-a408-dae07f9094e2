package mainPackage;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Item> items = new HashMap<>();


    public void storeItems(Item item){
        this.items.put(item.getItemName(), item);
    }

    public Map<String, Item> getItems() {
        return items;
    }


}
