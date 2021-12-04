package com.ittext.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    private static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

    public static Date convertStringToDate(String date) throws ParseException {
        return formatter.parse(date);
    }

    public static String convertDateToString(Date date){
        return formatter.format(date);
    }
}
