package com.mk.convert;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mk.utils.ExpressionEvaluator;

public abstract class BaseActivity extends AppCompatActivity {
    protected ImageButton backBtn;
    protected Button btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_clear,btn_plus,btn_minus,btn_multiply,btn_divide,btn_equals,btn_backspace,btn_percent,btn_0,btn_00,btn_dot;
    protected boolean firstTimePressed = false;
    protected boolean isLongPressed = false;
    private boolean isLongPressDetected = false; // Flag to track long press
    protected TextView selectedTextViewValue;

    protected final Handler handler = new Handler(Looper.getMainLooper());

    protected boolean isConversionInProgress = false;


    protected void initializeResources(){
        //selectedTextViewValue = null;
        btn_1=findViewById(R.id.btn_1);
        btn_2=findViewById(R.id.btn_2);
        btn_3=findViewById(R.id.btn_3);
        btn_4=findViewById(R.id.btn_4);
        btn_5=findViewById(R.id.btn_5);
        btn_6=findViewById(R.id.btn_6);
        btn_7=findViewById(R.id.btn_7);
        btn_8=findViewById(R.id.btn_8);
        btn_9=findViewById(R.id.btn_9);
        btn_0=findViewById(R.id.btn_0);
        btn_00=findViewById(R.id.btn_00);
        btn_clear=findViewById(R.id.btn_clear);
        btn_plus=findViewById(R.id.btn_plus);
        btn_minus=findViewById(R.id.btn_minus);
        btn_multiply=findViewById(R.id.btn_multiply);
        btn_divide=findViewById(R.id.btn_divide);
        btn_equals=findViewById(R.id.btn_equals);
        btn_backspace=findViewById(R.id.btn_backspace);
        btn_percent=findViewById(R.id.btn_percent);
        btn_dot=findViewById(R.id.btn_dot);

        backBtn = findViewById(R.id.backBtn);
    }

    protected void setupListeners(){
        Button [] buttons = {btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_0,btn_00,btn_backspace,
                btn_clear,btn_plus,btn_minus,btn_multiply,btn_divide,
                btn_dot,btn_equals};

        for (Button button : buttons) {
            button.setOnClickListener(v -> {
                updateSelectedTextView(button.getText().toString());
            });
        }

        setupBackspaceBtn();

        backBtn.setOnClickListener(v -> finish());
    }

    protected void setupBackspaceBtn(){
        // Set a long-click listener for the button
        btn_backspace.setOnLongClickListener(view -> {
            isLongPressed = true;
            if (selectedTextViewValue != null) {
                handler.postDelayed(removeTextRunnable, 100);
            }
            return true; // Consume the long click event
        });

        // Set an onClickListener for the button
        btn_backspace.setOnClickListener(view -> {
            if (!isLongPressed && selectedTextViewValue != null) { // Only handle short clicks if not long pressing
                String currentText = selectedTextViewValue.getText().toString();
                if (currentText.equals("1") || currentText.equals("0")) {
                    selectedTextViewValue.setText("0");
                } else if (!currentText.isEmpty() && currentText.length() > 1) {
                    selectedTextViewValue.setText(currentText.substring(0, currentText.length() - 1));
                } else {
                    selectedTextViewValue.setText("0");
                }

                if (currentText.startsWith("-") && currentText.length() < 3) {
                    selectedTextViewValue.setText("0");
                }
            }
        });


        // Stop the long-press action when the button is released
        btn_backspace.setOnTouchListener((v, event) -> {
            if (selectedTextViewValue != null && (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)) {
                if (isLongPressed) {
                    isLongPressed = false;
                    handler.removeCallbacks(removeTextRunnable); // Stop the runnable
                }
                if(selectedTextViewValue.getText().toString().isEmpty()){
                    selectedTextViewValue.setText("0");
                }
            }
            return false;
        });
    }

