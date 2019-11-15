package specialPricePacakage;

import mainPackage.FormatBigDecimal;
import mainPackage.Item;
import mainPackage.SpecialPrice;
import java.math.BigDecimal;

public class NForXDollar extends FormatBigDecimal implements SpecialPrice {

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
        BigDecimal remainPrice;
        int count = 0;
        if (quantity >= packageQuantity) { // if qualify for special offers
            if (limitQuantity == null) {
                int reminder = quantity % this.packageQuantity;
                remainPrice = item.getItemPrice().multiply(getFormat(reminder));
                count = getCountQuantity(quantity, count);
                return getFormat(count).multiply(this.packagePrice).add(remainPrice);
            } else {
                int remainQuantity = quantity - limitQuantity;
                remainPrice = item.getItemPrice().multiply(getFormat(remainQuantity));
                count = getCountQuantity(limitQuantity, count);
                return remainPrice.add(getFormat(count).multiply(packagePrice));
            }
        } else { //if not qualify for special offers
            return getFormat(quantity).multiply(item.getItemPrice());
        }
    }

    private int getCountQuantity(int quantity, int count) {
        for (int i = 1; i <= quantity; i++) {
            if (i % packageQuantity == 0) {
                count++;
            }
        }
        return count;
    }

    @Override
    public BigDecimal calculatePrice(Item item, double weight) {
        BigDecimal remainPrice;
        double count = 0;
        if (weight >= this.packageWeight) { // if qualify for special offers
            if (limitWeight == null) {
                double remainWeight = weight % this.packageWeight;
                remainPrice = item.getItemPrice().multiply(getFormat(remainWeight));
                count = getCountWeight(weight, count);
                return BigDecimal.valueOf(count).multiply(this.packagePrice).add(remainPrice);
            } else {
                double remainWeight = weight - (double) limitWeight;
                remainPrice = item.getItemPrice().multiply(getFormat(remainWeight));
                count = (weight - remainWeight) / packageWeight;
                return remainPrice.add(getFormat(count).multiply(packagePrice));
            }

        } else { //if not qualify for special offers
            return getFormat(packageWeight).multiply(item.getItemPrice());
        }
    }

    private double getCountWeight(double weight, double count) {
        for (double i = 1; i <= weight; i++) {
            if (i % this.packageWeight == 0) {
                count++;
            }
        }
        return count;

    }
}
