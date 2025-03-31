package com.mk.utils;

import java.text.DecimalFormat;

public class StringFormatterWithPattern {
    public static String formatString(double number,String pattern){

        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(number);
    }
}
