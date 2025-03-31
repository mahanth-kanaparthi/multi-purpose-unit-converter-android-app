package com.mk.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class InvestmentCalculator {

    // Calculate the value of a one-time investment with precision
    public static BigDecimal calculateOneTimeInvestmentValue(BigDecimal principal, BigDecimal rate, int years, int months) {
        BigDecimal totalYears = BigDecimal.valueOf(years).add(BigDecimal.valueOf(months).divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP));
        BigDecimal annualRate = rate.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
        BigDecimal compoundFactor = BigDecimal.ONE.add(annualRate).pow(totalYears.intValue());
        return principal.multiply(compoundFactor).setScale(2, RoundingMode.HALF_UP);
    }

    // Calculate the value of a recurring investment with precision
    public static BigDecimal calculateRecurringInvestmentValue(BigDecimal deposit, BigDecimal rate, int years, int months) {
        int totalMonths = (years * 12) + months;
        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);

        if (monthlyRate.compareTo(BigDecimal.ZERO) == 0) {
            return deposit.multiply(BigDecimal.valueOf(totalMonths)).setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal futureValue = BigDecimal.ZERO;
        for (int i = 1; i <= totalMonths; i++) {
            BigDecimal growthFactor = BigDecimal.ONE.add(monthlyRate).pow(totalMonths - i + 1);
            futureValue = futureValue.add(deposit.multiply(growthFactor));
        }

        return futureValue.setScale(2, RoundingMode.HALF_UP);
    }
}
