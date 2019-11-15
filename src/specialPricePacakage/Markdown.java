package specialPricePacakage;

import mainPackage.Item;
import mainPackage.SpecialPrice;
import mainPackage.FormatBigDecimal;
import java.math.BigDecimal;


public class Markdown extends FormatBigDecimal implements SpecialPrice {
    private BigDecimal markdownPrice;


    public Markdown(BigDecimal markdownPrice) {
        this.markdownPrice = markdownPrice;

    }

    public BigDecimal calculatePrice(Item item, int quantity){
        return (item.getItemPrice().subtract(this.markdownPrice)).multiply(getFormat(quantity));
    }

    public BigDecimal calculatePrice(Item item, double weight){
        return (item.getItemPrice().subtract(this.markdownPrice)).multiply(getFormat(weight));
    }

}
