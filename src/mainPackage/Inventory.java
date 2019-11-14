package mainPackage;

import specialPricePacakage.BuyNGetMAtAPercentageInQuantity;
import specialPricePacakage.BuyNGetMAtAPercentageInWeight;
import specialPricePacakage.Markdown;
import specialPricePacakage.NForXDollar;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Item> itemHashMap = new HashMap<>();


    public void storeItems(Item item) {
        this.itemHashMap.put(item.getItemName(), item);
    }

    public Map<String, Item> getItemHashMap() {
        return itemHashMap;
    }

    public Item findItem(String name){
        if(itemHashMap.containsKey(name)){
            return itemHashMap.get(name);
        }else{
            throw new RuntimeException(name + " is not found, please contact the clerk.");
        }
    }

    public void setMarkdown(String name, BigDecimal markdownPrice){
        Item item = findItem(name);
        item.setSpecialPrice(new SpecialPrice(new Markdown(markdownPrice)));
    }

    public void setNForXDollarSpecial(String name, int packageQuantity, BigDecimal packagePrice){
        Item item = findItem(name);
        item.setSpecialPrice(new SpecialPrice(new NForXDollar(packageQuantity, packagePrice)));
    }

    public void setBuyNGetMAtAPercentageInQuantity(String name, int initialQuantity, int freeQuantity){ //in quantity
        setBuyNGetMAtAPercentageInQuantity(name, initialQuantity, freeQuantity, null, null);
    }

    public void setBuyNGetMAtAPercentageInQuantity(String name, int initialQuantity, int freeQuantity, Double discountPercentage){
        setBuyNGetMAtAPercentageInQuantity(name, initialQuantity, freeQuantity, discountPercentage, null);
    }

    public void setBuyNGetMAtAPercentageInQuantity(String name, int initialQuantity, int freeQuantity, Double discountPercentage, Integer limit){
        Item item = findItem(name);
        item.setSpecialPrice(new SpecialPrice(new BuyNGetMAtAPercentageInQuantity(initialQuantity, freeQuantity, discountPercentage, limit)));
    }

    public void setBuyNGetMAtAPercentageInWeight(String name, double initialWeight, double freeWeight){ //in weight
        setBuyNGetMAtAPercentageInWeight(name, initialWeight, freeWeight, null);
    }

    public void setBuyNGetMAtAPercentageInWeight(String name, double initialWeight, double freeWeight, Double discountPercentage){
        Item item = findItem(name);
        item.setSpecialPrice(new SpecialPrice(new BuyNGetMAtAPercentageInWeight(initialWeight, freeWeight, discountPercentage)));
    }

}
