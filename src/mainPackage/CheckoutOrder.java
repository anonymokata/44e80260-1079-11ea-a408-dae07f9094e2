package mainPackage;

public class CheckoutOrder {

    private Inventory inventory;

    public CheckoutOrder(Inventory inventory) {
        this.inventory = inventory;
    }

    public Item scanItem(String name) {
        return inventory.findAnItem(name);
    }


}
