package mainPackage;

import specialPricePacakage.BuyNGetMAtAPercentage;
import specialPricePacakage.Markdown;
import specialPricePacakage.NForXDollar;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Item> itemInventory = new HashMap<>();

    public void storeItems(Item item) {
        this.itemInventory.put(item.getItemName(), item);
    }

    public Item findItem(String name){
        if(itemInventory.containsKey(name)){
            return itemInventory.get(name);
        }else{
            throw new RuntimeException(name + " is not found, please contact the clerk.");
        }
    }

    public void setMarkdown(String name, BigDecimal markdownPrice, boolean isSpecial){
        Item item = findItem(name);
        item.setSpecial(isSpecial);
        item.setSpecialPrice(new Markdown(markdownPrice));
    }

    /*--------methods of  NForXDollar:  params in quantity --------*/
    public void setNForXDollarSpecial(String name, int packageQuantity, BigDecimal packagePrice, boolean isSpecial){
      setNForXDollarSpecial(name, packageQuantity, packagePrice, (Integer) null, isSpecial);
    }

    public void setNForXDollarSpecial(String name, int packageQuantity, BigDecimal packagePrice, Integer limit, boolean isSpecial){
        Item item = findItem(name);
        item.setSpecial(isSpecial);
        item.setSpecialPrice(new NForXDollar(packageQuantity, packagePrice, limit));
    }


    /*--------methods of  NForXDollar:  params in weight --------*/
    public void setNForXDollarSpecial(String name, double packageWeight, BigDecimal packagePrice, boolean isSpecial){
        setNForXDollarSpecial(name, packageWeight, packagePrice, null, isSpecial);
    }
    public void setNForXDollarSpecial(String name, double packageWeight, BigDecimal packagePrice, Double limit, boolean isSpecial){
        Item item = findItem(name);
        item.setSpecial(isSpecial);
        item.setSpecialPrice(new NForXDollar(packageWeight, packagePrice, limit));
    }


    /*----------methods of BuyNGetMAtAPercentage: params in quantity ----------*/
    public void setBuyNGetMAtAPercentage(String name, int initialQuantity, int discountQuantity, boolean isSpecial){ //in quantity
        setBuyNGetMAtAPercentage(name, initialQuantity, discountQuantity, null, isSpecial);
    }

    public void setBuyNGetMAtAPercentage(String name, int initialQuantity, int discountQuantity, Double discountPercentage, boolean isSpecial){
        setBuyNGetMAtAPercentage(name, initialQuantity, discountQuantity, discountPercentage, (Integer)null, isSpecial);
    }

    public void setBuyNGetMAtAPercentage(String name, int initialQuantity, int discountQuantity, Double discountPercentage, Integer limit, boolean isSpecial){
        Item item = findItem(name);
        item.setSpecial(isSpecial);
        item.setSpecialPrice(new BuyNGetMAtAPercentage(initialQuantity, discountQuantity, discountPercentage, limit));
    }


    /*----------methods of BuyNGetMAtAPercentage: params in weight ----------*/
    public void setBuyNGetMAtAPercentage(String name, double initialWeight, double discountWeight, boolean isSpecial){ //in quantity
        setBuyNGetMAtAPercentage(name, initialWeight, discountWeight, null, isSpecial);
    }

    public void setBuyNGetMAtAPercentage(String name, double initialWeight, double discountWeight, Double discountPercentage, boolean isSpecial){
        setBuyNGetMAtAPercentage(name, initialWeight, discountWeight, discountPercentage, null, isSpecial);
    }

    public void setBuyNGetMAtAPercentage(String name, double initialWeight, double discountWeight, Double discountPercentage, Double limit, boolean isSpecial){
        Item item = findItem(name);
        item.setSpecial(isSpecial);
        item.setSpecialPrice(new BuyNGetMAtAPercentage(initialWeight, discountWeight, discountPercentage, limit));
    }

    //getter
    public Map<String, Item> getItemInventory() {
        return itemInventory;
    }
}
