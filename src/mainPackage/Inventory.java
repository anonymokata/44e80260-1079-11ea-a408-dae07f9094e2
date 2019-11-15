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
      setNForXDollarSpecial(name, packageQuantity, packagePrice, (Integer) null, isSpecial);
    }

    public void setNForXDollarSpecial(String name, int packageQuantity, BigDecimal packagePrice, Integer limit, boolean isSpecial){
        Item item = findItem(name);
        item.setSpecial(isSpecial);
        item.setSpecialPrice(new NForXDollar(packageQuantity, packagePrice, limit));
    }

    //NForXDollar: weight params
    public void setNForXDollarSpecial(String name, double packageWeight, BigDecimal packagePrice, boolean isSpecial){
        setNForXDollarSpecial(name, packageWeight, packagePrice, null, isSpecial);
    }
    public void setNForXDollarSpecial(String name, double packageWeight, BigDecimal packagePrice, Double limit, boolean isSpecial){
        Item item = findItem(name);
        item.setSpecial(isSpecial);
        item.setSpecialPrice(new NForXDollar(packageWeight, packagePrice, limit));
    }


    //BuyNGetMAtAPercentage: quantity params
    public void setBuyNGetMAtAPercentage(String name, int initialQuantity, int freeQuantity, boolean isSpecial){ //in quantity
        setBuyNGetMAtAPercentage(name, initialQuantity, freeQuantity, null, isSpecial);
    }

    public void setBuyNGetMAtAPercentage(String name, int initialQuantity, int freeQuantity, Double discountPercentage, boolean isSpecial){
        setBuyNGetMAtAPercentage(name, initialQuantity, freeQuantity, discountPercentage, (Integer)null, isSpecial);
    }

    public void setBuyNGetMAtAPercentage(String name, int initialQuantity, int freeQuantity, Double discountPercentage, Integer limit, boolean isSpecial){
        Item item = findItem(name);
        item.setSpecial(isSpecial);
        item.setSpecialPrice(new BuyNGetMAtAPercentage(initialQuantity, freeQuantity, discountPercentage, limit));
    }

    //uyNGetMAtAPercentage: weight params
    public void setBuyNGetMAtAPercentage(String name, double initialWeight, double freeWeight, boolean isSpecial){ //in quantity
        setBuyNGetMAtAPercentage(name, initialWeight, freeWeight, null, isSpecial);
    }

    public void setBuyNGetMAtAPercentage(String name, double initialWeight, double freeWeight, Double discountPercentage, boolean isSpecial){
        setBuyNGetMAtAPercentage(name, initialWeight, freeWeight, discountPercentage, null, isSpecial);
    }

    public void setBuyNGetMAtAPercentage(String name, double initialWeight, double freeWeight, Double discountPercentage, Double limit, boolean isSpecial){
        Item item = findItem(name);
        item.setSpecial(isSpecial);
        item.setSpecialPrice(new BuyNGetMAtAPercentage(initialWeight, freeWeight, discountPercentage, limit));
    }

}
