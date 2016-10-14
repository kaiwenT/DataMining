package com.hust.util;

import java.util.List;

public class CommonUtils {

    public static boolean hasEmpty(String...strs) {
        if (null == strs || strs.length == 0) {
            return true;
        }
        for (String str : strs) {
            if (str == null || str.length() == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasEmpty(List...list) {
        if (null == list || list.length == 0) {
            return true;
        }
        for (List ele : list) {
            if (ele == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean lowerThan0(int...is) {
        if (is == null || is.length == 0) {
            throw new IllegalArgumentException();
        }
        for (int i : is) {
            if (i < 0) {
                return true;
            }
        }
        return false;
    }
}
