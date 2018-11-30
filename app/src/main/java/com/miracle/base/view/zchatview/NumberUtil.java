package com.miracle.base.view.zchatview;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * 数字，日期格式化类
 * Created by Administrator on 2018/6/1.
 */

public class NumberUtil {

    private static final String DEF_FORMAT = "#.##";
    private static final String TWO_DECIMAL = "#.00";

    /**
     * 货币格式化以中国显示
     *
     * @param price
     * @return “￥ 0.00”
     */
    public static String priceFmt(double price) {

        return NumberFormat.getCurrencyInstance(Locale.CHINA).format(price);
    }

    /**
     * 货币格式化以美国显示
     *
     * @param price
     * @return “$0.00”
     */
    public static String priceUsFmt(double price) {

        return NumberFormat.getCurrencyInstance(Locale.US).format(price);
    }

    /**
     * 数字百分比显示
     *
     * @param number
     * @return “50%”
     */
    public static String percentFmt(double number) {

        return NumberFormat.getPercentInstance().format(number);
    }

    /**
     * 数字百分比显示
     *
     * @param number
     * @return "50.00%"
     */
    public static String percentPlace(double number) {

        return new DecimalFormat("##.00%").format(number);
    }

    /**
     * 数字格式化
     *
     * @param number
     * @return
     */
    public static String numberPlace(double number) {

        return new DecimalFormat(DEF_FORMAT).format(number);
    }

    /**
     * 数字格式化
     *
     * @param number 12.3566
     * @param format “#.##”
     * @return "12.36"
     */
    public static String numberFmt(double number, String format) {

        return new DecimalFormat(format).format(number);
    }

    /**
     * 数字格式化,保留两位小数
     *
     * @param number 12.3566  4
     * @return "12.36"  "4.00"
     */
    public static String keepTwoDecimal(double number) {
        return new DecimalFormat(TWO_DECIMAL).format(number);
    }

}
