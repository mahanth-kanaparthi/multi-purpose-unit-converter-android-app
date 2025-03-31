package com.mk.convert;


import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;

public class DiscountConverterActivity extends BaseActivity {

    public TextView originalPrice,discountPercent,selectedTextView,finalPrice,savedValue;
    public TextView originalPriceValue,discountPercentValue,finalPriceValue;

    boolean invalidEntries = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.discount_activity_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.discount_activity_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        initializeResources();
        setupExtraListeners();
        setupListeners();


    }
    protected void initializeViews(){
        selectedTextView = null;
        originalPrice=findViewById(R.id.discountFirstTextView);
        discountPercent=findViewById(R.id.discountSecondTextView);
        finalPrice = findViewById(R.id.discountThirdTextView);
        originalPriceValue=findViewById(R.id.discountFirstTextViewValue);
        discountPercentValue=findViewById(R.id.discountSecondTextViewValue);
        finalPriceValue = findViewById(R.id.discountThirdTextViewValue);
        savedValue = findViewById(R.id.discount_savedValue);


    }

    @Override
    protected void setupListeners(){
        Button[] buttons = {btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_0,btn_00,btn_backspace,
                btn_clear, btn_dot};

        for (Button button : buttons) {
            button.setOnClickListener(v -> updateSelectedTextView(button.getText().toString()));
        }

        setupBackspaceBtn();

        backBtn.setOnClickListener(v -> finish());
        sizeReduceTextWatcher(finalPriceValue);
    }

    protected void setupExtraListeners() {
        int defaultFontColor = getResources().getColor(R.color.black);
        int selectedFontColor = getResources().getColor(R.color.orange);

        // Handle selection for original price
        originalPriceValue.setOnClickListener(v -> {
            selectedTextViewValue = originalPriceValue;
            setValueTextLimitations(originalPriceValue);

            originalPriceValue.setTextColor(selectedFontColor);
            discountPercentValue.setTextColor(defaultFontColor);
            finalPriceValue.setTextColor(defaultFontColor);
        });

        // Handle selection for discount percentage
        discountPercentValue.setOnClickListener(v -> {
            selectedTextViewValue = discountPercentValue;
            setValueTextLimitations(discountPercentValue);

            discountPercentValue.setTextColor(selectedFontColor);
            originalPriceValue.setTextColor(defaultFontColor);
            finalPriceValue.setTextColor(defaultFontColor);
        });

    }

    // Method to recalculate final price
    private void updateFinalPrice() {
        double finalPriceResult = performConversion();
        finalPriceValue.setText(String.format("%.2f", finalPriceResult));
    }

    @Override
    protected void updateConvertedValues(TextView selectedValue) {
        try {
            // Perform discount calculation and update the UI
            double finalPriceResult = performConversion();
            BigDecimal finalPrice = BigDecimal.valueOf(finalPriceResult);

            // Format the final price with commas
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            String formattedFinalPrice = decimalFormat.format(finalPrice);

            // Display the formatted final price
            finalPriceValue.setText(formattedFinalPrice);

            // Calculate and display the saved value
            BigDecimal originalValue = new BigDecimal(originalPriceValue.getText().toString());
            BigDecimal savedAmount = originalValue.subtract(finalPrice);

            // Format the saved amount with commas
            String formattedSavedAmount = decimalFormat.format(savedAmount);

            savedValue.setText(String.format("You saved: %s", formattedSavedAmount));

        } catch (NumberFormatException e) {
            Log.e("DiscountConverter", "Error converting values: " + e.getMessage(), e);
        }
    }


    private double performConversion() {
        try {
            // Get input values
            String originalPriceStr = originalPriceValue.getText().toString();
            String discountPercentStr = discountPercentValue.getText().toString();

            if (originalPriceStr.isEmpty() || discountPercentStr.isEmpty()) {
                return 0; // Return 0 if inputs are missing
            }

            // Convert inputs to BigDecimal for precision
            BigDecimal originalValue = new BigDecimal(originalPriceStr);
            BigDecimal discountPercent = new BigDecimal(discountPercentStr);

            // Validate inputs
            if (
                    (discountPercent.compareTo(BigDecimal.ZERO) < 0
                    || discountPercent.compareTo(new BigDecimal("100")) > 0
                    || originalValue.compareTo(BigDecimal.ZERO) < 0)
                    && !invalidEntries) {
                Toast.makeText(this, "Invalid values entered", Toast.LENGTH_SHORT).show();
                invalidEntries = true;
                return 0;
            }
            invalidEntries = false;

            // Calculate the discount amount and final price
            BigDecimal discountAmount = originalValue. multiply(discountPercent).divide(new BigDecimal("100"), MathContext.DECIMAL128);
            BigDecimal finalPrice = originalValue.subtract(discountAmount);

            return finalPrice.doubleValue();

        } catch (Exception e) {
            Log.e("DiscountConverter", "Error performing conversion: " + e.getMessage(), e);
            return 0; // Return 0 in case of any error
        }
    }

    @Override
    protected void setValueTextLimitations(TextView current) {
        super.setValueTextLimitations(current);

        // Add a listener to trigger conversions for both fields
        current.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (!isConversionInProgress) {
                        isConversionInProgress = true;
                        updateConvertedValues(current);
                        isConversionInProgress = false;
                    }
                } catch (Exception e) {
                    Log.e("ConverterApp", "Error during text change: " + e.getMessage(), e);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void sizeReduceTextWatcher(TextView textView){
        int maxCharacters = 25; // Define the maximum number of characters
        textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCharacters)});
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int maxCharactersAtStart = 10; // Maximum characters before reducing text size
                    float maxSize = 25f; // Maximum text size in SP
                    float minSize = 13f; // Minimum text size in SP

                    if (s.length() > maxCharactersAtStart) {
                        float newSize = Math.max(minSize, maxSize - (s.length() - maxCharactersAtStart) - 4);
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, newSize);
                    } else {
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, maxSize);
                    }
                } catch (Exception e) {
                    Log.e("DiscountApp", "Error during text change: " + e.getMessage(), e);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() > maxCharacters) {
                    // Remove extra last characters to prevent exceeding the limit
                    s.delete(maxCharacters, s.length());
                }
            }
        });
    }

}
