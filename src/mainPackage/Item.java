package mainPackage;

import specialPricePacakage.Markdown;

import java.math.BigDecimal;

public class Item extends formatBigDecimal {
    private String itemName;
    private boolean isWeight;
    private BigDecimal itemPrice;
    private SpecialPrice specialPrice;
    private boolean isSpecial;

    public Item(String itemName, boolean isWeight, BigDecimal itemPrice, Boolean isSpecial) {
        this.itemName = itemName;
        this.isWeight = isWeight;
        this.itemPrice = itemPrice;
        this.isSpecial = isSpecial;
    }

    //get price without any special -> in quantity
    public BigDecimal calculatePrice(Item item, int quantity) {
        return getFormat(quantity).multiply(item.getItemPrice());
    }

    //get price without any special -> in weight
    public BigDecimal calculatePrice(Item item, double weight) {
        return getFormat(weight).multiply(item.getItemPrice());
    }

    public String getItemName() {
        return itemName;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public SpecialPrice getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(SpecialPrice specialPrice) {
        this.specialPrice = specialPrice;
    }

    public boolean isInWeight() {
        return isWeight;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setSpecial(boolean special) {
        isSpecial = special;
    }

    //for debug
    @Override
    public String toString() {
        return "<Name:"+itemName + "              weighted:" + isWeight +"       $" + itemPrice +">";
    }
}
