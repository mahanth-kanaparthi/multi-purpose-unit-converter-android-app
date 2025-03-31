package com.mk.model;

import com.mk.convert.PressureConverterActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PressureConverterModel {

    private final PressureConverterActivity view;

    private static final Map<String,Double> pressureUnitsWithFactors = new HashMap<>();
    private static final Map<String,String> pressureUnitsWithCodes = new HashMap<>();
    public PressureConverterModel(PressureConverterActivity view){
        this.view = view;
        //populateAreaUnitsWithFactors();
        //populateAreaUnitsWithCodes();
    }
    static {

        // Base unit: Pascals (Pa)
        pressureUnitsWithFactors.put("atmospheres", 101325.0);                    // 1 atmosphere = 101325 Pa
        pressureUnitsWithFactors.put("bars", 100000.0);                           // 1 bar = 100000 Pa
        pressureUnitsWithFactors.put("centimeters mercury", 1333.22);             // 1 cmHg ≈ 1333.22 Pa
        pressureUnitsWithFactors.put("centimeters water", 98.0665);               // 1 cmH2O ≈ 98.0665 Pa
        pressureUnitsWithFactors.put("feet of water", 2989.06692);                // 1 ftH2O ≈ 2989.067 Pa
        pressureUnitsWithFactors.put("hectopascals", 100.0);                      // 1 hPa = 100 Pa
        pressureUnitsWithFactors.put("inches of water", 249.08891);               // 1 inH2O ≈ 249.089 Pa
        pressureUnitsWithFactors.put("inches of mercury", 3386.389);              // 1 inHg ≈ 3386.389 Pa
        pressureUnitsWithFactors.put("kilogram-forces/sq.centimeter", 98066.5);   // 1 kgf/cm² ≈ 98066.5 Pa
        pressureUnitsWithFactors.put("kilogram-forces/sq.meter", 9.80665);        // 1 kgf/m² ≈ 9.80665 Pa
        pressureUnitsWithFactors.put("kilonewtons/sq.meter", 1000.0);             // 1 kN/m² = 1000 Pa
        pressureUnitsWithFactors.put("kilonewtons/sq.millimeter", 1e9);           // 1 kN/mm² = 10⁹ Pa
        pressureUnitsWithFactors.put("kilopascals", 1000.0);                      // 1 kPa = 1000 Pa
        pressureUnitsWithFactors.put("kips/sq.inch", 6894757.29);                 // 1 kip/in² ≈ 6894757.29 Pa
        pressureUnitsWithFactors.put("meganewtons/sq.meter", 1e6);                // 1 MN/m² = 10⁶ Pa
        pressureUnitsWithFactors.put("meganewtons/sq.millimeter", 1e12);          // 1 MN/mm² = 10¹² Pa
        pressureUnitsWithFactors.put("megapascals", 1e6);                         // 1 MPa = 10⁶ Pa
        pressureUnitsWithFactors.put("meters of water", 9806.65);                 // 1 mH2O ≈ 9806.65 Pa
        pressureUnitsWithFactors.put("millibars", 100.0);                         // 1 mbar = 100 Pa
        pressureUnitsWithFactors.put("millimeters of mercury", 133.322);          // 1 mmHg ≈ 133.322 Pa
        pressureUnitsWithFactors.put("millimeters of water", 9.80665);            // 1 mmH2O ≈ 9.80665 Pa
        pressureUnitsWithFactors.put("newtons/sq.centimeter", 10000.0);           // 1 N/cm² = 10000 Pa
        pressureUnitsWithFactors.put("newtons/sq.meter", 1.0);                    // 1 N/m² = 1 Pa
        pressureUnitsWithFactors.put("newtons/sq.millimeter", 1e6);               // 1 N/mm² = 10⁶ Pa
        pressureUnitsWithFactors.put("pascals", 1.0);                             // 1 Pascal = 1 Pa (base unit)
        pressureUnitsWithFactors.put("pounds-force/sq.foot", 47.88026);           // 1 lbf/ft² ≈ 47.88026 Pa
        pressureUnitsWithFactors.put("pounds-force/sq.inch", 6894.75729);         // 1 lbf/in² ≈ 6894.757 Pa
        pressureUnitsWithFactors.put("poundals/sq.foot", 1.488164);               // 1 pdl/ft² ≈ 1.488164 Pa
        pressureUnitsWithFactors.put("tons (UK)-force/sq.foot", 107251.779);      // 1 ton (UK)-force/ft² ≈ 107251.779 Pa
        pressureUnitsWithFactors.put("tons (UK)-force/sq.inch", 15444256.3);      // 1 ton (UK)-force/in² ≈ 15444256.3 Pa
        pressureUnitsWithFactors.put("tons (US)-force/sq.foot", 95760.51796);     // 1 ton (US)-force/ft² ≈ 95760.518 Pa
        pressureUnitsWithFactors.put("tons (US)-force/sq.inch", 13789514.6);      // 1 ton (US)-force/in² ≈ 13789514.6 Pa
        pressureUnitsWithFactors.put("tonnes-force/sq.cm", 9806650.0);            // 1 tf/cm² ≈ 9806650 Pa
        pressureUnitsWithFactors.put("tonnes-force/sq.meter", 9806.65);           // 1 tf/m² ≈ 9806.65 Pa
        pressureUnitsWithFactors.put("torr", 133.322);                            // 1 torr ≈ 133.322 Pa
    }
    static {

        pressureUnitsWithCodes.put("atm", "atmospheres");
        pressureUnitsWithCodes.put("bar", "bars");
        pressureUnitsWithCodes.put("cmHg", "centimeters mercury");
        pressureUnitsWithCodes.put("cmH2O", "centimeters water");
        pressureUnitsWithCodes.put("ftH2O", "feet of water");
        pressureUnitsWithCodes.put("hPa", "hectopascals");
        pressureUnitsWithCodes.put("inH2O", "inches of water");
        pressureUnitsWithCodes.put("inHg", "inches of mercury");
        pressureUnitsWithCodes.put("kgf/cm²", "kilogram-forces/sq.centimeter");
        pressureUnitsWithCodes.put("kgf/m²", "kilogram-forces/sq.meter");
        pressureUnitsWithCodes.put("kN/m²", "kilonewtons/sq.meter");
        pressureUnitsWithCodes.put("kN/mm²", "kilonewtons/sq.millimeter");
        pressureUnitsWithCodes.put("kPa", "kilopascals");
        pressureUnitsWithCodes.put("kip/in²", "kips/sq.inch");
        pressureUnitsWithCodes.put("MN/m²", "meganewtons/sq.meter");
        pressureUnitsWithCodes.put("MN/mm²", "meganewtons/sq.millimeter");
        pressureUnitsWithCodes.put("MPa", "megapascals");
        pressureUnitsWithCodes.put("mH2O", "meters of water");
        pressureUnitsWithCodes.put("mbar", "millibars");
        pressureUnitsWithCodes.put("mmHg", "millimeters of mercury");
        pressureUnitsWithCodes.put("mmH2O", "millimeters of water");
        pressureUnitsWithCodes.put("N/cm²", "newtons/sq.centimeter");
        pressureUnitsWithCodes.put("N/m²", "newtons/sq.meter");
        pressureUnitsWithCodes.put("N/mm²", "newtons/sq.millimeter");
        pressureUnitsWithCodes.put("Pa", "pascals");
        pressureUnitsWithCodes.put("lbf/ft²", "pounds-force/sq.foot");
        pressureUnitsWithCodes.put("lbf/in²", "pounds-force/sq.inch");
        pressureUnitsWithCodes.put("pdl/ft²", "poundals/sq.foot");
        pressureUnitsWithCodes.put("ton (UK)-force/ft²", "tons (UK)-force/sq.foot");
        pressureUnitsWithCodes.put("ton (UK)-force/in²", "tons (UK)-force/sq.inch");
        pressureUnitsWithCodes.put("ton (US)-force/ft²", "tons (US)-force/sq.foot");
        pressureUnitsWithCodes.put("ton (US)-force/in²", "tons (US)-force/sq.inch");
        pressureUnitsWithCodes.put("tf/cm²", "tonnes-force/sq.cm");
        pressureUnitsWithCodes.put("tf/m²", "tonnes-force/sq.meter");
        pressureUnitsWithCodes.put("torr (mm Hg 0℃)", "torr");

    }
    public Map<String,Double> getPressureUnitsWithFactors(){
        return pressureUnitsWithFactors;
    }
    public Map<String,String> getPressureUnitsWithCodes(){
        return new TreeMap<>(pressureUnitsWithCodes); // for sorting
    }
}
