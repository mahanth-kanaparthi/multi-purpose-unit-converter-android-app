package com.mk.model;

import com.mk.convert.EnergyConverterActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class EnergyConverterModel {

    private final EnergyConverterActivity view;

    private static final Map<String,Double> energyUnitsWithFactors = new HashMap<>();
    private static final Map<String,String> energyUnitsWithCodes = new HashMap<>();
    public EnergyConverterModel(EnergyConverterActivity view){
        this.view = view;
        //populateAreaUnitsWithFactors();
        //populateAreaUnitsWithCodes();
    }
    static {

        // Base unit: Joules (J)
        energyUnitsWithFactors.put("Btu", 1055.05585262);                // 1 Btu = ~1055.056 Joules
        energyUnitsWithFactors.put("Btu (mean)", 1055.87);              // 1 Btu (mean) = 1055.87 Joules
        energyUnitsWithFactors.put("Calories (IT)", 4.1868);            // 1 calorie (IT) = 4.1868 Joules
        energyUnitsWithFactors.put("Calories (th)", 4.184);             // 1 calorie (th) = 4.184 Joules
        energyUnitsWithFactors.put("Calories (mean)", 4.19002);         // 1 calorie (mean) = 4.19002 Joules
        energyUnitsWithFactors.put("Calories (15C)", 4.1858);           // 1 calorie (15°C) = 4.1858 Joules
        energyUnitsWithFactors.put("Calories (20C)", 4.182);            // 1 calorie (20°C) = 4.182 Joules
        energyUnitsWithFactors.put("Calories (food)", 4184.0);          // 1 calorie (food) = 4184 Joules
        energyUnitsWithFactors.put("Centigrade heat units", 1900.0);    // 1 centigrade heat unit ≈ 1900 Joules
        energyUnitsWithFactors.put("Dutch natural gas", 3.6e10);        // 1 Dutch natural gas = 3.6 × 10¹⁰ Joules
        energyUnitsWithFactors.put("Electron volts", 1.602176634e-19);  // 1 electron volt = 1.602 × 10⁻¹⁹ Joules
        energyUnitsWithFactors.put("Ergs", 1e-7);                       // 1 erg = 10⁻⁷ Joules
        energyUnitsWithFactors.put("Exajoules", 1e18);                  // 1 exajoule = 10¹⁸ Joules
        energyUnitsWithFactors.put("Foot-pound force", 1.355817948);    // 1 foot-pound force = ~1.356 Joules
        energyUnitsWithFactors.put("Foot-poundals", 0.04214011);        // 1 foot-poundal = ~0.042 Joules
        energyUnitsWithFactors.put("Gasoline", 4.4e7);                  // 1 gasoline = 44 × 10⁶ Joules
        energyUnitsWithFactors.put("Gigajoules", 1e9);                  // 1 gigajoule = 10⁹ Joules
        energyUnitsWithFactors.put("Gigawatt hours", 3.6e12);           // 1 gigawatt hour = 3.6 × 10¹² Joules
        energyUnitsWithFactors.put("Horsepower hours", 2684520.0);      // 1 horsepower hour = ~2684520 Joules
        energyUnitsWithFactors.put("Inch-pound force", 0.112984829);    // 1 inch-pound force = ~0.113 Joules
        energyUnitsWithFactors.put("Joules", 1.0);                      // 1 Joule = 1 Joule (base)
        energyUnitsWithFactors.put("Kilocalories", 4184.0);             // 1 kilocalorie = 4184 Joules
        energyUnitsWithFactors.put("Kilocalories (IT)", 4186.8);        // 1 kilocalorie (IT) = 4186.8 Joules
        energyUnitsWithFactors.put("Kilocalories (th)", 4184.0);        // 1 kilocalorie (th) = 4184 Joules
        energyUnitsWithFactors.put("Kilogram-force meters", 9.80665);   // 1 kilogram-force meter ≈ 9.807 Joules
        energyUnitsWithFactors.put("Kilojoules", 1000.0);               // 1 kilojoule = 1000 Joules
        energyUnitsWithFactors.put("Kilowatt hours", 3.6e6);            // 1 kilowatt hour = 3.6 × 10⁶ Joules
        energyUnitsWithFactors.put("Megajoules", 1e6);                  // 1 megajoule = 10⁶ Joules
        energyUnitsWithFactors.put("Megawatt hours", 3.6e9);            // 1 megawatt hour = 3.6 × 10⁹ Joules
        energyUnitsWithFactors.put("Newton meters", 1.0);               // 1 newton meter = 1 Joule
        energyUnitsWithFactors.put("Petajoules", 1e15);                 // 1 petajoule = 10¹⁵ Joules
        energyUnitsWithFactors.put("Terajoules", 1e12);                 // 1 terajoule = 10¹² Joules
        energyUnitsWithFactors.put("Terawatt hours", 3.6e15);           // 1 terawatt hour = 3.6 × 10¹⁵ Joules
        energyUnitsWithFactors.put("Therms", 105505585.262);            // 1 therm ≈ 105505585 Joules
        energyUnitsWithFactors.put("Watt seconds", 1.0);                // 1 watt second = 1 Joule
        energyUnitsWithFactors.put("Watt hours", 3600.0);               // 1 watt hour = 3600 Joules
    }
    static {

        energyUnitsWithCodes.put("Btu", "BTU");
        energyUnitsWithCodes.put("Btu (mean)", "BTU (mean)");
        energyUnitsWithCodes.put("cal (IT)", "Calories (IT)");
        energyUnitsWithCodes.put("cal (th)", "Calories (th)");
        energyUnitsWithCodes.put("cal (mean)", "Calories (mean)");
        energyUnitsWithCodes.put("cal (15C)", "Calories (15C)");
        energyUnitsWithCodes.put("cal (20C)", "Calories (20C)");
        energyUnitsWithCodes.put("cal (food)", "Calories (food)");
        energyUnitsWithCodes.put("ch", "Centigrade heat units");
        energyUnitsWithCodes.put("dng/LHV", "Dutch natural gas");
        energyUnitsWithCodes.put("eV", "Electron volts");
        energyUnitsWithCodes.put("ergs", "Ergs");
        energyUnitsWithCodes.put("EJ", "Exajoules");
        energyUnitsWithCodes.put("ft-lbf", "Foot-pound force");
        energyUnitsWithCodes.put("ft-pdl", "Foot-poundals");
        energyUnitsWithCodes.put("L", "Gasoline");
        energyUnitsWithCodes.put("GJ", "Gigajoules");
        energyUnitsWithCodes.put("GWh", "Gigawatt hours");
        energyUnitsWithCodes.put("hp-H", "Horsepower hours");
        energyUnitsWithCodes.put("in-lbf", "Inch-pound force");
        energyUnitsWithCodes.put("J", "Joules");
        energyUnitsWithCodes.put("kcal", "Kilocalories");
        energyUnitsWithCodes.put("kcal (IT)", "Kilocalories (IT)");
        energyUnitsWithCodes.put("kcal (th)", "Kilocalories (th)");
        energyUnitsWithCodes.put("kg-m", "Kilogram-force meters");
        energyUnitsWithCodes.put("kJ", "Kilojoules");
        energyUnitsWithCodes.put("kWh", "Kilowatt hours");
        energyUnitsWithCodes.put("MJ", "Megajoules");
        energyUnitsWithCodes.put("MWh", "Megawatt hours");
        energyUnitsWithCodes.put("Nm", "Newton meters");
        energyUnitsWithCodes.put("PJ", "Petajoules");
        energyUnitsWithCodes.put("TJ", "Terajoules");
        energyUnitsWithCodes.put("TWh", "Terawatt hours");
        energyUnitsWithCodes.put("therm", "Therms");
        energyUnitsWithCodes.put("Ws", "Watt seconds");
        energyUnitsWithCodes.put("Wh", "Watt hours");

    }
    public Map<String,Double> getEnergyUnitsWithFactors(){
        return energyUnitsWithFactors;
    }
    public Map<String,String> getEnergyUnitsWithCodes(){
        return new TreeMap<>(energyUnitsWithCodes); // for sorting
    }
}
