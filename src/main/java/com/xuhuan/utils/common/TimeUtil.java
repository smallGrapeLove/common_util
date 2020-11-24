package com.xuhuan.utils.common;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author huan.xu
 * @Time 2019-2-13
 */
public class TimeUtil {

    private static final Logger logger = Logger.getLogger(TimeUtil.class);

    private static final String[] NUMBERS = new String[]{"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

    public static String formatDate(Date date, String format) {
        try {
            if (StringUtil.isStrEmpty(format)) {
                format = "yyyy-MM-dd";
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        } catch (Exception var3) {
            return "";
        }
    }

    public static Date formatDate(String date, String format) {
        if (StringUtils.isBlank(format)) {
            format = "yyyy-MM-dd";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date sysdate = null;
        if (StringUtils.isBlank(date)) {
            return null;
        } else {
            try {
                sysdate = dateFormat.parse(date);
            } catch (ParseException var5) {
                logger.error("日期格式化错误", var5);
            }

            return sysdate;
        }
    }

    public static String formatDateToString(String date, String format) {
        if (StringUtil.isStrEmpty(format)) {
            format = "yyyy-MM-dd";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar var3 = Calendar.getInstance();

        try {
            return dateFormat.format(dateFormat.parse(date));
        } catch (Exception var5) {
            logger.error("Convert String to Date Error,check String format.");
            return "";
        }
    }

    public static int compareDate(String str1, String str2) {
        int result = 0;
        boolean b1 = StringUtil.isNotBlank(str1);
        boolean b2 = StringUtil.isNotBlank(str2);
        boolean b = NumberTool.safeToInteger(str2, Integer.valueOf(0)).intValue() - NumberTool.safeToInteger(str1, Integer.valueOf(0)).intValue() > 0;
        if (b1 || b2) {
            if (b) {
                result = 1;
            } else {
                result = -1;
            }
        }

        return result;
    }

    public static String toChinese(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        StringBuffer sb = new StringBuffer();
        sb.append(getSplitDateStr(str, 0)).append("年").append(getSplitDateStr(str, 1)).append("月").append(getSplitDateStr(str, 2)).append("日");
        return sb.toString();
    }

    public static String toChinese(String str) {
        StringBuffer sb = new StringBuffer();
        sb.append(getSplitDateStr(str, 0)).append("年").append(getSplitDateStr(str, 1)).append("月").append(getSplitDateStr(str, 2)).append("日");
        return sb.toString();
    }

    public static String getSplitDateStr(String str, int unit) {
        String[] DateStr = str.split("-");
        if (unit > DateStr.length) {
            unit = 0;
        }

        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < DateStr[unit].length(); ++i) {
            if ((unit == 1 || unit == 2) && Integer.valueOf(DateStr[unit]).intValue() > 9) {
                sb.append(convertNum(DateStr[unit].substring(0, 1))).append("十").append(convertNum(DateStr[unit].substring(1, 2)));
                break;
            }

            sb.append(convertNum(DateStr[unit].substring(i, i + 1)));
        }

        return unit != 1 && unit != 2 ? sb.toString() : sb.toString().replaceAll("^一", "").replace("〇", "");
    }

    private static String convertNum(String str) {
        return NUMBERS[Integer.valueOf(str).intValue()];
    }
}
