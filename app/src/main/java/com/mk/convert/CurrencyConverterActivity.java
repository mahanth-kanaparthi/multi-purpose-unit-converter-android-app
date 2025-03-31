package com.mk.convert;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mk.controller.CurrencyConverterController;
import com.mk.model.CurrencyConverterModel;
import com.mk.utils.ExpressionEvaluator;
import com.mk.utils.MapDialog;
import com.mk.utils.TextFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CurrencyConverterActivity extends BaseActivity{


    TextView t1,t2,t3,resultListViewValue1,resultListViewValue2;
    TextView currencyText1,ct_code1,currencyText2,ct_code2,currencyText3,ct_code3,selectedTextView,selectedCurrencyCode;
    TextView exchangeRateNoteTextView;
    private boolean firstTimePressed = false;
    private final Handler handler = new Handler();
    CurrencyConverterController controller;
    CurrencyConverterModel model;

    //testing purpose
    public List<TextView> currencies;
    public List<TextView> codes;
    public List<TextView> amounts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.currency_activity_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.currency_activity_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeResources();
        initializeActivitySpecificResources();
        setupExtraListeners();
        setupListeners();

        //applying input text filters to the textviews
        TextFilter.applyMultipleInputTextFilters(10,19,t1,t2,t3);

        // Initialize the controller
        controller = new CurrencyConverterController(this);
        // Initialize the model
        model = new CurrencyConverterModel(this);
        exchangeRateNoteTextView.setText(model.getExchangeRatesDataNote());

    }

    private void initializeActivitySpecificResources(){
        selectedTextView=null;
        //selectedTextViewValue = null;
        currencyText1=findViewById(R.id.currencyFirstListView);
        currencyText2=findViewById(R.id.currencySecondListView);
        currencyText3=findViewById(R.id.currencyThirdListView);
        ct_code1=findViewById(R.id.currencyFirstListViewCode);
        ct_code2=findViewById(R.id.currencySecondListViewCode);
        ct_code3=findViewById(R.id.currencyThirdListViewCode);
        t1=findViewById(R.id.currencyFirstListViewValue);
        t2=findViewById(R.id.currencySecondListViewValue);
        t3=findViewById(R.id.currencyThirdListViewValue);

        exchangeRateNoteTextView = findViewById(R.id.exchangeRateNote);

        currencies = new ArrayList<>((Arrays.asList(currencyText1,currencyText2,currencyText3)));
        codes = new ArrayList<>(Arrays.asList(ct_code1,ct_code2,ct_code3));
        amounts = new ArrayList<>(Arrays.asList(t1, t2, t3));
    }

    private void setupExtraListeners(){

        int defaultFontColor = getResources().getColor(R.color.black);
        int selectedFontColor = getResources().getColor(R.color.orange);

        t1.setOnClickListener(v -> {
            selectedTextViewValue = t1;
            setValueTextLimitations(t1);
            resultListViewValue1 = t2;
            resultListViewValue2 = t3;

            t1.setTextColor(selectedFontColor);
            t2.setTextColor(defaultFontColor);
            t3.setTextColor(defaultFontColor);

            String currentText = selectedTextViewValue.getText().toString();
            if(!firstTimePressed && !currentText.equals("1")){
                selectedTextViewValue.setText("1");
                firstTimePressed = true;
            }

            //debug purpose
            System.out.println("t1 : "+t1.getText().toString());
            System.out.println("selected value : "+selectedTextViewValue.getText().toString());

        });
        t2.setOnClickListener(v -> {
            selectedTextViewValue = t2;
            setValueTextLimitations(t2);
            resultListViewValue1 = t1;
            resultListViewValue2 = t3;

            t2.setTextColor(selectedFontColor);
            t1.setTextColor(defaultFontColor);
            t3.setTextColor(defaultFontColor);

            String currentText = selectedTextViewValue.getText().toString();
            if (!firstTimePressed && !currentText.equals("1")) {
                selectedTextViewValue.setText("1");
                firstTimePressed = true;
            }

            //debug purpose
            System.out.println("t2 : "+t2.getText().toString());
            System.out.println("selected value : "+selectedTextViewValue.getText().toString());

        });
        t3.setOnClickListener(v -> {
            selectedTextViewValue = t3;
            setValueTextLimitations(t3);
            resultListViewValue1 = t1;
            resultListViewValue2 = t2;

            t3.setTextColor(selectedFontColor);
            t1.setTextColor(defaultFontColor);
            t2.setTextColor(defaultFontColor);

            String currentText = selectedTextViewValue.getText().toString();
            if (!firstTimePressed && !currentText.equals("1")) {
                selectedTextViewValue.setText("1");
                firstTimePressed = true;
            }
            //debug purpose
            System.out.println("t2 : "+t2.getText().toString());
            System.out.println("selected value : "+selectedTextViewValue.getText().toString());

        });

        currencyText1.setOnClickListener(v -> {
            selectedTextView = currencyText1;
            selectedCurrencyCode = ct_code1;
            showAlertDialog(currencyText1,ct_code1);
        });
        currencyText2.setOnClickListener(v -> {
            selectedTextView = currencyText2;
            selectedCurrencyCode = ct_code2;
            showAlertDialog(currencyText2,ct_code2);
        });
        currencyText3.setOnClickListener(v -> {
            selectedTextView = currencyText3;
            selectedCurrencyCode = ct_code3;
            showAlertDialog(currencyText3,ct_code3);
        });
    }


    private void showAlertDialog(TextView unitName,TextView unitCode){
        // get map from model
        Map<String, String> map = model.getCountriesList();

        MapDialog.showMapDialog(this, map, unitName,unitCode);

    }

    public void updateUI(double result1,double result2){
        resultListViewValue1.setText(String.valueOf(result1));
        resultListViewValue2.setText(String.valueOf(result2));
    }

    @Override
    protected void updateConvertedValues(TextView selectedValue) {

        calculateConversion(selectedValue);
    }

    public void calculateConversion(TextView currencyAmount){

        if (currencyAmount == null || amounts == null || codes == null || model == null) {
            Log.e("CurrencyApp", "Invalid state in calculateConversion: Null values detected");
            return;
        }

        int idx = amounts.indexOf(currencyAmount);
        if (idx == -1 || idx >= codes.size()) {
            Log.e("CurrencyApp", "TextView not found in amounts or index out of range");
            return;
        }

        String amount = currencyAmount.getText().toString();
        double resultAmount = 0;

        try {
            if (amount.contains("+") || amount.contains("-") || amount.contains("×") || amount.contains("÷")) {
                amount = amount.replace("×", "*").replace("÷", "/");
                resultAmount = ExpressionEvaluator.evaluateExpression(amount);
            } else {
                resultAmount = Double.parseDouble(amount);
            }
        } catch (Exception e) {
            Log.e("CurrencyApp", "Error parsing amount: " + e.getMessage(), e);
            return;
        }

        TextView baseCurrencyTextView = (TextView) codes.get(idx);
        String baseCurrency = baseCurrencyTextView.getText().toString();
        System.out.println("1base currency : " + baseCurrency);

        for (int i = 0; i < codes.size(); i++) {
            if (i != idx) { // Skip the selected currency
                TextView targetCurrencyTextView = (TextView) codes.get(i);
                String targetCurrency = targetCurrencyTextView.getText().toString();
                System.out.println("1target currency : " + targetCurrency);

                try {

                    Double baseRate = model.getExchangeRatesMap().get(baseCurrency);
                    Double targetRate = model.getExchangeRatesMap().get(targetCurrency);

                    if (baseRate == null || targetRate == null) {
                        Log.e("CurrencyApp", "Missing exchange rate for base/target currency");
                        continue;
                    }

                    double conversionRate = targetRate / baseRate;
                    double convertedAmount = resultAmount * conversionRate;

                    if (i < amounts.size() && amounts.get(i) != null) {
                        amounts.get(i).setText(String.format("%.4f", convertedAmount));
                    }
                } catch (Exception e) {
                    Log.e("CurrencyApp", "Error during conversion: " + e.getMessage(), e);
                }
            }
        }
    }
}
