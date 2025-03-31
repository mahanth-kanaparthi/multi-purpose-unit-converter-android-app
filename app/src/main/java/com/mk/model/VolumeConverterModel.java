package com.mk.model;


import com.mk.convert.VolumeConverterActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class VolumeConverterModel {
    private final VolumeConverterActivity view;
    private static final Map<String, Double> volumeUnitsWithFactors = new HashMap<>();
    private static final Map<String, String> volumeUnitsWithCodes = new HashMap<>();
    public VolumeConverterModel(VolumeConverterActivity view){
        this.view = view;
//        populateVolumeUnitsWithFactors();
//        populateVolumeUnitsWithCodes();
    }
    static {

        // Metric Units
        volumeUnitsWithFactors.put("Cubic meter", 1.0); // Base unit
        volumeUnitsWithFactors.put("Cubic decimeter", 0.001); // 1 dm³ = 0.001 m³
        volumeUnitsWithFactors.put("Cubic centimeter", 1e-6); // 1 cm³ = 0.000001 m³
        volumeUnitsWithFactors.put("Cubic millimeter", 1e-9); // 1 mm³ = 0.000000001 m³
        volumeUnitsWithFactors.put("Hectoliter", 0.1); // 1 hl = 0.1 m³
        volumeUnitsWithFactors.put("Liter", 0.001); // 1 l = 0.001 m³
        volumeUnitsWithFactors.put("Deciliter", 0.0001); // 1 dl = 0.0001 m³
        volumeUnitsWithFactors.put("Centiliter", 0.00001); // 1 cl = 0.00001 m³
        volumeUnitsWithFactors.put("Milliliter", 1e-6); // 1 ml = 0.000001 m³

        // Imperial Units
        volumeUnitsWithFactors.put("Cubic foot", 0.028316846592); // 1 ft³ = 0.028316846592 m³
        volumeUnitsWithFactors.put("Cubic inch", 0.000016387064); // 1 in³ = 0.000016387064 m³
        volumeUnitsWithFactors.put("Cubic yard", 0.764554857984); // 1 yd³ = 0.764554857984 m³
        volumeUnitsWithFactors.put("Acre-foot", 1_233.48183754752); // 1 acre-foot = 1233.48183754752 m³

    }
    static {

        volumeUnitsWithCodes.put("m³","Cubic meter");
        volumeUnitsWithCodes.put("dm³","Cubic decimeter");
        volumeUnitsWithCodes.put("cm³","Cubic centimeter");
        volumeUnitsWithCodes.put("mm³","Cubic millimeter");
        volumeUnitsWithCodes.put("hl","Hectoliter");
        volumeUnitsWithCodes.put("l","Liter");
        volumeUnitsWithCodes.put("dl","Deciliter");
        volumeUnitsWithCodes.put("cl","Centiliter");
        volumeUnitsWithCodes.put("ml","Milliliter");
        volumeUnitsWithCodes.put("ft³","Cubic foot");
        volumeUnitsWithCodes.put("in³","Cubic inch");
        volumeUnitsWithCodes.put("yd³","Cubic yard");
        volumeUnitsWithCodes.put("af³","Acre-foot");
    }
    public Map<String, Double> getVolumeUnitsWithFactors(){
        return volumeUnitsWithFactors;
    }
    public Map<String, String> getVolumeUnitsWithCodes() {
        return new TreeMap<>(volumeUnitsWithCodes);
    }
}
