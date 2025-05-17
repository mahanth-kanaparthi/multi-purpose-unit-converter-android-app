package com.mk.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberResultFormatter {

    public static BigDecimal format(BigDecimal value) {
        String plain = value.stripTrailingZeros().toPlainString();

        // No decimal point? Just return original
        if (!plain.contains(".")) {
            return value.setScale(0, RoundingMode.HALF_UP);
        }

        String[] parts = plain.split("\\.");
        String decimalPart = parts[1];

        // If decimal length is 4 or less, preserve
        if (decimalPart.length() <= 4) {
            return value.setScale(decimalPart.length(), RoundingMode.HALF_UP);
        }

        // Now, check digits after the 4th
        String afterFourth = decimalPart.substring(4);

        int totalDigits = afterFourth.length();
        int zeroCount = afterFourth.replaceAll("[^0]", "").length();
        int nonZeroCount = totalDigits - zeroCount;

        // If most are zeros (e.g., zeroCount >= nonZeroCount + 1), round to 4
        if (zeroCount >= nonZeroCount + 1) {
            return value.setScale(4, RoundingMode.HALF_UP);
        }

        // Otherwise, keep full decimal precision
        return value;
    }
}
