package mainPackage;

import specialPricePacakage.BuyNGetMAtAPercentageSellPrice;
import specialPricePacakage.Markdown;
import specialPricePacakage.NForXDollar;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Price {
    private BigDecimal regularPrice;
    private BigDecimal markdown;
    private NForXDollar nForXDollar;
    private BuyNGetMAtAPercentageSellPrice buyNGetMAtAPercentageSellPrice;
    private BigDecimal itemTotalPrice;

    public Price() {

    }

    public Price(BigDecimal regularPrice) {
        this.regularPrice = regularPrice;
    }

    public BigDecimal getRegularPrice() {
        return regularPrice;
    }

    public BigDecimal getMarkdown() {
        return markdown;
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


    private BigDecimal getFormat(int value){
        return BigDecimal.valueOf(value).setScale(0, RoundingMode.HALF_UP);
    }
}
