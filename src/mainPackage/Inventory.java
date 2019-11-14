package mainPackage;

import specialPricePacakage.BuyNGetMAtAPercentage;
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

    //markdown
    public void setMarkdown(String name, BigDecimal markdownPrice, boolean isSpecial){
        Item item = findItem(name);
        item.setSpecial(isSpecial);
        item.setSpecialPrice(new Markdown(markdownPrice));
    }

    //NForXDollar: quantity params
    public void setNForXDollarSpecial(String name, int packageQuantity, BigDecimal packagePrice, boolean isSpecial){
        Item item = findItem(name);
        item.setSpecial(isSpecial);
        item.setSpecialPrice(new NForXDollar(packageQuantity, packagePrice));
    }

    //NForXDollar: weight params
    public void setNForXDollarSpecial(String name, double packageWeight, BigDecimal packagePrice){
        Item item = findItem(name);
        item.setSpecialPrice(new NForXDollar(packageWeight, packagePrice));
    }


    //BuyNGetMAtAPercentage: quantity params
    public void setBuyNGetMAtAPercentage(String name, int initialQuantity, int freeQuantity){ //in quantity
        setBuyNGetMAtAPercentage(name, initialQuantity, freeQuantity, null);
    }

    public void setBuyNGetMAtAPercentage(String name, int initialQuantity, int freeQuantity, Double discountPercentage){
        setBuyNGetMAtAPercentage(name, initialQuantity, freeQuantity, discountPercentage, null);
    }

    public void setBuyNGetMAtAPercentage(String name, int initialQuantity, int freeQuantity, Double discountPercentage, Integer limit){
        Item item = findItem(name);
        item.setSpecialPrice(new BuyNGetMAtAPercentage(initialQuantity, freeQuantity, discountPercentage, limit));
    }

    //uyNGetMAtAPercentage: weight params
    public void setBuyNGetMAtAPercentage(String name, double initialWeight, double freeWeight){ //in quantity
        setBuyNGetMAtAPercentage(name, initialWeight, freeWeight, null);
    }

    public void setBuyNGetMAtAPercentage(String name,double initialWeight, double freeWeight, Double discountPercentage){
        setBuyNGetMAtAPercentage(name, initialWeight, freeWeight, discountPercentage, null);
    }

    public void setBuyNGetMAtAPercentage(String name, double initialWeight, double freeWeight,Double discountPercentage, Integer limit){
        Item item = findItem(name);
        item.setSpecialPrice(new BuyNGetMAtAPercentage(initialWeight, freeWeight, discountPercentage, limit));
    }

}
