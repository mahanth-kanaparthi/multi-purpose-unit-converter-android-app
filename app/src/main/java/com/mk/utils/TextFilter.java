package com.mk.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;


public class TextFilter {

    public static void applyInputTextFilter(TextView textView, int startChars, int max){

        // Define the maximum number of characters
        textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max)});

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    // Maximum characters before reducing text size
                    float maxSize = 25f; // Maximum text size in SP
                    float minSize = 13f; // Minimum text size in SP

                    if (s.length() > startChars) {
                        float newSize = Math.max(minSize, maxSize - (s.length() - startChars) - 4);
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, newSize);
                    } else {
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, maxSize);
                    }


                } catch (Exception e) {
                    Log.e("ConverterApp", "Error during text change: " + e.getMessage(), e);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > max){
                    // Remove extra last characters to prevent exceeding the limit
                    s.delete(max,s.length());

                    Toast.makeText(textView.getContext(), "Can't enter more", Toast.LENGTH_SHORT).show();
                    //current.setText(s.subSequence(0, maxCharacters));
                }
            }
        });
    }

    public static void applyMultipleInputTextFilters(int start, int max, TextView ...textViews){
        for (TextView textView : textViews) {
            applyInputTextFilter(textView, start, max);
        }
    }
}
