package com.mk.controller;

import android.util.Log;
import android.widget.TextView;

import com.mk.convert.NumeralsConverterActivity;
import com.mk.model.NumeralsConverterModel;
import com.mk.utils.ExpressionEvaluator;

public class NumeralsConverterController {

    private final NumeralsConverterModel model;
    private final NumeralsConverterActivity view;

    public NumeralsConverterController(NumeralsConverterModel model, NumeralsConverterActivity view) {
        this.model = model;
        this.view = view;
    }

    public String performConversion(TextView selectedValue){
        String value = selectedValue.getText().toString();
        if (value.isEmpty()) {
            return String.valueOf(0.0);
        }

        Double resultValue = 0.0;

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
            Log.e("NumeralsApp", "Error parsing value: " + e.getMessage(), e);
            return String.valueOf(Double.NaN);
        }

        int idx = view.values.indexOf(selectedValue);
        TextView baseUnitTextView = (TextView) view.units.get(idx);
        String baseUnit = baseUnitTextView.getText().toString();
        int targetIdx = (idx + 1) % 2;
        TextView targetUnitTextView = (TextView) view.units.get(targetIdx);
        String targetUnit = targetUnitTextView.getText().toString();


        return convertNumeral(String.valueOf(resultValue.intValue()),baseUnit,targetUnit);
    }
    public String convertNumeral(String value, String fromUnit, String toUnit) {
        // Get the bases for the source and target numeral systems
        int fromBase = NumeralsConverterModel.numeralUnitsWithBases.getOrDefault(fromUnit, -1);
        int toBase = NumeralsConverterModel.numeralUnitsWithBases.getOrDefault(toUnit, -1);

        if (fromBase == -1 || toBase == -1) {
            throw new IllegalArgumentException("Invalid numeral system: " + fromUnit + " or " + toUnit);
        }

        // Convert the value to an integer (base 10) from the source base
        int decimalValue = Integer.parseInt(value, fromBase);

        // Convert the decimal value to the target base
        return Integer.toString(decimalValue, toBase).toUpperCase(); // Uppercase for Hexadecimal representation
    }

}
