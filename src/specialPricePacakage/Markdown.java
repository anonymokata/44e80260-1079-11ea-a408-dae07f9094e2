package specialPricePacakage;

import mainPackage.Item;
import mainPackage.formatBigDecimal;
import java.math.BigDecimal;


public class Markdown extends formatBigDecimal {
    private BigDecimal markdownPrice;

    public Markdown(BigDecimal markdownPrice) {
        this.markdownPrice = markdownPrice;
    }

    public BigDecimal getItemTotalPriceAfterMarkdown(Item item, int quantity){
        return item.getPrice().getRegularPrice().subtract(this.markdownPrice).multiply(getFormat(quantity));
    }

}
