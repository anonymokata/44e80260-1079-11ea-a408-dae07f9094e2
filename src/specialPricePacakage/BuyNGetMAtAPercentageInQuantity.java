package specialPricePacakage;

import mainPackage.Item;
import mainPackage.Price;
import mainPackage.formatBigDecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BuyNGetMAtAPercentageInQuantity extends formatBigDecimal {
    private int initialQuantity;
    private int freeQuantity;
    private Integer limitByQuantity;
    private double discountPercentage;

    public BuyNGetMAtAPercentageInQuantity(int initialQuantity, int freeQuantity, Integer limitByQuantity, double discountPercentage) {
        this.initialQuantity = initialQuantity;
        this.freeQuantity = freeQuantity;
        this.limitByQuantity = limitByQuantity;
        this.discountPercentage = discountPercentage;
    }

    public BigDecimal calculateThisItemTotalPrice(Item item, int quantity) {
        int counter = 0;
        int specialCounter = 0;
        int tempQuantity = 0;
        if (initialQuantity + freeQuantity > quantity) { //if not qualify for special offers
           return item.getPrice().getRegularPrice().multiply(getFormat(quantity));

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
        return item.getPrice().getRegularPrice().multiply(getFormat(counter)).add(calculatePriceQuantifySpecialOffer(item, specialCounter));

    }

    private BigDecimal calculatePriceQuantifySpecialOffer(Item item, int specialCounter) {
        return getFormat(specialCounter).multiply(item.getPrice().getRegularPrice().multiply(BigDecimal.valueOf((100 - this.discountPercentage) / 100)));
    }

}
