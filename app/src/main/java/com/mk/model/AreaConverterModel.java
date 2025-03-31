package com.mk.model;

import com.mk.convert.AreaConverterActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AreaConverterModel {
    private final AreaConverterActivity view;

    private static final Map<String,Double> areaUnitsWithFactors = new HashMap<>();
    private static final Map<String,String> areaUnitsWithCodes = new HashMap<>();
    public AreaConverterModel(AreaConverterActivity view){
        this.view = view;
        //populateAreaUnitsWithFactors();
        //populateAreaUnitsWithCodes();
    }
    static {

        // Metric Units
        areaUnitsWithFactors.put("Square kilometer", 1_000_000.0); // 1 km² = 1,000,000 m²
        areaUnitsWithFactors.put("Hectare", 10_000.0); // 1 ha = 10,000 m²
        areaUnitsWithFactors.put("Are", 100.0); // 1 are = 100 m²
        areaUnitsWithFactors.put("Square meter", 1.0); // Base unit
        areaUnitsWithFactors.put("Square decimeter", 0.01); // 1 dm² = 0.01 m²
        areaUnitsWithFactors.put("Square centimeter", 0.0001); // 1 cm² = 0.0001 m²
        areaUnitsWithFactors.put("Square millimeter", 1e-6); // 1 mm² = 0.000001 m²
        areaUnitsWithFactors.put("Square micron", 1e-12); // 1 µm² = 0.000000000001 m²

        // Imperial Units
        areaUnitsWithFactors.put("Acre", 4046.8564224); // 1 acre = 4046.8564224 m²
        areaUnitsWithFactors.put("Square mile", 2_589_988.110336); // 1 mi² = 2,589,988.110336 m²
        areaUnitsWithFactors.put("Square yard", 0.83612736); // 1 yd² = 0.83612736 m²
        areaUnitsWithFactors.put("Square foot", 0.09290304); // 1 ft² = 0.09290304 m²
        areaUnitsWithFactors.put("Square inch", 0.00064516); // 1 in² = 0.00064516 m²
        areaUnitsWithFactors.put("Square rod", 25.29285264); // 1 rod² = 25.29285264 m²

        // Chinese Units
        areaUnitsWithFactors.put("Qing", 66_666.6667); // 1 Qing = 66,666.67 m² (approximate)
        areaUnitsWithFactors.put("Mu", 666.66667); // 1 Mu = 666.67 m² (approximate)
        areaUnitsWithFactors.put("Square chi", 1.0 / 9.0); // 1 Square Chi = 1/9 m²
        areaUnitsWithFactors.put("Square cun", 1.0 / 900.0); // 1 Square Cun = 1/900 m²


    }
    static {

        areaUnitsWithCodes.put("km²","Square kilometer");
        areaUnitsWithCodes.put("ha","Hectare");
        areaUnitsWithCodes.put("a","Are");
        areaUnitsWithCodes.put("m²","Square meter");
        areaUnitsWithCodes.put("dm²","Square decimeter");
        areaUnitsWithCodes.put("cm²","Square centimeter");
        areaUnitsWithCodes.put("mm²","Square millimeter");
        areaUnitsWithCodes.put("µm²","Square micron");
        areaUnitsWithCodes.put("ac","Acre");
        areaUnitsWithCodes.put("mile²","Square mile");
        areaUnitsWithCodes.put("yd²","Square yard");
        areaUnitsWithCodes.put("ft²","Square foot");
        areaUnitsWithCodes.put("in²","Square inch");
        areaUnitsWithCodes.put("rd²","Square rod");
        areaUnitsWithCodes.put("qing","Qing");
        areaUnitsWithCodes.put("mu","Mu");
        areaUnitsWithCodes.put("chi²","Square chi");
        areaUnitsWithCodes.put("cun²","Square cun");

    }
    public Map<String,Double> getAreaUnitsWithFactors(){
        return areaUnitsWithFactors;
    }
    public Map<String,String> getAreaUnitsWithCodes(){
        return new TreeMap<>(areaUnitsWithCodes); // for sorting
    }

}
