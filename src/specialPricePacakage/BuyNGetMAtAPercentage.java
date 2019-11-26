package specialPricePacakage;

import mainPackage.Item;
import mainPackage.SpecialPrice;
import mainPackage.FormattingBigDecimal;

import java.math.BigDecimal;

public class BuyNGetMAtAPercentage extends FormattingBigDecimal implements SpecialPrice {
    private int initialQuantity;
    private int discountQuantity;
    private double initialWeight;
    private double discountWeight;
    private Double discountPercentage;
    private Integer limitByQuantity;
    private Double limitByWeight;

    public BuyNGetMAtAPercentage(int initialQuantity, int discountQuantity, Double discountPercentage, Integer limitByQuantity) {
        this.initialQuantity = initialQuantity;
        this.discountQuantity = discountQuantity;
        getNullDiscountPercentage(discountPercentage);
        this.limitByQuantity = limitByQuantity;
    }

    public BuyNGetMAtAPercentage(double initialWeight, double discountWeight, Double discountPercentage, Double limitByWeight) {
        this.initialWeight = initialWeight;
        this.discountWeight = discountWeight;
        getNullDiscountPercentage(discountPercentage);
        this.limitByWeight = limitByWeight;
    }


    private void getNullDiscountPercentage(Double discountPercentage) {
        if (discountPercentage == null) {
            this.discountPercentage = 100.00; // the free item is 100% for free
        } else {
            this.discountPercentage = discountPercentage;
        }
    }


    /*---------------------calculate price in quantity methods--------------------------*/
    @Override
    public BigDecimal calculatePrice(Item item, int quantity) {
        int remainingQuantity = 0;
        int specialQuantityCounter = 0;
        if (initialQuantity + discountQuantity > quantity) { //if not qualify for special offers
            return item.getItemPrice().multiply(getFormat(quantity));

        } else {    //if qualify for special offers
            if (this.limitByQuantity == null) { //without limitation
                specialQuantityCounter = getSpecialCounter(quantity, specialQuantityCounter);
                remainingQuantity = quantity - (specialQuantityCounter * (this.initialQuantity + this.discountQuantity));
                return getItemTotalPrice(item, remainingQuantity, specialQuantityCounter);
            } else {
                specialQuantityCounter = limitByQuantity / (this.initialQuantity + this.discountQuantity);
                remainingQuantity = quantity - limitByQuantity;
                return getItemTotalPrice(item, remainingQuantity, specialQuantityCounter);
            }

        }

    }
    private int getSpecialCounter(int quantity, int specialCounter) {
        for (int i = 1; i <= quantity; i++) {
            if (i % (this.initialQuantity + this.discountQuantity) == 0) {
                specialCounter += this.discountQuantity;
            }
        }
        return specialCounter;
    }

    private BigDecimal getItemTotalPrice(Item item, int remainingQuantity, int specialQuantityCounter) {
        BigDecimal discountedPrice = getPriceForDiscountItems(item, specialQuantityCounter);
        BigDecimal priceWithoutDiscount = item.getItemPrice().multiply(getFormat(this.initialQuantity).multiply(getFormat(specialQuantityCounter)));
        return priceWithoutDiscount.add(getFormat(remainingQuantity).multiply(item.getItemPrice())).add(discountedPrice);
    }

    private BigDecimal getPriceForDiscountItems(Item item, int specialQuantityCounter) {
        return getFormat(specialQuantityCounter).multiply(getFormat(this.discountQuantity).multiply(item.getItemPrice().multiply(BigDecimal.valueOf((100 - this.discountPercentage) / 100))));
    }


    /*---------------------calculate price in weight methods------------------------------------*/
    @Override
    public BigDecimal calculatePrice(Item item, double weight) {
        double remainingWeight = 0;
        double specialWeightCount = 0;
        BigDecimal remainPrice;

        if ((this.initialWeight + this.discountWeight > weight)) { // if not qualify for free offers
            return getFormat(weight).multiply(item.getItemPrice());
        } else {   // if qualify for free offers
            if (limitByWeight == null) { // without limitation
                specialWeightCount = getSpecialCounter(weight, specialWeightCount);
                remainingWeight = weight - (specialWeightCount * (this.initialWeight + this.discountWeight));
                return getItemTotalPrice(item, remainingWeight, specialWeightCount);
            } else {
                specialWeightCount = limitByWeight / (initialWeight + discountWeight);
                remainingWeight = weight - limitByWeight;
                return getItemTotalPrice(item, remainingWeight, specialWeightCount);
            }
        }
    }

    private BigDecimal getItemTotalPrice(Item item, double remainingWeight, double specialWeightCounter) {
        BigDecimal discountedPrice = getPriceForDiscountItems(item, specialWeightCounter);
        BigDecimal priceWithoutDiscount = item.getItemPrice().multiply(getFormat(this.initialWeight)).multiply(getFormat(specialWeightCounter));
        return priceWithoutDiscount.add(getFormat(remainingWeight).multiply(item.getItemPrice())).add(discountedPrice);
    }

    private double getSpecialCounter(double weight, double specialCount) {
        for (double i = 1; i <= weight; i += 0.01) {
            if (getFormat(i).doubleValue() % (this.initialWeight + this.discountWeight) == 0) {
                specialCount++;
            }
        }
        return specialCount;
    }

    private BigDecimal getPriceForDiscountItems(Item item, double specialWeightCounter) {
        return getFormat(specialWeightCounter).multiply(getFormat(this.discountWeight).multiply(item.getItemPrice().multiply(BigDecimal.valueOf((100 - this.discountPercentage) / 100))));
    }

}
