package com.mk.model;

import com.mk.convert.DataConverterActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DataConverterModel {
    private final DataConverterActivity view;

    private static final Map<String, Double> dataUnitsWithFactors = new HashMap<>();
    private static final Map<String, String> dataUnitsWithCodes = new HashMap<>();

    public DataConverterModel(DataConverterActivity view) {
        this.view = view;
        //populateDataUnitsWithFactors();
        //populateDataUnitsWithCodes();
    }

    static {
        // Data Units
        dataUnitsWithFactors.put("Byte", 1.0); // Base unit
        dataUnitsWithFactors.put("Kilobyte", 1_024.0); // 1 KB = 1024 Bytes
        dataUnitsWithFactors.put("Megabyte", 1_048_576.0); // 1 MB = 1024^2 Bytes
        dataUnitsWithFactors.put("Gigabyte", 1_073_741_824.0); // 1 GB = 1024^3 Bytes
        dataUnitsWithFactors.put("Terabyte", 1_099_511_627_776.0); // 1 TB = 1024^4 Bytes
        dataUnitsWithFactors.put("Petabyte", 1_125_899_906_842_624.0); // 1 PB = 1024^5 Bytes

    }
    static {
        dataUnitsWithCodes.put("B", "Byte");
        dataUnitsWithCodes.put("KB", "Kilobyte");
        dataUnitsWithCodes.put("MB", "Megabyte");
        dataUnitsWithCodes.put("GB", "Gigabyte");
        dataUnitsWithCodes.put("TB", "Terabyte");
        dataUnitsWithCodes.put("PB", "Petabyte");
    }
    public Map<String, Double> getDataUnitsWithFactors() {
        return dataUnitsWithFactors;
    }
    public Map<String, String> getDataUnitsWithCodes() {
        return new TreeMap<>(dataUnitsWithCodes);
    }
}
