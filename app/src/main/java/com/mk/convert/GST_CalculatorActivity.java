package com.mk.convert;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mk.utils.ExpressionEvaluator;
import com.mk.utils.StringFormatterWithPattern;
import com.mk.utils.TextFilter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GST_CalculatorActivity extends BaseActivity{

    private TextView originalPrice,percentage3,percentage5,percentage12,percentage18,percentage28,finalPrice,selectedTextView;
    private TextView cgst_sgst_value;
    private byte gstPercent = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.gst_activity_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gst_activity_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        initializeResources();
        setupExtraListeners();
        setupListeners();

        //applying input text filters to the textviews
        TextFilter.applyMultipleInputTextFilters(15,25,originalPrice,finalPrice);

        //applying default values
        applyDefaults();

        performGSTCalculation(originalPrice);
    }
    protected void initializeViews(){
        selectedTextView = null;
        originalPrice=findViewById(R.id.tv_originalPriceValue);
        percentage3=findViewById(R.id.gst_3Percent);
        percentage5=findViewById(R.id.gst_5Percent);
        percentage12=findViewById(R.id.gst_12Percent);
        percentage18=findViewById(R.id.gst_18Percent);
        percentage28=findViewById(R.id.gst_28Percent);
        finalPrice=findViewById(R.id.tv_final_price_value);
        cgst_sgst_value=findViewById(R.id.tv_cgst_sgst_value);
        backBtn = findViewById(R.id.backBtn);

        //default initialization
        selectedTextViewValue = originalPrice;
        setValueTextLimitations(originalPrice);
        originalPrice.setTextColor(getColor(R.color.orange));
        finalPrice.setTextColor(getColor(R.color.black));
    }

    protected void setupExtraListeners(){

        int defaultColor = getColor(R.color.black);
        int selectedColor = getColor(R.color.orange);
        int defaultTextViewColor = getColor(R.color.white);
        Drawable defaultBackground = getResources().getDrawable(R.drawable.gst_option_bg);
        Drawable selectedBackground = getResources().getDrawable(R.drawable.gst_selected_option_bg);
        // Handle selection for original price
        originalPrice.setOnClickListener(v -> {
            selectedTextViewValue = originalPrice;
            setValueTextLimitations(originalPrice);

            originalPrice.setTextColor(selectedColor);
            finalPrice.setTextColor(defaultColor);
        });
        // Handle selection for final price
        finalPrice.setOnClickListener(v -> {
            selectedTextViewValue = finalPrice;
            setValueTextLimitations(finalPrice);

            finalPrice.setTextColor(selectedColor);
            originalPrice.setTextColor(defaultColor);
        });

        percentage3.setOnClickListener(v -> {
            gstPercent = 3;
            percentage3.setBackground(selectedBackground);
            percentage5.setBackground(defaultBackground);
            percentage12.setBackground(defaultBackground);
            percentage18.setBackground(defaultBackground);
            percentage28.setBackground(defaultBackground);

            if (selectedTextViewValue != null)
                performGSTCalculation(selectedTextViewValue);
        });
        percentage5.setOnClickListener(v -> {
            gstPercent = 5;
            percentage3.setBackground(defaultBackground);
            percentage5.setBackground(selectedBackground);
            percentage12.setBackground(defaultBackground);
            percentage18.setBackground(defaultBackground);
            percentage28.setBackground(defaultBackground);

            if (selectedTextViewValue != null)
                performGSTCalculation(selectedTextViewValue);
        });
        percentage12.setOnClickListener(v -> {
            gstPercent = 12;
            percentage3.setBackground(defaultBackground);
            percentage5.setBackground(defaultBackground);
            percentage12.setBackground(selectedBackground);
            percentage18.setBackground(defaultBackground);
            percentage28.setBackground(defaultBackground);

            if (selectedTextViewValue != null)
                performGSTCalculation(selectedTextViewValue);
        });
        percentage18.setOnClickListener(v -> {
            gstPercent = 18;
            percentage3.setBackground(defaultBackground);
            percentage5.setBackground(defaultBackground);
            percentage12.setBackground(defaultBackground);
            percentage18.setBackground(selectedBackground);
            percentage28.setBackground(defaultBackground);

            if (selectedTextViewValue != null)
                performGSTCalculation(selectedTextViewValue);
        });
        percentage28.setOnClickListener(v -> {
            gstPercent = 28;
            percentage3.setBackground(defaultBackground);
            percentage5.setBackground(defaultBackground);
            percentage12.setBackground(defaultBackground);
            percentage18.setBackground(defaultBackground);
            percentage28.setBackground(selectedBackground);

            if (selectedTextViewValue != null)
                performGSTCalculation(selectedTextViewValue);
        });

    }
    private void applyDefaults(){
        gstPercent = 5;
        originalPrice.setText(getResources().getString(R.string.tv_originalPriceValue));
        finalPrice.setText(getResources().getString(R.string.tv_final_price_value));
        cgst_sgst_value.setText(getResources().getString(R.string.tv_cgst_sgst_value));
    }
    private void performGSTCalculation(TextView selectedValue) {
        BigDecimal originalPriceValue;
        BigDecimal gstAmount;
        BigDecimal finalPriceValue;

        if(selectedValue == originalPrice){
            originalPriceValue = getEnteredValue(selectedValue);
            gstAmount = originalPriceValue.multiply(BigDecimal.valueOf(gstPercent)).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            finalPriceValue = originalPriceValue.add(gstAmount);
            updateCgstSgstValue(gstAmount);
            finalPrice.setText(finalPriceValue.toPlainString());
        }else{
            finalPriceValue = getEnteredValue(selectedValue);
            originalPriceValue = finalPriceValue.divide(BigDecimal.ONE.add(BigDecimal.valueOf(gstPercent).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)), 2, RoundingMode.HALF_UP);
            gstAmount = finalPriceValue.subtract(originalPriceValue);
            updateCgstSgstValue(gstAmount);
            originalPrice.setText(originalPriceValue.toPlainString());
        }
    }
    private BigDecimal getEnteredValue(TextView textView) {
        String value = textView.getText().toString();
        if (value.isEmpty()) {
            return BigDecimal.ZERO;
        }

        double resultValue = 0.0;

        try {
            if (value.contains("+") || value.contains("-") || value.contains("×") || value.contains("÷")) {
                value = value.replace("×", "*").replace("÷", "/");

                if(value.endsWith("+") || value.endsWith("-") || value.endsWith("*") || value.endsWith("/")){
                    value = value.substring(0,value.length()-1);
                }

                resultValue = ExpressionEvaluator.evaluateExpression(value);
            } else {
                resultValue = Double.parseDouble(value);
            }
        } catch (Exception e) {
            Log.e("GST_CalculatorApp", "Error parsing value: " + e.getMessage(), e);
            return BigDecimal.ZERO;
        }
        return new BigDecimal(resultValue);
    }
    private void updateCgstSgstValue(BigDecimal gstAmount) {
        double cgst_by_sgst = gstAmount.divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP).doubleValue();
        String cgstSgstValue = "CGST/SGST: "+StringFormatterWithPattern.formatString(cgst_by_sgst, "#.00");
        cgst_sgst_value.setText(cgstSgstValue);
    }
    @Override
    protected void updateConvertedValues(TextView selectedValue) {
        performGSTCalculation(selectedValue);
    }

}
