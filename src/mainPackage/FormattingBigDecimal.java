package mainPackage;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FormattingBigDecimal {

    protected BigDecimal getFormat(int i){
        return BigDecimal.valueOf(i).setScale(2, RoundingMode.HALF_UP);
    }

    protected BigDecimal getFormat(double d){
       return BigDecimal.valueOf(d).setScale(2, RoundingMode.HALF_UP);
    }

    protected  BigDecimal getFormat(BigDecimal bd){
        return bd.setScale(2, RoundingMode.HALF_UP);
    }

}
