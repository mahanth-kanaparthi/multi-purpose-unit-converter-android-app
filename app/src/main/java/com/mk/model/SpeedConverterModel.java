package com.mk.model;

import com.mk.convert.SpeedConverterActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SpeedConverterModel {
    private final SpeedConverterActivity view;
    private static final Map<String,Double> speedUnitsWithFactors = new HashMap<>();
    private static final Map<String,String> speedUnitsWithCodes = new HashMap<>();
    public SpeedConverterModel(SpeedConverterActivity view){
        this.view = view;
//        populateSpeedUnitsWithFactors();
//        populateSpeedUnitsWithCodes();
    }

    static {

        // Speed Units
        speedUnitsWithFactors.put("Lightspeed", 299_792_458.0); // 1 Lightspeed = 299,792,458 m/s
        speedUnitsWithFactors.put("Mach", 340.29); // 1 Mach = 340.29 m/s (at sea level, 20°C)
        speedUnitsWithFactors.put("Meter per second", 1.0); // Base unit
        speedUnitsWithFactors.put("Kilometer per hour", 0.2777777778); // 1 km/h = 0.2777777778 m/s
        speedUnitsWithFactors.put("Kilometer per second", 1_000.0); // 1 km/s = 1,000 m/s
        speedUnitsWithFactors.put("Knot", 0.5144444444); // 1 Knot = 0.5144444444 m/s
        speedUnitsWithFactors.put("Mile per hour", 0.44704); // 1 mph = 0.44704 m/s
        speedUnitsWithFactors.put("Foot per second", 0.3048); // 1 ft/s = 0.3048 m/s
        speedUnitsWithFactors.put("Inch per second", 0.0254); // 1 in/s = 0.0254 m/s

    }
    static {
        speedUnitsWithCodes.put("c","Lightspeed");
        speedUnitsWithCodes.put("Ma","Mach");
        speedUnitsWithCodes.put("m/s","Meter per second");
        speedUnitsWithCodes.put("km/h","Kilometer per hour");
        speedUnitsWithCodes.put("km/s","Kilometer per second");
        speedUnitsWithCodes.put("kn","Knot");
        speedUnitsWithCodes.put("mph","Mile per hour");
        speedUnitsWithCodes.put("ft/s","Foot per second");
        speedUnitsWithCodes.put("in/s","Inch per second");

    }
    public Map<String,Double> getSpeedUnitsWithFactors(){
        return speedUnitsWithFactors;
    }
    public Map<String,String> getSpeedUnitsWithCodes() {
        return new TreeMap<>(speedUnitsWithCodes);
    }
}
