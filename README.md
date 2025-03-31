# multi-purpose-unit-converter-android-app
The Multipurpose Unit Converter is an android application designed to convert various unit metrics quickly and efficiently.

This app helps users convert units like Area, Length, Mass, Time, Data, Date, Discount, Speed, Temperature, Volume, Force, Energy, Pressure, Numeral Systems (like binary, decimal, hexadecimal, octal) all within a single Android application created using Java. 
It also features specialized conversations like Currency Converter, BMI Calculator, GST Calculator, Finance Calculations like EMI and Investments. 

## Specially Currency Converter :
uses api calls to fetch daily currencies once in a day and stores them in the shared preferences and access them whenever it needs. The exchange rates are provided by "exchangeratesapi.io".
For api calls it uses "retrofit" and "Gson" Libraries.

## For the rest conversions : 
it is locally done using various unit factors hashmaps(contains unit and factor based on base unit). 

## Specialized Calculators :
BMI Calculator, GST Calculator, Finance Calculations like EMI and Investments. 

All the layouts are made using XML
Various layouts like ConstraintLayout, RelativeLayout, LinearLayout, GridLayout, ScrollLayout are used in creating layouts for different activities. 

This application features smooth animation and beautiful dialogs for items selection.

It is developed by taking care in producing accurate calculations for financial conversions.
