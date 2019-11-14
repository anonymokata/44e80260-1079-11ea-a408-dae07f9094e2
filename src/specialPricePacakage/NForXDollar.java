package specialPricePacakage;

import mainPackage.Item;
import mainPackage.SpecialPrice;
import mainPackage.formatBigDecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NForXDollar extends formatBigDecimal implements SpecialPrice {

    private int packageQuantity;
    private double packageWeight;
    private BigDecimal packagePrice;

    public NForXDollar(int initialPackageDeal, BigDecimal packagePrice) {
        this.packageQuantity = initialPackageDeal;
        this.packagePrice = packagePrice;
    }

    public NForXDollar(double packageWeight, BigDecimal packagePrice) {
        this.packageWeight = packageWeight;
        this.packagePrice = packagePrice;
    }

    @Override
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
            return BigDecimal.valueOf(count).multiply(this.packagePrice).add(tempPrice);
        } else { //if not qualify for special offers
            count = quantity;
            return getFormat(quantity).multiply(item.getItemPrice());
        }
    }

    @Override
    public BigDecimal calculatePrice(Item item, double weight) {
        BigDecimal remainPrice;
        double count = 0;
        if (weight >= this.packageWeight) { // if qualify for special offers
            double remainWeight = weight % this.packageWeight;
            remainPrice = item.getItemPrice().multiply(getFormat(remainWeight));
            for(double i = 0; i <= weight; i++) {
                if (i % this.packageWeight == 0) {
                    count++;
                }
            }
            return BigDecimal.valueOf(count).multiply(this.packagePrice).add(remainPrice);
        } else { //if not qualify for special offers
            return getFormat(packageWeight).multiply(item.getItemPrice());
        }
    }

    protected BigDecimal getFormat(int value){
        return BigDecimal.valueOf(value).setScale(0, RoundingMode.HALF_UP);
    }

}
