package com.mk.controller;


import static com.mk.model.TemperatureConverterModel.temperatureUnitsWithFactors;

import android.util.Log;
import android.widget.TextView;

import com.mk.convert.TemperatureConverterActivity;
import com.mk.model.TemperatureConverterModel;
import com.mk.utils.ExpressionEvaluator;

public class TemperatureConverterController {
    private final TemperatureConverterModel model;
    private final TemperatureConverterActivity view;

    public TemperatureConverterController(TemperatureConverterModel model, TemperatureConverterActivity view) {
        this.model = model;
        this.view = view;
    }

    public double performConversion(TextView selectedValue){
        String value = selectedValue.getText().toString();
        if (value.isEmpty()) {
            return 0.0;
        }

        double resultValue = 0.0;

//        try {
//            if (value.contains("+") || value.contains("-") || value.contains("×") || value.contains("÷")) {
//                value = value.replace("×", "*").replace("÷", "/");
//                //Log.w("AreaApp", "Before Expression: " + value);
//                if(value.endsWith("+") || value.endsWith("-") || value.endsWith("*") || value.endsWith("/")){
//                    value = value.substring(0,value.length()-1);
//                }
//                //Log.w("AreaApp", "After Expression: " + value);
//                resultValue = ExpressionEvaluator.evaluateExpression(value);
//            } else {
//                resultValue = Double.parseDouble(value);
//            }
//        } catch (Exception e) {
//            Log.e("TemperatureApp", "Error parsing value: " + e.getMessage(), e);
//            return Double.NaN;
//        }
        try {
            if(value.startsWith("-")) {
                value = value.substring(1);
                resultValue = -1 * Double.parseDouble(value);
            } else if (value.contains("-") && value.length() == 1) {
                return Double.NaN;
            } else {
                resultValue = Double.parseDouble(value);
            }
        } catch (Exception e) {
            Log.e("TemperatureApp", "Error parsing value: " + e.getMessage(), e);
            return Double.NaN;
        }


        int idx = view.values.indexOf(selectedValue);
        TextView baseUnitTextView = (TextView) view.units.get(idx);
        String baseUnit = baseUnitTextView.getText().toString();
        int targetIdx = (idx + 1) % 2;
        TextView targetUnitTextView = (TextView) view.units.get(targetIdx);
        String targetUnit = targetUnitTextView.getText().toString();

        try {
            resultValue = convertTemperature(resultValue, baseUnit, targetUnit);
        }catch (IllegalArgumentException e){
            Log.e("TemperatureApp", "Error converting temperature: " + e.getMessage(), e);
            return Double.NaN;
        }

        return resultValue;
    }

    public static double convertTemperature(double value, String fromUnit, String toUnit) {
        if (fromUnit.equals(toUnit)) {
            return value; // No conversion needed
        }

        // Convert to Kelvin as the base unit
        double valueInKelvin = toKelvin(value, fromUnit);

        // Convert from Kelvin to the target unit
        return fromKelvin(valueInKelvin, toUnit);
    }

    private static double toKelvin(double value, String fromUnit) {
        switch (fromUnit) {
            case "Celsius":
                return value + 273.15;
            case "Fahrenheit":
                return (value + 459.67) * 5 / 9;
            case "Rankine":
                return value * 5 / 9;
            case "Réaumur":
                return value * 1.25 + 273.15;
            case "Kelvin":
                return value; // Already Kelvin
            default:
                throw new IllegalArgumentException("Invalid fromUnit: " + fromUnit);
        }
    }

    private static double fromKelvin(double value, String toUnit) {
        switch (toUnit) {
            case "Celsius":
                return value - 273.15;
            case "Fahrenheit":
                return value * 9 / 5 - 459.67;
            case "Rankine":
                return value * 9 / 5;
            case "Réaumur":
                return (value - 273.15) * 0.8;
            case "Kelvin":
                return value; // Already Kelvin
            default:
                throw new IllegalArgumentException("Invalid toUnit: " + toUnit);
        }
    }
}
