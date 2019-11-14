//package specialPricePacakage;
//
//import mainPackage.Item;
//import mainPackage.SpecialPrice;
//import mainPackage.formatBigDecimal;
//import java.math.BigDecimal;
//
//public class BuyNGetMAtAPercentageInWeight extends formatBigDecimal implements SpecialPrice {
//    private double initialWeight;
//    private double freeWeight;
//    private Double discountPercentage;
//    private Boolean isSpecialInWeight = false;
//
//
//    public BuyNGetMAtAPercentageInWeight(double initialWeight, double freeWeight, Double discountPercentage, boolean isSpecialInWeight) {
//        this.initialWeight = initialWeight;
//        this.freeWeight = freeWeight;
//        if(discountPercentage == null){
//            this.discountPercentage = 100.00; // 100% for free
//        }else {
//            this.discountPercentage = discountPercentage;
//        }
//        this.isSpecialInWeight = isSpecialInWeight;
//    }
//
//    @Override
//    public BigDecimal calculatePrice(Item item, int quantity) {
//        return null;
//    }
//
//    @Override
//    public BigDecimal calculatePrice(Item item, double weight){
//        double weightCount = 0;
//        double specialWeightCount = 0;
//        double tempWeight;
//
//        if ((this.initialWeight + this.freeWeight > weight)) { // if not qualify for free offers
//            return getFormat(weight).multiply(item.getItemPrice());
//        } else {   // if qualify for free offers
//            tempWeight = weight;
//            specialWeightCount = getSpecialCounter(tempWeight, specialWeightCount);
//            weightCount = weight - specialWeightCount;
//
//            return getItemTotalPrice(item, weightCount, specialWeightCount);
//
//        }
//    }
//
//    private BigDecimal getItemTotalPrice(Item item, double counter, double specialWeightCounter) {
//        return item.getItemPrice().multiply(getFormat(counter)).add(getPriceForThoseItemsQualifySpecialOffer(item, specialWeightCounter));
//    }
//
//    private double getSpecialCounter(double weight, double specialCount) {
//        for (double i = 1; i <= weight; i++) {
//            if (i % (this.initialWeight + this.freeWeight) == 0) {
//                specialCount += this.freeWeight;
//            }
//        }
//        return specialCount;
//    }
//
//    private BigDecimal getPriceForThoseItemsQualifySpecialOffer(Item item, double specialWeightCounter) {
//        return item.getItemPrice().multiply(getFormat(getFormat(specialWeightCounter)).multiply(BigDecimal.valueOf((100 - this.discountPercentage) / 100)));
//    }
//
//    public Boolean isSpecialInWeight() {
//        return isSpecialInWeight;
//    }
//}
