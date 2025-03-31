package com.mk.model;

import com.mk.convert.NumeralsConverterActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class NumeralsConverterModel {

    private final NumeralsConverterActivity view;

    public static final Map<String, Integer> numeralUnitsWithBases;
    private static final Map<String,String> numeralUnitsWithCodes;

    public NumeralsConverterModel(NumeralsConverterActivity view){
        this.view = view;
        //populateAreaUnitsWithFactors();
        //populateAreaUnitsWithCodes();
    }
    static {

        numeralUnitsWithBases = new HashMap<>();
        numeralUnitsWithBases.put("Binary", 2);       // Base 2
        numeralUnitsWithBases.put("Octal", 8);       // Base 8
        numeralUnitsWithBases.put("Decimal", 10);    // Base 10
        numeralUnitsWithBases.put("Hexadecimal", 16);// Base 16

    }
    static {

        numeralUnitsWithCodes = new HashMap<>();
        numeralUnitsWithCodes.put("BIN","Binary");
        numeralUnitsWithCodes.put("OCT","Octal");
        numeralUnitsWithCodes.put("DEC","Decimal");
        numeralUnitsWithCodes.put("HEX","Hexadecimal");

    }
    public Map<String,Integer> getNumeralsUnitsWithBases(){
        return numeralUnitsWithBases;
    }
    public Map<String,String> getNumeralsUnitsWithCodes(){
        return new TreeMap<>(numeralUnitsWithCodes); // for sorting
    }
}
