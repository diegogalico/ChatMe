package com.schibsted.chatme.presentation.ui.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by diego.galico
 */

public class TimeUtils {

    public static final String DATE_FORMAT = "HH:mm";

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(new Date());
    }
}
