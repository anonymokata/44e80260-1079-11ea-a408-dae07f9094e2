package specialPricePacakage;

import mainPackage.Item;
import mainPackage.formatBigDecimal;
import java.math.BigDecimal;

public class BuyNGetMAtAPercentageInWeight extends formatBigDecimal {
    private double initialWeight;
    private double freeWeight;
    private double discountPercentage;


    public BuyNGetMAtAPercentageInWeight(double initialWeight, double freeWeight, double discountPercentage) {
        this.initialWeight = initialWeight;
        this.freeWeight = freeWeight;
        this.discountPercentage = discountPercentage;
    }

    public BigDecimal calculatePrice(Item item, double weight){
        double weightCount = 0;
        double specialWeightCount = 0;
        double tempWeight;

        if ((this.initialWeight + this.freeWeight > weight)) { // if not qualify for free offers
            return getFormat(weight).multiply(item.getItemPrice());
        } else {   // if qualify for free offers
            tempWeight = weight;
            specialWeightCount = getSpecialCounter(tempWeight, specialWeightCount);
            weightCount = weight - specialWeightCount;

            return getItemTotalPrice(item, weightCount, specialWeightCount);

        }
    }

    private BigDecimal getPriceForThoseItemsQualifySpecialOffer(Item item, double specialWeightCounter) {
        return getFormat(getFormat(specialWeightCounter).multiply(BigDecimal.valueOf((100 - this.discountPercentage) / 100)));
    }

    private BigDecimal getItemTotalPrice(Item item, double counter, double specialWeightCounter) {
        return item.getItemPrice().multiply(getFormat(counter)).add(getPriceForThoseItemsQualifySpecialOffer(item, specialWeightCounter));
    }

    private double getSpecialCounter(double weight, double specialCount) {
        for (double i = 1; i <= weight; i++) {
            if (i % (this.initialWeight + this.freeWeight) == 0) {
                specialCount += this.freeWeight;
            }
        }
        return specialCount;
    }
}
