package com.gxwz.wzxy.bookstoreapp.utils;

/**
 * Created by crucy on 2017/12/3.
 */

public class StringUtils {
    /**
     * 获取操作状态
     * @param type
     * @return
     */
    public static String getContrlType(int type) {
        switch (type) {
            case 0:
                return "未付款";
            case 1:
                return "待收货";
            case 2:
                return "待评论";
            case 3:
                return "退款中";
            case 4:
                return "完成";
        }
        return "处理中";
    }
}
