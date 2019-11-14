package specialPricePacakage;

import mainPackage.Item;
import mainPackage.SpecialPrice;
import mainPackage.formatBigDecimal;

import java.math.BigDecimal;


public class BuyNGetMAtAPercentage extends formatBigDecimal implements SpecialPrice {
    private int initialQuantity;
    private int freeQuantity;
    private double initialWeight;
    private double freeWeight;
    private Double discountPercentage;
    private Integer limitByQuantity;

    public BuyNGetMAtAPercentage(int initialQuantity, int freeQuantity, Double discountPercentage, Integer limitByQuantity) {
        this.initialQuantity = initialQuantity;
        this.freeQuantity = freeQuantity;
        if (discountPercentage == null) {
            this.discountPercentage = 100.00; // the free item is 100% for free
        } else {
            this.discountPercentage = discountPercentage;
        }
        this.limitByQuantity = limitByQuantity;
    }

    public BuyNGetMAtAPercentage(double initialWeight, double freeWeight, Double discountPercentage, Integer limitByQuantity) {
        this.initialWeight = initialWeight;
        this.freeWeight = freeWeight;
        this.discountPercentage = discountPercentage;
        this.limitByQuantity = limitByQuantity;
    }

    /*---------------------calculate price in quantity methods--------------------------*/
    @Override
    public BigDecimal calculatePrice(Item item, int quantity) {
        int counter = 0;
        int specialCounter = 0;
        int tempQuantity = 0;
        if (initialQuantity + freeQuantity > quantity) { //if not qualify for special offers
            return item.getItemPrice().multiply(getFormat(quantity));

        } else {    //if qualify for special offers
            if (this.limitByQuantity == null) { //without special offer limitation
                tempQuantity = quantity;
                specialCounter = getSpecialCounter(tempQuantity, specialCounter);
                counter = quantity - specialCounter;
                return getItemTotalPrice(item, counter, specialCounter);
            } else {
                tempQuantity = this.limitByQuantity; //with item limitation
                specialCounter = getSpecialCounter(tempQuantity, specialCounter);
                counter = quantity - specialCounter;

                return getItemTotalPrice(item, counter, specialCounter);
            }

        }

    }
    private int getSpecialCounter(int quantity, int specialCounter) {
        for (int i = 1; i <= quantity; i++) {
            if (i % (this.initialQuantity + this.freeQuantity) == 0) {
                specialCounter += this.freeQuantity;
            }
        }
        return specialCounter;
    }

    private BigDecimal getItemTotalPrice(Item item, int counter, int specialCounter) {
        return item.getItemPrice().multiply(getFormat(counter)).add(calculatePriceQuantifySpecialOffer(item, specialCounter));

    }

    private BigDecimal calculatePriceQuantifySpecialOffer(Item item, int specialCounter) {
        return getFormat(specialCounter).multiply(item.getItemPrice().multiply(BigDecimal.valueOf((100 - this.discountPercentage) / 100)));
    }
    /*-------------------------------------------------------------------------------------------*/


    /*---------------------calculate price in weight methods--------------------------*/
    @Override
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

    private BigDecimal getPriceForThoseItemsQualifySpecialOffer(Item item, double specialWeightCounter) {
        return item.getItemPrice().multiply(getFormat(getFormat(specialWeightCounter)).multiply(BigDecimal.valueOf((100 - this.discountPercentage) / 100)));
    }
    /*---------------------------------------------------------------------------------*/

}
