package specialPricePacakage;

import mainPackage.Item;
import mainPackage.Price;
import mainPackage.formatBigDecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Markdown extends formatBigDecimal {
    private BigDecimal markdownPrice;

    public BigDecimal getPriceAfterMarkdown(Item item, int quantity){
        System.out.println(getFormat(quantity));
        return item.getPrice().getRegularPrice().subtract(this.markdownPrice).multiply(getFormat(quantity));
    }

    public BigDecimal getMarkdownPrice() {
        return this.markdownPrice;
    }

    public void setMarkdownPrice(BigDecimal markdownPrice) {
        this.markdownPrice = markdownPrice;
    }

}
