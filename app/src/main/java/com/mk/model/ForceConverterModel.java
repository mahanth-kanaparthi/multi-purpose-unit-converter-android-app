package com.mk.model;

import com.mk.convert.ForceConverterActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ForceConverterModel {

    private final ForceConverterActivity view;

    private static final Map<String,Double> forceUnitsWithFactors = new HashMap<>();
    private static final Map<String,String> forceUnitsWithCodes = new HashMap<>();
    public ForceConverterModel(ForceConverterActivity view){
        this.view = view;
        //populateAreaUnitsWithFactors();
        //populateAreaUnitsWithCodes();
    }
    static {

        // Base unit: Newtons (N)
        forceUnitsWithFactors.put("Dyne", 1e5);                       // 1 Newton = 10⁵ Dyne
        forceUnitsWithFactors.put("Kilogram force", 9.80665);         // 1 Newton ≈ 9.80665 Kilogram force
        forceUnitsWithFactors.put("Kilonewtons", 0.001);              // 1 Newton = 0.001 Kilonewtons
        forceUnitsWithFactors.put("Kips", 0.0002248089431);           // 1 Newton ≈ 0.0002248089431 Kips
        forceUnitsWithFactors.put("Meganewtons", 1e-6);               // 1 Newton = 10⁻⁶ Meganewtons
        forceUnitsWithFactors.put("Newtons", 1.0);                    // 1 Newton = 1 Newton (base)
        forceUnitsWithFactors.put("Pound force", 0.2248089431);       // 1 Newton ≈ 0.2248089431 Pound force
        forceUnitsWithFactors.put("Poundals", 7.23301385);            // 1 Newton ≈ 7.23301385 Poundals
        forceUnitsWithFactors.put("sthène", 1e-3);                    // 1 Newton = 10⁻³ sthène
        forceUnitsWithFactors.put("Tonnes force", 0.0001019716213);   // 1 Newton ≈ 0.0001019716213 Tonnes force
        forceUnitsWithFactors.put("Tons force (UK)", 0.0001003611356);// 1 Newton ≈ 0.0001003611356 Tons force (UK)
        forceUnitsWithFactors.put("Tons force (US)", 0.0001124044719);// 1 Newton ≈ 0.0001124044719 Tons force (US)
    }
    static {

        forceUnitsWithCodes.put("dyn", "Dyne");
        forceUnitsWithCodes.put("kgf", "Kilogram force");
        forceUnitsWithCodes.put("kN", "Kilogram newtons");
        forceUnitsWithCodes.put("kips", "Kips");
        forceUnitsWithCodes.put("MN", "Meganewtons");
        forceUnitsWithCodes.put("N", "Newtons");
        forceUnitsWithCodes.put("lbf", "Pound force");
        forceUnitsWithCodes.put("pdl", "Poundals");
        forceUnitsWithCodes.put("sn", "sthène");
        forceUnitsWithCodes.put("F", "Tonnes force");
        forceUnitsWithCodes.put("F-uk", "Tons force (UK)");
        forceUnitsWithCodes.put("F-us", "Tons force (US)");

    }
    public Map<String,Double> getForceUnitsWithFactors(){
        return forceUnitsWithFactors;
    }
    public Map<String,String> getForceUnitsWithCodes(){
        return new TreeMap<>(forceUnitsWithCodes); // for sorting
    }
}
