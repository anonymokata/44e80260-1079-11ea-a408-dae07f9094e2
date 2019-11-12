package mainPackage;

public class Item {
    private String itemName;
    private int itemQuantity;
    private double itemWeight;
    private Price price;

    public Item(String itemName, int itemQuantity, Price price) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.price = price;
    }

    public Item(String itemName, double itemWeight, Price price) {
        this.itemName = itemName;
        this.itemWeight = itemWeight;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public double getItemWeight() {
        return itemWeight;
    }

    public Price getPrice() {
        return price;
    }

}
