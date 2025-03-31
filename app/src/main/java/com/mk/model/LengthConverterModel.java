package com.mk.model;

import com.mk.convert.LengthConverterActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LengthConverterModel {

    private final LengthConverterActivity view;

    private static final Map<String,Double> lengthUnitsWithFactors = new HashMap<>();
    private static final Map<String,String> lengthUnitsWithCodes = new HashMap<>();
    public LengthConverterModel(LengthConverterActivity view) {
        this.view = view;
        //populateLengthUnitsWithFactors();
        //populateLengthUnitsWithCodes();
    }
    static {

        lengthUnitsWithFactors.put("Kilometer", 1000.0);
        lengthUnitsWithFactors.put("Meter", 1.0);
        lengthUnitsWithFactors.put("Decimeter", 0.1);
        lengthUnitsWithFactors.put("Centimeter", 0.01);
        lengthUnitsWithFactors.put("Millimeter", 0.001);
        lengthUnitsWithFactors.put("Micrometer", 1e-6);
        lengthUnitsWithFactors.put("Nanometer", 1e-9);
        lengthUnitsWithFactors.put("Picometer", 1e-12);
        lengthUnitsWithFactors.put("Nautical Mile", 1852.0);
        lengthUnitsWithFactors.put("Mile", 1609.344);
        lengthUnitsWithFactors.put("Furlong", 201.168);
        lengthUnitsWithFactors.put("Fathom", 1.8288);
        lengthUnitsWithFactors.put("Yard", 0.9144);
        lengthUnitsWithFactors.put("Foot", 0.3048);
        lengthUnitsWithFactors.put("Inch", 0.0254);
        lengthUnitsWithFactors.put("Li", 500.0); // 1 Li = 500 meters
        lengthUnitsWithFactors.put("Zhang", 10.0); // 1 Zhang = 10 meters
        lengthUnitsWithFactors.put("Chi", 1.0 / 3.0); // 1 Chi = 1/3 meter
        lengthUnitsWithFactors.put("Cun", 0.033333); // 1 Cun = 1/30 meter
        lengthUnitsWithFactors.put("Fen", 0.003333); // 1 Fen = 1/300 meter
        lengthUnitsWithFactors.put("Lii", 0.000333); // 1 Lii = 1/3000 meter
        lengthUnitsWithFactors.put("Hao", 0.0000333); // 1 Hao = 1/30000 meter
        lengthUnitsWithFactors.put("Parsec", 3.085677581e16); // 1 Parsec = 3.085677581 × 10^16 meters
        lengthUnitsWithFactors.put("Lunar Distance", 384400000.0); // Average distance from Earth to Moon in meters
        lengthUnitsWithFactors.put("Astronomical Unit", 149597870700.0); // 1 AU = 149,597,870,700 meters
        lengthUnitsWithFactors.put("Light Year", 9.4607e15); // 1 Light Year = 9.4607 × 10^15 meters

    }
    static {
        lengthUnitsWithCodes.put("km", "Kilometer");
        lengthUnitsWithCodes.put("m", "Meter");
        lengthUnitsWithCodes.put("dm", "Decimeter");
        lengthUnitsWithCodes.put("cm", "Centimeter");
        lengthUnitsWithCodes.put("mm", "Millimeter");
        lengthUnitsWithCodes.put("um", "Micrometer");
        lengthUnitsWithCodes.put("nm", "Nanometer");
        lengthUnitsWithCodes.put("pm", "Picometer");
        lengthUnitsWithCodes.put("Nm", "Nautical Mile");
        lengthUnitsWithCodes.put("mi", "Mile");
        lengthUnitsWithCodes.put("fur", "Furlong");
        lengthUnitsWithCodes.put("ftm", "Fathom");
        lengthUnitsWithCodes.put("yd", "Yard");
        lengthUnitsWithCodes.put("ft", "Foot");
        lengthUnitsWithCodes.put("in", "Inch");
        lengthUnitsWithCodes.put("li", "Li");
        lengthUnitsWithCodes.put("zh", "Zhang");
        lengthUnitsWithCodes.put("chi", "Chi");
        lengthUnitsWithCodes.put("cun", "Cun");
        lengthUnitsWithCodes.put("fen", "Fen");
        lengthUnitsWithCodes.put("lii", "Lii");
        lengthUnitsWithCodes.put("hao", "Hao");
        lengthUnitsWithCodes.put("pc", "Parsec");
        lengthUnitsWithCodes.put("ld", "Lunar Distance");
        lengthUnitsWithCodes.put("au", "Astronomical Unit");
        lengthUnitsWithCodes.put("ly", "Light Year");
    }

    public Map<String,Double> getLengthUnitsWithFactors(){
        return lengthUnitsWithFactors;
    }
    public Map<String,String> getLengthUnitsWithCodes(){
        return new TreeMap<>(lengthUnitsWithCodes);
    }
}
