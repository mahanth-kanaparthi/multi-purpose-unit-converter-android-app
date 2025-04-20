package com.mk.controller;


import com.mk.model.CurrencyConverterModel;
import com.mk.convert.CurrencyConverterActivity;

import java.math.BigDecimal;
import java.util.List;

public class CurrencyConverterController {
    private final CurrencyConverterModel model;
    private final CurrencyConverterActivity view;

    public CurrencyConverterController(CurrencyConverterActivity view, CurrencyConverterModel model) {
        this.view = view;
        this.model = model;

    }
    public void handleButtonClick(double amount,String fromCurrency, String toCurrency1, String toCurrency2){
        List<BigDecimal> convertedAmounts = model.convertCurrency(BigDecimal.valueOf(amount), fromCurrency, toCurrency1, toCurrency2);
        view.updateUI(convertedAmounts.get(0).doubleValue(),convertedAmounts.get(1).doubleValue());
    }
}

