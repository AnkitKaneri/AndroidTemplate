package com.github.ankitkaneri.androidtemplate.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {


    private Utils() {
        //To hide public constructor
    }

    public static String getDateStringFromLong(long millis, String dateFormat) {
        Date date = new Date(millis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(date);
    }
}
