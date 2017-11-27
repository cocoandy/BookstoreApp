package com.gxwz.wzxy.bookstoreapp.utils;

import java.util.List;

/**
 * Created by crucy on 2017/10/31.
 */

public class Utils {

    public static boolean isEmpty(String str) {
        if (str != null && !"".endsWith(str))
            return false;
        return true;
    }

    public static boolean isEmpty(List list) {
        if (list != null && !list.isEmpty())
            return false;
        return true;
    }
}