    protected void updateSelectedTextView(String selectedButton) {

        if (selectedTextViewValue != null) {

            String currentText = selectedTextViewValue.getText().toString();

            boolean operatorPressed = currentText.endsWith("+")
                    || currentText.endsWith("-")
                    || currentText.endsWith("×")
                    || currentText.endsWith("÷");

            switch(selectedButton){

                case "1":
                    if(currentText.equals("1") && firstTimePressed){
                        selectedTextViewValue.setText("1");
                        firstTimePressed = false;
                    }else if(currentText.equals("0")) {
                        selectedTextViewValue.setText("1");
                        System.out.println(currentText);
                    }
                    else
                        selectedTextViewValue.setText(currentText + "1");
                    break;
                case "2":
                    if(currentText.equals("1") && firstTimePressed){
                        selectedTextViewValue.setText("2");
                        firstTimePressed = false;
                    }else if(currentText.equals("0"))
                        selectedTextViewValue.setText("2");
                    else
                        selectedTextViewValue.setText(currentText + 2);
                    break;
                case "3":
                    if(currentText.equals("1") && firstTimePressed){
                        selectedTextViewValue.setText("3");
                        firstTimePressed = false;
                    }else if(currentText.equals("0"))
                        selectedTextViewValue.setText("3");
                    else
                        selectedTextViewValue.setText(currentText + 3);
                    break;
                case "4":
                    if(currentText.equals("1") && firstTimePressed){
                        selectedTextViewValue.setText("4");
                        firstTimePressed = false;
                    }else if(currentText.equals("0"))
                        selectedTextViewValue.setText("4");
                    else
                        selectedTextViewValue.setText(currentText + 4);
                    break;
                case "5":
                    if(currentText.equals("1") && firstTimePressed){
                        selectedTextViewValue.setText("5");
                        firstTimePressed = false;
                    }else if(currentText.equals("0"))
                        selectedTextViewValue.setText("5");
                    else
                        selectedTextViewValue.setText(currentText + 5);
                    break;
                case "6":
                    if(currentText.equals("1") && firstTimePressed){
                        selectedTextViewValue.setText("6");
                        firstTimePressed = false;
                    }else if(currentText.equals("0"))
                        selectedTextViewValue.setText("6");
                    else
                        selectedTextViewValue.setText(currentText + 6);
                    break;
                case "7":
                    if(currentText.equals("1") && firstTimePressed){
                        selectedTextViewValue.setText("7");
                        firstTimePressed = false;
                    }else if(currentText.equals("0"))
                        selectedTextViewValue.setText("7");
                    else
                        selectedTextViewValue.setText(currentText + 7);

                    break;
                case "8":
                    if(currentText.equals("1") && firstTimePressed){
                        selectedTextViewValue.setText("8");
                        firstTimePressed = false;
                    }else if(currentText.equals("0"))
                        selectedTextViewValue.setText("8");
                    else
                        selectedTextViewValue.setText(currentText + 8);
                    break;
                case "9":
                    if(currentText.equals("1") && firstTimePressed){
                        selectedTextViewValue.setText("9");
                        firstTimePressed = false;
                    }else if(currentText.equals("0"))
                        selectedTextViewValue.setText("9");
                    else
                        selectedTextViewValue.setText(currentText + 9);
                    break;
                case "0":
                    if(currentText.equals("1") && firstTimePressed){
                        selectedTextViewValue.setText("0");
                        firstTimePressed = false;
                    }else if(currentText.equals("0"))
                        selectedTextViewValue.setText("0");
                    else
                        selectedTextViewValue.setText(currentText + 0);

                    break;
                case "00":
                    if(currentText.equals("1") && firstTimePressed){
                        selectedTextViewValue.setText("0");
                        firstTimePressed = false;
                    }else if(currentText.equals("0"))
                        selectedTextViewValue.setText("0");
                    else
                        selectedTextViewValue.setText(currentText + "00");
                    break;
                case ".":
                    if(!currentText.contains(".")){
                        selectedTextViewValue.setText(currentText + ".");
                    }
                    break;
                case "+":
                    if(!operatorPressed) {
                        selectedTextViewValue.setText(currentText + "+");
                        operatorPressed = true;
                    }else if(operatorPressed){
                        selectedTextViewValue.setText(currentText.substring(0, currentText.length() - 1) + "+");
                    }

                    break;
                case "-":
                    if(!operatorPressed) {
                        selectedTextViewValue.setText(currentText + "-");
                        operatorPressed = true;
                    }else if(operatorPressed){
                        selectedTextViewValue.setText(currentText.substring(0, currentText.length() - 1) + "-");
                    }
                    break;
                case "×":
                    if(!operatorPressed) {
                        selectedTextViewValue.setText(currentText + "×");
                        operatorPressed = true;
                    }else if(operatorPressed){
                        selectedTextViewValue.setText(currentText.substring(0, currentText.length() - 1) + "×");
                    }
                    break;
                case "÷":
                    if(!operatorPressed) {
                        selectedTextViewValue.setText(currentText + "÷");
                        operatorPressed = true;
                    }else if(operatorPressed){
                        selectedTextViewValue.setText(currentText.substring(0, currentText.length() - 1) + "÷");
                    }
                    break;
                case "⌫k":
                    if(currentText.equals("1") || currentText.equals("0"))
                        selectedTextViewValue.setText("0");
                    else if (!currentText.isEmpty() && currentText.length() > 1) {
                        if (!currentText.substring(0, currentText.length() - 1).isEmpty())
                            selectedTextViewValue.setText(currentText.substring(0, currentText.length() - 1));
                        else selectedTextViewValue.setText("0");
                    }else
                        selectedTextViewValue.setText("0");
                    break;
                case "C":
                    selectedTextViewValue.setText("0");
                    break;
                case "=":
                    double result = ExpressionEvaluator.
                            evaluateExpression(
                                    currentText.replace("×","*").
                                            replace("÷","/"));
                    selectedTextViewValue.setText(String.valueOf(result));
                    break;
                case "+/-":
                    if(currentText.equals("0")){
                        selectedTextViewValue.setText("0");
                    }else {
                        if (currentText.startsWith("-")) {
                            selectedTextViewValue.setText(currentText.substring(1));
                        } else if (currentText.startsWith("-") && currentText.length() == 1) {
                            selectedTextViewValue.setText("0");
                        } else {
                            selectedTextViewValue.setText("-" + currentText);
                        }
                    }

            }
        }
        else System.out.println("selected view is null!");
    }

