package com.code.salesappbackend.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoundData {
    public static Double round(Double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
