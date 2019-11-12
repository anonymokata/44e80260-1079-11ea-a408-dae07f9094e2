package specialPricePacakage;

import mainPackage.Item;
import mainPackage.Price;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NForXDollar {
    private BigDecimal packagePrice;
    private double initialPackageDeal;
    private BigDecimal itemTotalPrice;

    public NForXDollar(BigDecimal packagePrice, double initialPackageDeal) {
        this.packagePrice = packagePrice;
        this.initialPackageDeal = initialPackageDeal;
    }


    public BigDecimal calculateThisItemTotalPrice(Item item, int quantity){
        BigDecimal tempPrice;
        int count = 0;
        if (quantity >= this.initialPackageDeal) { // if qualify for special offers
            int reminder = (int) (quantity % this.initialPackageDeal);
            tempPrice = item.getPrice().getRegularPrice().multiply(getFormat(reminder));
            for (int i = 1; i <= quantity; i++) {
                if (i % this.initialPackageDeal == 0) {
                    count++;
                }
            }
            this.itemTotalPrice = BigDecimal.valueOf(count).multiply(this.packagePrice).add(tempPrice);
        } else { //if not qualify for special offers
            count = quantity;
            this.itemTotalPrice = getFormat(quantity).multiply(item.getPrice().getRegularPrice());
        }

        return this.itemTotalPrice;
    }

    protected BigDecimal getFormat(int value){
        return BigDecimal.valueOf(value).setScale(0, RoundingMode.HALF_UP);
    }

}
