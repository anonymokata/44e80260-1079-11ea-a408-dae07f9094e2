package mainPackage;

import specialPricePacakage.BuyNGetMAtAPercentageInQuantity;
import specialPricePacakage.BuyNGetMAtAPercentageInWeight;
import specialPricePacakage.Markdown;
import specialPricePacakage.NForXDollar;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    Boolean isMarkdown = false;
    Boolean isNForXDollar = false;
    Boolean isSpecialInQuantity = false;
    Boolean isSpecialInWeight = false;
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

    public void setMarkdown(String name, BigDecimal markdownPrice, Boolean isMarkdown){
        Item item = findItem(name);
        item.setSpecialPrice(new SpecialPrice(new Markdown(markdownPrice)));
        isMarkdown =  true;
    }

    public void setNForXDollarSpecial(String name, int packageQuantity, BigDecimal packagePrice, Boolean isNForXDollar){
        Item item = findItem(name);
        item.setSpecialPrice(new SpecialPrice(new NForXDollar(packageQuantity, packagePrice)));
        isNForXDollar = true;
    }

    public void setBuyNGetMAtAPercentageInQuantity(String name, int initialQuantity, int freeQuantity, Boolean isSpecialInQuantity){ //in quantity
        setBuyNGetMAtAPercentageInQuantity(name, initialQuantity, freeQuantity, null, null, isSpecialInQuantity);
    }

    public void setBuyNGetMAtAPercentageInQuantity(String name, int initialQuantity, int freeQuantity, Double discountPercentage, Boolean isSpecialInQuantity){
        setBuyNGetMAtAPercentageInQuantity(name, initialQuantity, freeQuantity, discountPercentage, null, isSpecialInQuantity);
    }

    public void setBuyNGetMAtAPercentageInQuantity(String name, int initialQuantity, int freeQuantity, Double discountPercentage, Integer limit, Boolean isSpecialInQuantity){
        Item item = findItem(name);
        item.setSpecialPrice(new SpecialPrice(new BuyNGetMAtAPercentageInQuantity(initialQuantity, freeQuantity, discountPercentage, limit)));
        isSpecialInQuantity = true;
    }

    public void setBuyNGetMAtAPercentageInWeight(String name, double initialWeight, double freeWeight, Boolean isSpecialInWeight){ //in weight
        setBuyNGetMAtAPercentageInWeight(name, initialWeight, freeWeight, null, isSpecialInWeight);
    }

    public void setBuyNGetMAtAPercentageInWeight(String name, double initialWeight, double freeWeight, Double discountPercentage, Boolean isSpecialInWeight){
        Item item = findItem(name);
        item.setSpecialPrice(new SpecialPrice(new BuyNGetMAtAPercentageInWeight(initialWeight, freeWeight, discountPercentage)));
        isSpecialInWeight = true;
    }

    public Boolean getMarkdown() {
        return isMarkdown;
    }

    public Boolean getNForXDollar() {
        return isNForXDollar;
    }

    public Boolean getSpecialInQuantity() {
        return isSpecialInQuantity;
    }

    public Boolean getSpecialInWeight() {
        return isSpecialInWeight;
    }
}