    // Runnable to remove characters
    private final Runnable removeTextRunnable = new Runnable() {
        @Override
        public void run() {
            if (isLongPressed) {
                String currentText = selectedTextViewValue.getText().toString();
                if (!currentText.isEmpty()) {
                    selectedTextViewValue.setText(currentText.substring(0, currentText.length() - 1));
                    handler.postDelayed(this, 100); // Continue removing
                } else {
                    selectedTextViewValue.setText("0");
                    isLongPressed = false; // Stop when text is empty
                }
            }
        }
    };

    protected void setValueTextLimitations(TextView current){
        int maxCharacters = 19; // Define the maximum number of characters
        current.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCharacters)});

        current.addTextChangedListener(new TextWatcher() {
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
                        current.setTextSize(TypedValue.COMPLEX_UNIT_SP, newSize);
                    } else {
                        current.setTextSize(TypedValue.COMPLEX_UNIT_SP, maxSize);
                    }

                    // Perform conversion
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
            public void afterTextChanged(Editable s) {
                if(s.length() > maxCharacters ){

                    Toast.makeText(current.getContext(), "Can't enter more", Toast.LENGTH_SHORT).show();
                    // Remove extra last characters to prevent exceeding the limit
                    s.delete(maxCharacters,s.length());

                    //current.setText(s.subSequence(0, maxCharacters));
                }
            }
        });
    }

    protected abstract void updateConvertedValues(TextView selectedValue);

}
