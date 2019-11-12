package specialPricePacakage;

import mainPackage.Item;
import mainPackage.Price;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Markdown {
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

    private BigDecimal getFormat(int value){
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }
}
