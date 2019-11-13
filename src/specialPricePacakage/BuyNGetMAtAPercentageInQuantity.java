package specialPricePacakage;

import mainPackage.Item;
import mainPackage.formatBigDecimal;

import java.math.BigDecimal;


public class BuyNGetMAtAPercentageInQuantity extends formatBigDecimal {
    private int initialQuantity;
    private int freeQuantity;
    private Double discountPercentage;
    private Integer limitByQuantity;

    public BuyNGetMAtAPercentageInQuantity(int initialQuantity, int freeQuantity, Double discountPercentage, Integer limitByQuantity) {
        this.initialQuantity = initialQuantity;
        this.freeQuantity = freeQuantity;
        this.discountPercentage = discountPercentage;
        this.limitByQuantity = limitByQuantity;
    }

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

}
