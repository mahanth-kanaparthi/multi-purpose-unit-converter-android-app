package com.mk.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LoanCalculator {

    // Calculate Simple Interest with precision
    public static BigDecimal calculateSimpleInterest(BigDecimal principal, BigDecimal rate, int years, int months) {
        BigDecimal totalYears = BigDecimal.valueOf(years).add(BigDecimal.valueOf(months).divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP));
        BigDecimal interest = principal.multiply(rate).multiply(totalYears).divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
        return interest.setScale(2, RoundingMode.HALF_UP);
    }

    // Calculate Compound Interest with precision
    public static BigDecimal calculateCompoundInterest(BigDecimal principal, BigDecimal rate, int years, int months) {
        int totalMonths = years * 12 + months;
        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);
        BigDecimal compoundFactor = BigDecimal.ONE.add(monthlyRate).pow(totalMonths);
        BigDecimal interest = principal.multiply(compoundFactor.subtract(BigDecimal.ONE));
        return interest.setScale(2, RoundingMode.HALF_UP);
    }

    // Calculate EMI with precision
    public static BigDecimal calculateEMI(BigDecimal principal, BigDecimal rate, int years, int months) {
        int totalMonths = (years * 12) + months;
        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);

        if (monthlyRate.compareTo(BigDecimal.ZERO) == 0) { // Avoid division by zero
            return principal.divide(BigDecimal.valueOf(totalMonths), 2, RoundingMode.HALF_UP);
        }

        BigDecimal factor = BigDecimal.ONE.add(monthlyRate).pow(totalMonths);
        BigDecimal emi = principal.multiply(monthlyRate).multiply(factor).divide(factor.subtract(BigDecimal.ONE), 10, RoundingMode.HALF_UP);
        return emi.setScale(2, RoundingMode.HALF_UP);
    }

    // Calculate Total Repayment with EMI
    public static BigDecimal calculateTotalRepaymentWithEMI(BigDecimal principal, BigDecimal rate, int years, int months) {
        BigDecimal emi = calculateEMI(principal, rate, years, months);
        int totalMonths = (years * 12) + months;
        BigDecimal totalRepayment = emi.multiply(BigDecimal.valueOf(totalMonths));
        return totalRepayment.setScale(2, RoundingMode.HALF_UP);
    }

    // Calculate Total Amount Payable
    public static BigDecimal calculateTotalAmountPayable(BigDecimal principal, BigDecimal rate, int years, int months, boolean isCompoundInterest) {
        BigDecimal totalAmount;
        if (isCompoundInterest) {
            totalAmount = principal.add(calculateCompoundInterest(principal, rate, years, months));
        } else {
            totalAmount = principal.add(calculateSimpleInterest(principal, rate, years, months));
        }
        return totalAmount.setScale(2, RoundingMode.HALF_UP);
    }
}
