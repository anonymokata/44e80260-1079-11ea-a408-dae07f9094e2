package mainPackage;

import specialPricePacakage.*;

public class SpecialPrice {
    private Markdown markdown;
    private NForXDollar nForXDollar;
    private BuyNGetMAtAPercentageInQuantity quantitySpecial;
    private BuyNGetMAtAPercentageInWeight weightSpecial;

    public SpecialPrice(Markdown markdown) {
        this.markdown = markdown;
    }

    public SpecialPrice(NForXDollar nForXDollar) {
        this.nForXDollar = nForXDollar;
    }

    public SpecialPrice(BuyNGetMAtAPercentageInQuantity quantitySpecial) {
        this.quantitySpecial = quantitySpecial;
    }

    public SpecialPrice(BuyNGetMAtAPercentageInWeight weightSpecial) {
        this.weightSpecial = weightSpecial;
    }
//
//    public void getPrice(){
//
//    }

    public Markdown getMarkdown() {
        return markdown;
    }

    public NForXDollar getnForXDollar() {
        return nForXDollar;
    }

//    public void setMarkdown(Markdown markdown) {
//        this.markdown = markdown;
//    }
//
//    public void setnForXDollar(NForXDollar nForXDollar) {
//        this.nForXDollar = nForXDollar;
//    }

//    public void setQuantitySpecial(BuyNGetMAtAPercentageInQuantity quantitySpecial) {
//        this.quantitySpecial = quantitySpecial;
//    }
//
//    public void setWeightSpecial(BuyNGetMAtAPercentageInWeight weightSpecial) {
//        this.weightSpecial = weightSpecial;
//    }

    public BuyNGetMAtAPercentageInQuantity getQuantitySpecial() {
        return quantitySpecial;
    }

    public BuyNGetMAtAPercentageInWeight getWeightSpecial() {
        return weightSpecial;
    }
}
