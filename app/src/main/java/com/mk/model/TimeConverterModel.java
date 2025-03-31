package com.mk.model;

import com.mk.convert.TimeConverterActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TimeConverterModel {
    private final TimeConverterActivity view;
    private static final Map<String,Double> timeUnitsWithFactors = new HashMap<>();;
    private static final Map<String,String> timeUnitsWithCodes = new HashMap<>();
    public TimeConverterModel(TimeConverterActivity view){
        this.view = view;
//        populateTimeUnitsWithFactors();
//        populateTimeUnitsWithCodes();
    }
    static {

        // Time Units
        timeUnitsWithFactors.put("Year", 31_536_000.0); // 1 Year = 365 days = 31,536,000 seconds (non-leap year)
        timeUnitsWithFactors.put("Week", 604_800.0); // 1 Week = 7 days = 604,800 seconds
        timeUnitsWithFactors.put("Day", 86_400.0); // 1 Day = 24 hours = 86,400 seconds
        timeUnitsWithFactors.put("Hour", 3600.0); // 1 Hour = 60 minutes = 3600 seconds
        timeUnitsWithFactors.put("Minute", 60.0); // 1 Minute = 60 seconds
        timeUnitsWithFactors.put("Second", 1.0); // Base unit
        timeUnitsWithFactors.put("Millisecond", 0.001); // 1 Millisecond = 0.001 seconds
        timeUnitsWithFactors.put("Microsecond", 1e-6); // 1 Microsecond = 0.000001 seconds
        timeUnitsWithFactors.put("Picosecond", 1e-12); // 1 Picosecond = 0.000000000001 seconds

    }
    static {
        timeUnitsWithCodes.put("yr","Year");
        timeUnitsWithCodes.put("wk","Week");
        timeUnitsWithCodes.put("d","Day");
        timeUnitsWithCodes.put("h","Hour");
        timeUnitsWithCodes.put("min","Minute");
        timeUnitsWithCodes.put("s","Second");
        timeUnitsWithCodes.put("ms","Millisecond");
        timeUnitsWithCodes.put("µs","Microsecond");
        timeUnitsWithCodes.put("ps","Picosecond");

    }
    public Map<String, Double> getTimeUnitsWithFactors(){
        return timeUnitsWithFactors;
    }
    public Map<String, String> getTimeUnitsWithCodes(){
        return new TreeMap<>(timeUnitsWithCodes); // for sorting
    }
}
