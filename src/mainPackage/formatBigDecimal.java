package mainPackage;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class formatBigDecimal {

    protected BigDecimal getFormat(int value){
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

    //overload
    protected BigDecimal getFormat(double value){
       return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

    protected  BigDecimal getFormat(BigDecimal value){
        return value.setScale(2, RoundingMode.HALF_UP);
    }

}
