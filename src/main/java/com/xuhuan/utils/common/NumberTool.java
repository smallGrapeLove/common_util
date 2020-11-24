package com.xuhuan.utils.common;

import java.math.BigDecimal;

/**
 * 数字工具类
 *
 * @author huan.xu
 * @Time 2019-2-13
 */
public class NumberTool {

    public static Integer safeToInteger(Object o, Integer dv) {
        Integer r = dv;
        if (o != null) {
            try {
                r = (new BigDecimal(String.valueOf(o))).intValue();
            } catch (Exception var4) {
                ;
            }
        }

        return r;
    }

    public static Float safeToFloat(Object o, Float dv) {
        Float r = dv;
        if (o != null) {
            try {
                r = (new BigDecimal(String.valueOf(o))).floatValue();
            } catch (Exception var4) {
                ;
            }
        }

        return r;
    }

    public static Float safeToFloat(Object o, Float dv, int round) {
        Float r = dv;
        if (o != null) {
            try {
                r = (new BigDecimal(String.valueOf(o))).floatValue();
            } catch (Exception var5) {
                ;
            }
        }

        r = (new BigDecimal((double) r.floatValue())).setScale(round, 4).floatValue();
        return r;
    }

    public static Double safeToDouble(Object o, Double dv) {
        Double r = dv;
        if (o != null) {
            try {
                r = (new BigDecimal(String.valueOf(o))).doubleValue();
            } catch (Exception var4) {
                ;
            }
        }

        return r;
    }

    public static Double safeToDouble(Object o, Double dv, int round) {
        Double r = dv;
        if (o != null) {
            try {
                r = (new BigDecimal(String.valueOf(o))).doubleValue();
            } catch (Exception var5) {
                ;
            }
        }

        r = (new BigDecimal(r.doubleValue())).setScale(round, 4).doubleValue();
        return r;
    }

    public static BigDecimal stringToBigDecimal(String paraValue) {
        try {
            BigDecimal bDecimal = null;
            return paraValue.indexOf("%") == -1 ? (new BigDecimal(Double.valueOf(paraValue.trim().replace(",", "")).doubleValue())).setScale(4, 4) : (new BigDecimal(Double.valueOf(paraValue.trim().replace("%", "")).doubleValue() / 100.0D)).setScale(4, 4);
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }
}
