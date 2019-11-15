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
    private Integer limit;

    public NForXDollar(int initialPackageDeal, BigDecimal packagePrice, Integer limit) {
        this.packageQuantity = initialPackageDeal;
        this.packagePrice = packagePrice;
        this.limit = limit;
    }

    public NForXDollar(double packageWeight, BigDecimal packagePrice, Integer limit) {
        this.packageWeight = packageWeight;
        this.packagePrice = packagePrice;
        this.limit = limit;
    }

    @Override
    public BigDecimal calculatePrice(Item item, int quantity) {
        BigDecimal remianPrice;
        int count = 0;
        if (quantity >= packageQuantity) { // if qualify for special offers
            if (limit == null) {
                int reminder = quantity % this.packageQuantity;
                remianPrice = item.getItemPrice().multiply(getFormat(reminder));
               count = getCountQuantity(quantity, count);
                return getFormat(count).multiply(this.packagePrice).add(remianPrice);
            } else {
                int remainQuantity = quantity - limit;
                BigDecimal remainPrice = item.getItemPrice().multiply(getFormat(remainQuantity));
                count = getCountQuantity(limit, count);
                return remainPrice.add(getFormat(count).multiply(packagePrice));
            }
        } else { //if not qualify for special offers
            return getFormat(quantity).multiply(item.getItemPrice());
        }
    }

    private int getCountQuantity(int quantity, int count) {
        for(int i = 1; i <= quantity; i++){
            if(i % packageQuantity == 0){
                count++;
            }
        }
        return count;
    }

    @Override
    public BigDecimal calculatePrice(Item item, double weight) {
        BigDecimal tempPrice;
        double count = 0;
        if (weight >= this.packageWeight) { // if qualify for special offers
            if(limit == null){
                double remainWeight = weight % this.packageWeight;
                tempPrice = item.getItemPrice().multiply(getFormat(remainWeight));
                count = getCountWeight(weight, count);
                return BigDecimal.valueOf(count).multiply(this.packagePrice).add(tempPrice);
            }else{
                double remainWeight = weight - (double)limit;
                BigDecimal remainPrice = item.getItemPrice().multiply(getFormat(remainWeight));
                tempPrice = item.getItemPrice().multiply(getFormat(limit % packageQuantity));
                count = getCountWeight(limit, count);
                return remainPrice.add(getFormat(count).multiply(packagePrice).add(tempPrice));
            }

        } else { //if not qualify for special offers
            return getFormat(packageWeight).multiply(item.getItemPrice());
        }
    }

    private double getCountWeight(double weight, double count) {
        for (double i = 0; i <= weight; i++) {
            if (i % this.packageWeight == 0) {
                count++;
            }
        }
        return count;
    }

    protected BigDecimal getFormat(int value) {
        return BigDecimal.valueOf(value).setScale(0, RoundingMode.HALF_UP);
    }

}
