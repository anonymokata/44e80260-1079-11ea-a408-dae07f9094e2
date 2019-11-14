package mainPackage;

import specialPricePacakage.Markdown;

import java.math.BigDecimal;

public class Item extends formatBigDecimal {
    private String itemName;
    private int itemQuantity;
    private boolean isWeight;
    private BigDecimal itemPrice;
    private SpecialPrice specialPrice;
//    private double itemWeight;

    public Item(SpecialPrice specialPrice) {
        this.specialPrice = specialPrice;
    }

    public Item(String itemName, int itemQuantity, boolean isWeight, BigDecimal itemPrice) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.isWeight = isWeight;
        this.itemPrice = itemPrice;
    }

    public Item(String itemName, boolean isWeight, BigDecimal itemPrice) {
        this.itemName = itemName;
        this.isWeight = isWeight;
        this.itemPrice = itemPrice;
    }
//    //for quantity
//    public Item(String itemName, int itemQuantity, BigDecimal itemPrice, SpecialPrice specialPrice) {
//        this.itemName = itemName;
//        this.itemQuantity = itemQuantity;
//        this.itemPrice = itemPrice;
//        this.specialPrice = specialPrice;
//    }

//    //for weight
//    public Item(String itemName, double itemWeight, BigDecimal itemPrice, SpecialPrice specialPrice) {
//        this.itemName = itemName;
//        this.itemWeight = itemWeight;
//        this.itemPrice = itemPrice;
//        this.specialPrice = specialPrice;
//    }


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

    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", itemQuantity=" + itemQuantity +
                ", isWeight=" + isWeight +
                ", itemPrice=" + itemPrice +
                '}';
    }
}
