package mainPackage;

import java.math.BigDecimal;

public interface SpecialPrice {
    BigDecimal calculatePrice(Item item, int quantity);
    BigDecimal calculatePrice(Item item, double weight);
}
