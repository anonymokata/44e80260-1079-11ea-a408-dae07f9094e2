package mainPackage;

import specialPricePacakage.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Price extends formatBigDecimal{
    private BigDecimal regularPrice;
    private Markdown markdown;
    private NForXDollar nForXDollar;
    private BuyNGetMAtAPercentageInQuantity quantitySpecial;
    private BuyNGetMAtAPercentageInWeight weightSpecial;

    public Price(BigDecimal regularPrice) {
        this.regularPrice = regularPrice;
    }

    public BigDecimal getRegularPrice() {
        return regularPrice;
    }

    public NForXDollar getNForXDollar() {
        return nForXDollar;
    }

    public BigDecimal getItemRegularTotalPrice(Item item, int quantity) {
        return getFormat(quantity).multiply(item.getPrice().getRegularPrice());
    }

    public Markdown getMarkdown() {
        return markdown;
    }

    public void setMarkdown(Markdown markdown) {
        this.markdown = markdown;
    }

    public void setNForXDollar(NForXDollar nForXDollar) {
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
