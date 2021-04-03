package com.jgt.twitter.utils;

import java.text.SimpleDateFormat;

public class Util {
    public static String toDate(long timeStamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
        return dateFormat.format(timeStamp);
    }
}