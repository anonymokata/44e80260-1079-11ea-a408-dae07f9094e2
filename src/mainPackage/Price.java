package mainPackage;

import specialPricePacakage.BuyNGetMAtAPercentageInQuantity;
import specialPricePacakage.BuyNGetMAtAPercentageInWeight;
import specialPricePacakage.NForXDollar;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Price extends formatBigDecimal{
    private BigDecimal regularPrice;
    private BigDecimal markdown;
    private NForXDollar nForXDollar;
    private BuyNGetMAtAPercentageInQuantity quantitySpecial;
    private BuyNGetMAtAPercentageInWeight weightSpecial;

    public Price() {

    }

    public Price(BigDecimal regularPrice) {
        this.regularPrice = regularPrice;
    }

    public BigDecimal getRegularPrice() {
        return regularPrice;
    }

    public NForXDollar getnForXDollar() {
        return nForXDollar;
    }

    public void setMarkdown(BigDecimal markdown) {
        this.markdown = markdown;
    }

    public BigDecimal getItemRegularTotalPrice(Item item, int quantity) {

        return getFormat(quantity).multiply(item.getPrice().getRegularPrice());
    }

    public BigDecimal getItemTotalPriceAfterMarkdown(Item item, int quantity){
        return item.getPrice().getRegularPrice().subtract(this.markdown).multiply(getFormat(quantity));
    }

    public void setRegularPrice(BigDecimal regularPrice) {
        this.regularPrice = regularPrice;
    }

    public BigDecimal getMarkdown() {
        return markdown;
    }

    public void setnForXDollar(NForXDollar nForXDollar) {
        this.nForXDollar = nForXDollar;
    }

    public BuyNGetMAtAPercentageInQuantity getQuantitySpecial() {
        return quantitySpecial;
    }

    public void setQuantitySpecial(BuyNGetMAtAPercentageInQuantity quantitySpecial) {
        this.quantitySpecial = quantitySpecial;
    }

    public BuyNGetMAtAPercentageInWeight getWeightSpecial() {
        return weightSpecial;
    }

    public void setWeightSpecial(BuyNGetMAtAPercentageInWeight weightSpecial) {
        this.weightSpecial = weightSpecial;
    }
}
