package com.mk.controller;

import android.util.Log;
import android.widget.TextView;

import com.mk.convert.LengthConverterActivity;
import com.mk.model.LengthConverterModel;
import com.mk.utils.ExpressionEvaluator;

import java.util.Objects;

public class LengthConverterController {
    private final LengthConverterModel model;
    private final LengthConverterActivity view;

    public LengthConverterController(LengthConverterModel model, LengthConverterActivity view) {
        this.model = model;
        this.view = view;
    }

    public double performConversion(TextView selectedValue){
        String value = selectedValue.getText().toString();
        if (value.isEmpty()) {
            return 0.0;
        }

        double resultValue = 0.0;

        try {
            if (value.contains("+") || value.contains("-") || value.contains("×") || value.contains("÷")) {
                value = value.replace("×", "*").replace("÷", "/");
                //Log.w("AreaApp", "Before Expression: " + value);
                if(value.endsWith("+") || value.endsWith("-") || value.endsWith("*") || value.endsWith("/")){
                    value = value.substring(0,value.length()-1);
                }
                //Log.w("AreaApp", "After Expression: " + value);
                resultValue = ExpressionEvaluator.evaluateExpression(value);
            } else {
                resultValue = Double.parseDouble(value);
            }
        } catch (Exception e) {
            Log.e("LengthApp", "Error parsing value: " + e.getMessage(), e);
            return Double.NaN;
        }

        int idx = view.values.indexOf(selectedValue);
        TextView baseUnitTextView = (TextView) view.units.get(idx);
        String baseUnit = baseUnitTextView.getText().toString();
        int targetIdx = (idx + 1) % 2;
        TextView targetUnitTextView = (TextView) view.units.get(targetIdx);
        String targetUnit = targetUnitTextView.getText().toString();

        double conversionFactor = Objects.requireNonNull(model.getLengthUnitsWithFactors().get(baseUnit)) /
                Objects.requireNonNull(model.getLengthUnitsWithFactors().get(targetUnit));
        resultValue *= conversionFactor;

        return resultValue;
    }
}

