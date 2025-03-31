package com.mk.model;

import com.mk.convert.MassConverterActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MassConverterModel {
    private MassConverterActivity view;

    private static final Map<String,Double> massUnitsWithFactors = new HashMap<>();
    private static final Map<String,String> massUnitsWithCodes = new HashMap<>();

    public MassConverterModel(MassConverterActivity view) {
        this.view = view;

//        populateMassUnitsWithFactors();
//        populateMassUnitsWithCodes();
    }

    static {

// Metric Units
        massUnitsWithFactors.put("Tonne", 1000.0); // 1 Tonne = 1000 Kilograms
        massUnitsWithFactors.put("Kilogram", 1.0); // Base unit
        massUnitsWithFactors.put("Gram", 0.001); // 1 Gram = 0.001 Kilograms
        massUnitsWithFactors.put("Milligram", 1e-6); // 1 Milligram = 0.000001 Kilograms
        massUnitsWithFactors.put("Microgram", 1e-9); // 1 Microgram = 0.000000001 Kilograms

// Other common units
        massUnitsWithFactors.put("Quintal", 100.0); // 1 Quintal = 100 Kilograms
        massUnitsWithFactors.put("Pound", 0.45359237); // 1 Pound = 0.45359237 Kilograms
        massUnitsWithFactors.put("Ounce", 0.0283495231); // 1 Ounce = 0.0283495231 Kilograms
        massUnitsWithFactors.put("Carat", 0.0002); // 1 Carat = 0.0002 Kilograms
        massUnitsWithFactors.put("Grain", 0.00006479891); // 1 Grain = 0.00006479891 Kilograms

// Tons
        massUnitsWithFactors.put("Long ton", 1016.0469088); // 1 Long Ton = 1016.0469088 Kilograms
        massUnitsWithFactors.put("Short ton", 907.18474); // 1 Short Ton = 907.18474 Kilograms

// Hundredweights
        massUnitsWithFactors.put("UK hundredweight", 50.80234544); // 1 UK Hundredweight = 50.80234544 Kilograms
        massUnitsWithFactors.put("US hundredweight", 45.359237); // 1 US Hundredweight = 45.359237 Kilograms

// Stones and smaller imperial units
        massUnitsWithFactors.put("Stone", 6.35029318); // 1 Stone = 6.35029318 Kilograms
        massUnitsWithFactors.put("Dram", 0.0017718451953125); // 1 Dram = 0.0017718451953125 Kilograms

// Chinese Units
        massUnitsWithFactors.put("Dan", 50.0); // 1 Dan = 50 Kilograms
        massUnitsWithFactors.put("Jin", 0.5); // 1 Jin = 0.5 Kilograms
        massUnitsWithFactors.put("Qian", 0.05); // 1 Qian = 0.05 Kilograms
        massUnitsWithFactors.put("Liang", 0.05); // 1 Liang (synonym of Qian) = 0.05 Kilograms
        massUnitsWithFactors.put("Jin (Taiwan)", 0.6); // 1 Jin (Taiwan) = 0.6 Kilograms

    }
    static {
        massUnitsWithCodes.put("t", "Tonne");
        massUnitsWithCodes.put("kg", "Kilogram");
        massUnitsWithCodes.put("g", "Gram");
        massUnitsWithCodes.put("mg", "Milligram");
        massUnitsWithCodes.put("mcg", "Microgram");
        massUnitsWithCodes.put("q", "Quintal");
        massUnitsWithCodes.put("lb", "Pound");
        massUnitsWithCodes.put("oz", "Ounce");
        massUnitsWithCodes.put("ct", "Carat");
        massUnitsWithCodes.put("gr", "Grain");
        massUnitsWithCodes.put("l.t", "Long ton");
        massUnitsWithCodes.put("sh.t", "Short ton");
        massUnitsWithCodes.put("ukcwt", "UK hundredweight");
        massUnitsWithCodes.put("uscwt", "US hundredweight");
        massUnitsWithCodes.put("st", "Stone");
        massUnitsWithCodes.put("dr", "Dram");
        massUnitsWithCodes.put("dan", "Dan");
        massUnitsWithCodes.put("jin", "Jin");
        massUnitsWithCodes.put("qian", "Qian");
        massUnitsWithCodes.put("liang", "Liang");
        massUnitsWithCodes.put("jin (Taiwan)", "Jin (Taiwan)");


    }

    public Map<String, Double> getMassUnitsWithFactors() {
        return massUnitsWithFactors;
    }

    public Map<String, String> getMassUnitsWithCodes() {
        return new TreeMap<>(massUnitsWithCodes);

    }

}