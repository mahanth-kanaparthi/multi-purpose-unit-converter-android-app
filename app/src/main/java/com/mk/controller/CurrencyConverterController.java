package com.mk.controller;


import com.mk.model.CurrencyConverterModel;
import com.mk.convert.CurrencyConverterActivity;

import java.util.List;

public class CurrencyConverterController {
    private CurrencyConverterModel model;
    private CurrencyConverterActivity view;

    public CurrencyConverterController(CurrencyConverterActivity view) {
        this.view = view;
        model = new CurrencyConverterModel(view);

    }
    public void handleButtonClick(double amount,String fromCurrency, String toCurrency1, String toCurrency2){
        List<Double> convertedAmounts = model.convertCurrency(amount, fromCurrency, toCurrency1, toCurrency2);
        view.updateUI(convertedAmounts.get(0),convertedAmounts.get(1));
    }
}

