package com.gxwz.wzxy.bookstoreapp.utils;

import java.text.DecimalFormat;

/**
 * Created by crucy on 2017/12/4.
 */

public class NumUtil {

    public static double moneyFormat(double money){
        DecimalFormat format = new DecimalFormat("#.##");
        return Double.parseDouble(format.format(money));
    }
    public static String moneyFormatStr(double money){
        DecimalFormat format = new DecimalFormat("#.##");
        return format.format(money);
    }
}
