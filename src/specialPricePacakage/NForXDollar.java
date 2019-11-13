package specialPricePacakage;

import mainPackage.Item;
import mainPackage.formatBigDecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NForXDollar extends formatBigDecimal {

    private int packageQuantity;
    private BigDecimal packagePrice;
    private BigDecimal itemTotalPrice;

    public NForXDollar(int initialPackageDeal, BigDecimal packagePrice) {
        this.packageQuantity = initialPackageDeal;
        this.packagePrice = packagePrice;
    }

    public BigDecimal calculatePrice(Item item, int quantity){
        BigDecimal tempPrice;
        int count = 0;
        if (quantity >= this.packageQuantity) { // if qualify for special offers
            int reminder = (int) (quantity % this.packageQuantity);
            tempPrice = item.getItemPrice().multiply(getFormat(reminder));
            for (int i = 1; i <= quantity; i++) {
                if (i % this.packageQuantity == 0) {
                    count++;
                }
            }
            this.itemTotalPrice = BigDecimal.valueOf(count).multiply(this.packagePrice).add(tempPrice);
        } else { //if not qualify for special offers
            count = quantity;
            this.itemTotalPrice = getFormat(quantity).multiply(item.getItemPrice());
        }

        return this.itemTotalPrice;
    }

    protected BigDecimal getFormat(int value){
        return BigDecimal.valueOf(value).setScale(0, RoundingMode.HALF_UP);
    }

}
