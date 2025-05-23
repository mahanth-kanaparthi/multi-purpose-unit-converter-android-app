package com.mk.controller;

import android.util.Log;
import android.widget.TextView;

import com.mk.convert.AreaConverterActivity;
import com.mk.model.AreaConverterModel;
import com.mk.utils.ExpressionEvaluator;

import java.util.Objects;

public class AreaConverterController {
    private final AreaConverterModel model;
    private final AreaConverterActivity view;

    public AreaConverterController(AreaConverterModel model, AreaConverterActivity view) {
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
            Log.e("AreaApp", "Error parsing value: " + e.getMessage(), e);
            return Double.NaN;
        }

        int idx = view.values.indexOf(selectedValue);
        TextView baseUnitTextView = (TextView) view.units.get(idx);
        String baseUnit = baseUnitTextView.getText().toString();
        int targetIdx = (idx + 1) % 2;
        TextView targetUnitTextView = (TextView) view.units.get(targetIdx);
        String targetUnit = targetUnitTextView.getText().toString();

        double conversionFactor = Objects.requireNonNull(model.getAreaUnitsWithFactors().get(baseUnit)) /
                Objects.requireNonNull(model.getAreaUnitsWithFactors().get(targetUnit));
        resultValue *= conversionFactor;

        return resultValue;
    }
}
