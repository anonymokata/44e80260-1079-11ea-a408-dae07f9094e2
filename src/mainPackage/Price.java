package mainPackage;

import specialPricePacakage.BuyNGetMAtAPercentageSellPrice;
import specialPricePacakage.MarkdownSellPrice;
import specialPricePacakage.NForXDollarSellPrice;
import specialPricePacakage.RegularSellPrice;

import java.math.BigDecimal;

public class Price {
    private BigDecimal regularPrice;
    private MarkdownSellPrice markdownSellPrice;
    private NForXDollarSellPrice nForXDollarSellPrice;
    private BuyNGetMAtAPercentageSellPrice buyNGetMAtAPercentageSellPrice;

    public Price(BigDecimal regularPrice) {
        this.regularPrice = regularPrice;
    }

    public BigDecimal getRegularPrice() {
        return regularPrice;
    }

    public MarkdownSellPrice getMarkdownSellPrice() {
        return markdownSellPrice;
    }

    public NForXDollarSellPrice getnForXDollarSellPrice() {
        return nForXDollarSellPrice;
    }

    public BuyNGetMAtAPercentageSellPrice getBuyNGetMAtAPercentageSellPrice() {
        return buyNGetMAtAPercentageSellPrice;
    }
}
