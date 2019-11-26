package specialPricePacakage;

import mainPackage.FormattingBigDecimal;
import mainPackage.Item;
import mainPackage.SpecialPrice;
import java.math.BigDecimal;

public class NForXDollar extends FormattingBigDecimal implements SpecialPrice {
    private int packageQuantity;
    private double packageWeight;
    private BigDecimal packagePrice;
    private Integer limitQuantity;
    private Double limitWeight;

    public NForXDollar(int initialPackageDeal, BigDecimal packagePrice, Integer limitQuantity) {
        this.packageQuantity = initialPackageDeal;
        this.packagePrice = packagePrice;
        this.limitQuantity = limitQuantity;
    }

    public NForXDollar(double packageWeight, BigDecimal packagePrice, Double limitWeight) {
        this.packageWeight = packageWeight;
        this.packagePrice = packagePrice;
        this.limitWeight = limitWeight;
    }

    @Override
    public BigDecimal calculatePrice(Item item, int quantity) {
        BigDecimal remainingPrice;
        int count = 0;
        if (quantity >= packageQuantity) { // if qualify for special offers
            if (limitQuantity == null) {
                int reminder = quantity % this.packageQuantity;
                remainingPrice = item.getItemPrice().multiply(getFormat(reminder));
                count = getQuantityCount(quantity, count);
                return getFormat(count).multiply(this.packagePrice).add(remainingPrice);
            } else {
                int remainingQuantity = quantity - limitQuantity;
                remainingPrice = item.getItemPrice().multiply(getFormat(remainingQuantity));
                count = getQuantityCount(limitQuantity, count);
                return remainingPrice.add(getFormat(count).multiply(packagePrice));
            }
        } else { //if not qualify for special offers
            return getFormat(quantity).multiply(item.getItemPrice());
        }
    }

    private int getQuantityCount(int quantity, int count) {
        for (int i = 1; i <= quantity; i++) {
            if (i % packageQuantity == 0) {
                count++;
            }
        }
        return count;
    }

    @Override
    public BigDecimal calculatePrice(Item item, double weight) {
        BigDecimal remainingPrice;
        double count = 0;
        double remainingWeight = 0;
        if (weight >= this.packageWeight) { // if qualify for special offers
            if (limitWeight == null) {
                remainingWeight = weight % this.packageWeight;
                remainingPrice = item.getItemPrice().multiply(getFormat(remainingWeight));
                count = getWeightCount(weight, count);
                return BigDecimal.valueOf(count).multiply(this.packagePrice).add(remainingPrice);
            } else {
                remainingWeight = weight - limitWeight;
                remainingPrice = item.getItemPrice().multiply(getFormat(remainingWeight));
                count = (weight - remainingWeight) / packageWeight;
                return remainingPrice.add(getFormat(count).multiply(packagePrice));
            }

        } else { //if not qualify for special offers
            return getFormat(packageWeight).multiply(item.getItemPrice());
        }
    }

    private double getWeightCount(double weight, double count) {
        for (double i = 1; i <= weight; i++) {
            if (i % this.packageWeight == 0) {
                count++;
            }
        }
        return count;
    }
}
