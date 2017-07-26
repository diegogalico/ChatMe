package com.schibsted.chatme.presentation.ui.utils;

/**
 * Created by diego.galico
 */

public class StringUtils {

    public static boolean equalsWithNulls(Object a, Object b) {
        if (a == b) return true;
        if ((a == null) || (b == null)) return false;
        return a.equals(b);
    }
}
