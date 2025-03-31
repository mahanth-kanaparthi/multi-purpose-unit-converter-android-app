package com.mk.model;

import com.mk.convert.TemperatureConverterActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TemperatureConverterModel {
    private TemperatureConverterActivity view;

    public TemperatureConverterModel(TemperatureConverterActivity view) {
        this.view = view;
    }

    // Map of temperature units with scaling factor and offset relative to Kelvin
    public static final Map<String, double[]> temperatureUnitsWithFactors = new HashMap<>();
    public static final Map<String, String> temperatureUnitsWithCodes = new HashMap<>();


    static {
        // Format: {scaleFactor, offsetToKelvin}

        // Kelvin (Base Unit)
        temperatureUnitsWithFactors.put("Kelvin", new double[]{1.0, 0.0}); // No offset, directly in Kelvin

        // Celsius
        temperatureUnitsWithFactors.put("Celsius", new double[]{1.0, 273.15}); // K = °C + 273.15

        // Fahrenheit
        temperatureUnitsWithFactors.put("Fahrenheit", new double[]{5.0 / 9.0, 255.372222}); // K = (°F + 459.67) * 5/9

        // Rankine
        temperatureUnitsWithFactors.put("Rankine", new double[]{5.0 / 9.0, 0.0}); // K = °R * 5/9

        // Réaumur
        temperatureUnitsWithFactors.put("Réaumur", new double[]{1.25, 273.15}); // K = (°Re * 5/4) + 273.15
    }

    static {
        temperatureUnitsWithCodes.put("K","Kelvin");
        temperatureUnitsWithCodes.put("°C","Celsius");
        temperatureUnitsWithCodes.put("°F","Fahrenheit");
        temperatureUnitsWithCodes.put("°R","Rankine");
        temperatureUnitsWithCodes.put("°Re","Réaumur");
    }

    public Map<String, double[]> getTemperatureUnitsWithFactors(){
        return temperatureUnitsWithFactors;
    }
    public Map<String, String> getTemperatureUnitsWithCodes(){
        return new TreeMap<>(temperatureUnitsWithCodes); // for sorting
    }
}
